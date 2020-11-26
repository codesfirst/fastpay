package com.codesfirst.fastpay.task;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;


import com.codesfirst.fastpay.common.Constants;
import com.codesfirst.fastpay.common.Utils;
import com.codesfirst.fastpay.data.Checkout;
import com.codesfirst.fastpay.data.CheckoutResponse;
import com.codesfirst.fastpay.data.FlutterStatus;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Locale;


/**
 * Represents an async task to request a checkout id from the server.
 */
public class CheckoutIdRequestAsyncTask extends AsyncTask<String, Void, String> {

    private CheckoutIdRequestListener listener;

    public CheckoutIdRequestAsyncTask(CheckoutIdRequestListener listener) {
        this.listener = listener;
    }

    private String Convert2Decimal(double d){
        int r = (int) Math.round(d*100);
        double f = r/100.0;
        System.out.println(f);
        Locale.setDefault(Locale.US);
        return String.format("%.2f",f);
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length != 2) {
            return null;
        }

        String amount = params[0];
        String currency = params[1];
        return requestCheckoutId();
    }

    @Override
    protected void onPostExecute(String checkoutId) {
        if (listener != null) {
            listener.onCheckoutIdReceived(checkoutId);
        }
    }

    private CheckoutResponse ReadCheckoutResponse(JsonReader reader) throws IOException {
        String codRsp="";
        String msjRsp="";
        String checkoutId = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("CodRsp")) {
                codRsp = reader.nextString();
            } else if (name.equals("MsjRsp")) {
                msjRsp = reader.nextString();
            } else if (name.equals("checkoutId")) {
                JsonToken check = reader.peek();

                if (check != JsonToken.NULL){
                    checkoutId = reader.nextString();
                }else {
                    reader.nextNull();
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        reader.close();
        return new CheckoutResponse(codRsp,msjRsp,checkoutId);
    }

    private String requestCheckoutId() {
        String urlString = Constants.URL_CHECKOUT;
        URL url;
        HttpURLConnection connection = null;
        String checkoutId = null;

        try {
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);
            JsonReader reader = new JsonReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));

            //reader.beginObject();
            CheckoutResponse checkoutResponse = ReadCheckoutResponse(reader);
            Checkout checkoutObj = new Checkout(checkoutResponse);
            checkoutId = checkoutObj.getCheckoutId();
            Log.d(Constants.LOG_TAG, "Checkout ID: " + checkoutId);
        } catch (Exception e) {
            Log.e(Constants.LOG_TAG, "Error: ", e);
            if(e.toString().length() > 100)
                FlutterStatus.Companion.setFlutterMessage("ERR","Error en Peticion",e.toString().substring(0, 100));
            else
                FlutterStatus.Companion.setFlutterMessage("ERR","Error en Peticion",e.toString());
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return checkoutId;
    }
}