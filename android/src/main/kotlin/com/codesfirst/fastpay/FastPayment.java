package com.codesfirst.fastpay;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.codesfirst.fastpay.data.FlutterStatus;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.WalletConstants;
import com.google.gson.Gson;
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.dialog.GooglePayHelper;
import com.oppwa.mobile.connect.checkout.meta.CheckoutBrandDetectionType;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSkipCVVMode;
import com.oppwa.mobile.connect.checkout.meta.CheckoutStorePaymentDetailsMode;
import com.oppwa.mobile.connect.exception.PaymentError;
import com.oppwa.mobile.connect.provider.Connect;
import com.oppwa.mobile.connect.provider.Transaction;
import com.oppwa.mobile.connect.provider.TransactionType;
import com.codesfirst.fastpay.common.Constants;
import com.codesfirst.fastpay.data.ConfigJson;
import com.codesfirst.fastpay.data.Language;
import com.codesfirst.fastpay.data.PrefSingleton;
import com.codesfirst.fastpay.receiver.CheckoutBroadcastReceiver;
import com.codesfirst.fastpay.task.CheckoutIdRequestAsyncTask;
import com.codesfirst.fastpay.task.CheckoutIdRequestListener;
import com.codesfirst.fastpay.task.PaymentStatusRequestAsyncTask;
import com.codesfirst.fastpay.task.PaymentStatusRequestListener;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.PluginRegistry;

public class FastPayment implements MethodChannel.MethodCallHandler, PluginRegistry.ActivityResultListener,
        CheckoutIdRequestListener, PaymentStatusRequestListener,PluginRegistry.NewIntentListener {
    private float amount;
    private final PluginRegistry.Registrar registrar;
    private final ConfigJson configJson = new ConfigJson();
    private Gson gson = new Gson();

    MethodChannel.Result result;
    /** Plugin registration. */
    private static final String STATE_RESOURCE_PATH = "STATE_RESOURCE_PATH";

    protected String resourcePath;

    private ProgressDialog progressDialog;

    private Application.ActivityLifecycleCallbacks activityLifecycleCallbacks;


    protected void showProgressDialog(String messageId) {
        if (progressDialog != null && progressDialog.isShowing()) {
            return;
        }

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(registrar.activity());
            progressDialog.setCancelable(false);
        }

        progressDialog.setMessage(messageId);
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog == null) {
            return;
        }

        progressDialog.dismiss();
    }

    protected void showAlertDialog(final String message) {
        new AlertDialog.Builder(registrar.activity())
                .setMessage(message)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        result.success(message);
                    }
                })
                .setCancelable(false)
                .show();
    }

    protected void showAlertDialog(int messageId) {
        showAlertDialog(registrar.activity().getString(messageId));
    }
    public static void registerWith(PluginRegistry.Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "fastpay");
        FastPayment paymentGatewayPlugin = new FastPayment(registrar);
        registrar.addActivityResultListener(paymentGatewayPlugin);
        registrar.addNewIntentListener(paymentGatewayPlugin);
        channel.setMethodCallHandler(paymentGatewayPlugin);
    }


    public FastPayment(final  PluginRegistry.Registrar registrar ) {

        this.registrar=registrar;

        this.activityLifecycleCallbacks =
                new Application.ActivityLifecycleCallbacks() {
                    @Override
                    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

                        if (savedInstanceState != null) {
                            resourcePath = savedInstanceState.getString(STATE_RESOURCE_PATH);
                        }
                    }

                    @Override
                    public void onActivityStarted(Activity activity) {}

                    @Override
                    public void onActivityResumed(Activity activity) {}

                    @Override
                    public void onActivityPaused(Activity activity) {}

                    @Override
                    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                        if (activity == registrar.activity()) {
                            outState.putString(STATE_RESOURCE_PATH, resourcePath);
                        }
                    }

                    @Override
                    public void onActivityDestroyed(Activity activity) {}

                    @Override
                    public void onActivityStopped(Activity activity) {}
                };

        if (this.registrar != null
                && this.registrar.activity() != null
                && this.registrar.activity().getApplication() != null) {
            this.registrar
                    .activity()
                    .getApplication()
                    .registerActivityLifecycleCallbacks(this.activityLifecycleCallbacks);
        }



    }
    @Override
    public void onMethodCall(MethodCall call, MethodChannel.Result result) {
        if (call.method.equals("checkoutActivity")) {
            this.result=result;
            String tempData = "";

            //amount = Float.parseFloat(call.argument("amt").toString());
            //Constants.Config.AMOUNT=amount+"";

            //Type Payment
            try {
                tempData = call.argument("type_payment").toString();
                if(Integer.parseInt(tempData) >= 0)
                    Constants.Config.TYPE_PAYMENT = Integer.parseInt(tempData);
            }catch (Exception e){
                Constants.Config.TYPE_PAYMENT = 0;
            }

            //Config
            tempData = call.argument("data").toString();
            Log.d("peter", tempData);
            configJson.LoadJson(tempData);

            //Config Json
            tempData = call.argument("config");
            if(!tempData.isEmpty() && tempData != null) configJson.LoadJson(tempData);

            //Config Brands
            try{configJson.LoadBrands();}catch (Exception e){}

            //Url
            tempData = call.argument("checkout");
            if(tempData.isEmpty() ||  tempData == null) {
                FlutterStatus.Companion.setFlutterMessage("ERR","Error al procesar", "La url de checkout no puede contener valores nulos o vacios");
                result.success(gson.toJson(FlutterStatus.flutterObject));
            }
            else {
                Constants.URL_CHECKOUT = tempData;
                tempData = call.argument("payment");
                if(tempData.isEmpty() ||  tempData == null) {
                    FlutterStatus.Companion.setFlutterMessage("ERR","Error al procesar", "La url de pagos no puede contener valores nulos o vacios");
                    result.success(gson.toJson(FlutterStatus.flutterObject));
                }else {
                    Constants.URL_PAYMENT = tempData;
                    requestCheckoutId(registrar.activity().getString(R.string.checkout_ui_callback_scheme));
                }
            }
        } else {
            result.notImplemented();
        }
    }


    protected void requestCheckoutId(String callbackScheme) {
        showProgressDialog(configJson.getPrefsConfig("progress_message_checkout_id"));

        new CheckoutIdRequestAsyncTask(this)
                .execute(Constants.Config.AMOUNT, Constants.Config.CURRENCY);
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {

   /* if (resultCode == RESULT_CANCELED&&requestCode==2000) {

      Log.i("mobile.connect.checkout", "Transaction was attempted, but unsuccessful");

       String data=intent.getStringExtra("value");
      result.success(data);

    }
    else if (resultCode == RESULT_OK&&requestCode==2000) {
      String data=intent.getStringExtra("value");
      result.success(data);
    }

*/
        if (requestCode == CheckoutActivity.REQUEST_CODE_CHECKOUT) {
            switch (resultCode) {
                case CheckoutActivity.RESULT_OK:
                    /* Transaction completed. */
                    Transaction transaction = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_TRANSACTION);

                    resourcePath = data.getStringExtra(
                            CheckoutActivity.CHECKOUT_RESULT_RESOURCE_PATH);

                    /* Check the transaction type. */
                    if (transaction.getTransactionType() == TransactionType.SYNC) {
                        /* Check the status of synchronous transaction. */
                        requestPaymentStatus(resourcePath);
                    } else {
                        /* Asynchronous transaction is processed in the onNewIntent(). */
                        hideProgressDialog();
                    }

                    break;
                case CheckoutActivity.RESULT_CANCELED:
                    hideProgressDialog();
                    FlutterStatus.Companion.setFlutterMessage("ERR","Transaccion Cancelada");
                    this.result.success(gson.toJson(FlutterStatus.flutterObject));
                    break;
                case CheckoutActivity.RESULT_ERROR:
                    hideProgressDialog();

                    PaymentError error = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_ERROR);

                    FlutterStatus.Companion.setFlutterMessage("ERR","Error " + CheckoutActivity.RESULT_ERROR, error.getErrorMessage());
                    this.result.success(gson.toJson(FlutterStatus.flutterObject));
                    //showAlertDialog(error.getErrorMessage()); //Comentado Peter Herrera
            }
        }

        return false;
    }
    @Override
    public void onCheckoutIdReceived(String checkoutId) {
        hideProgressDialog();

        if (checkoutId == null) {
            //showAlertDialog(configJson.getPrefsConfig("error_message"));
            this.result.success(gson.toJson(FlutterStatus.flutterObject));
        }
        else  if (checkoutId != null) {
            openCheckoutUI(checkoutId);
        }
    }


    private void openCheckoutUI(String checkoutId) {
        CheckoutSettings checkoutSettings = createCheckoutSettings(checkoutId, registrar.activity().getString(R.string.checkout_ui_callback_scheme));
        if(configJson.getPrefsBoolValueConf("display_total_amount"))
            checkoutSettings.setTotalAmountRequired(true);
        checkoutSettings.setLocale(configJson.getLanguage(configJson.getPrefsConfig("language")));
        String storePayment = configJson.getPrefsConfig("storing_payment_data").toLowerCase();
        if(storePayment.equals("never"))
            checkoutSettings.setStorePaymentDetailsMode(CheckoutStorePaymentDetailsMode.NEVER);
        else if (storePayment.equals("prompt"))
            checkoutSettings.setStorePaymentDetailsMode(CheckoutStorePaymentDetailsMode.PROMPT);
        else if (storePayment.equals("always"))
            checkoutSettings.setStorePaymentDetailsMode(CheckoutStorePaymentDetailsMode.ALWAYS);
        String skipping_cvv = configJson.getPrefsConfig("skipping_cvv").toLowerCase();
        if(storePayment.equals("never"))
            checkoutSettings.setSkipCVVMode(CheckoutSkipCVVMode.NEVER);
        else if (storePayment.equals("store"))
            checkoutSettings.setSkipCVVMode(CheckoutSkipCVVMode.FOR_STORED_CARDS);
        else if (storePayment.equals("always"))
            checkoutSettings.setSkipCVVMode(CheckoutSkipCVVMode.ALWAYS);

        checkoutSettings.setBrandDetectionType(CheckoutBrandDetectionType.REGEX);

        //Display installment options
        if(configJson.display_installment.size() > 0) {
            if (configJson.display_installment.contains(Constants.Config.TYPE_PAYMENT)) {
                checkoutSettings.setInstallmentEnabled(true);
                Integer[] myPaymentsOptions = new Integer[configJson.arr_display_installment.size()];
                myPaymentsOptions = configJson.arr_display_installment.toArray(myPaymentsOptions);
                checkoutSettings.setInstallmentOptions(myPaymentsOptions);
            }
        }

        /* Set componentName if you want to receive callbacks from the checkout */
        ComponentName componentName = new ComponentName(
                registrar.activity(). getPackageName(), CheckoutBroadcastReceiver.class.getName());

        /* Set up the Intent and start the checkout activity. */
        Intent intent = checkoutSettings.createCheckoutActivityIntent(registrar.activity(), componentName);

        registrar.activity().startActivityForResult(intent, CheckoutActivity.REQUEST_CODE_CHECKOUT);
    }


    @Override
    public void onErrorOccurred() {
        hideProgressDialog();
        //showAlertDialog(configJson.getPrefsConfig("error_message")); //Comentado Peter Herrera
        this.result.success(gson.toJson(FlutterStatus.flutterObject));
    }

    @Override
    public void onPaymentStatusReceived(String paymentStatus) {
        hideProgressDialog();
        this.result.success(gson.toJson(FlutterStatus.flutterObject));
        /* Comenatdo Peter Herrera
        if ("OK".equals(paymentStatus)) {
            showAlertDialog(configJson.getPrefsConfig("message_successful_payment"));
            return;
        }

        showAlertDialog(configJson.getPrefsConfig("message_unsuccessful_payment"));
         */
    }

    protected void requestPaymentStatus(String resourcePath) {
        showProgressDialog(configJson.getPrefsConfig("progress_message_payment_status"));
        new PaymentStatusRequestAsyncTask(this).execute(resourcePath);
    }

    private Connect.ProviderMode ProviderMode(boolean mode){
        if(mode) return Connect.ProviderMode.TEST;
        else return Connect.ProviderMode.LIVE;
    }

    /**
     * Creates the new instance of {@link CheckoutSettings}
     * to instantiate the {@link CheckoutActivity}.
     *
     * @param checkoutId the received checkout id
     * @return the new instance of {@link CheckoutSettings}
     */
    protected CheckoutSettings createCheckoutSettings(String checkoutId, String callbackScheme) {
        return new CheckoutSettings(checkoutId, Constants.Config.PAYMENT_BRANDS,
                ProviderMode(configJson.getPrefsBoolValueConf("mode_test")))
                .setSkipCVVMode(CheckoutSkipCVVMode.FOR_STORED_CARDS)
                .setWindowSecurityEnabled(false)
                .setShopperResultUrl(callbackScheme + "://callback")
                .setGooglePayPaymentDataRequest(getGooglePayRequest());
    }

    private PaymentDataRequest getGooglePayRequest() {
        return GooglePayHelper.preparePaymentDataRequestBuilder(
                Constants.Config.AMOUNT,
                Constants.Config.CURRENCY,
                Constants.MERCHANT_ID,
                getPaymentMethodsForGooglePay(),
                getDefaultCardNetworksForGooglePay()
        ).build();
    }

    private Integer[] getPaymentMethodsForGooglePay() {
        return new Integer[] {
                WalletConstants.PAYMENT_METHOD_CARD,
                WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD
        };
    }

    private Integer[] getDefaultCardNetworksForGooglePay() {
        return new Integer[] {
                WalletConstants.CARD_NETWORK_VISA,
                WalletConstants.CARD_NETWORK_MASTERCARD,
                WalletConstants.CARD_NETWORK_AMEX
        };
    }


    protected boolean hasCallbackScheme(Intent intent) {
        String scheme = intent.getScheme();

        return registrar.activity().getString(R.string.checkout_ui_callback_scheme).equals(scheme) ||
                registrar.activity(). getString(R.string.payment_button_callback_scheme).equals(scheme) ||
                registrar.activity(). getString(R.string.custom_ui_callback_scheme).equals(scheme);
    }
    @Override
    public boolean onNewIntent(Intent intent) {

        registrar.activity().setIntent(intent);
        /* Check if the intent contains the callback scheme. */
        if (resourcePath != null && hasCallbackScheme(intent)) {
            requestPaymentStatus(resourcePath);
        }
        return true;
    }
}
