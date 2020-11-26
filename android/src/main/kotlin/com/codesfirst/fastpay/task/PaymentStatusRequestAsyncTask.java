package com.codesfirst.fastpay.task;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;


import com.codesfirst.fastpay.common.Constants;
import com.codesfirst.fastpay.data.Checkout;
import com.codesfirst.fastpay.data.CheckoutResponse;
import com.codesfirst.fastpay.data.FlutterStatus;
import com.codesfirst.fastpay.data.PaymentStatus;
import com.codesfirst.fastpay.data.PaymentStatusObject;
import com.codesfirst.fastpay.data.ResponseCodes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Represents an async task to request a payment status from the server.
 */
public class PaymentStatusRequestAsyncTask extends AsyncTask<String, Void, String> {

    private PaymentStatusRequestListener listener;
    private String errPayment = null;

    public PaymentStatusRequestAsyncTask(PaymentStatusRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length != 1) {
            return null;
        }

        String resourcePath = params[0];

        if (resourcePath != null) {
            return requestPaymentStatus(resourcePath);
        }

        return null;
    }

    @Override
    protected void onPostExecute(String paymentStatus) {
        if (listener != null) {
            if (paymentStatus == null) {
                listener.onErrorOccurred();
                return;
            }

            listener.onPaymentStatusReceived(paymentStatus);
        }
    }

    private PaymentStatusObject ReadCheckoutResponse(JsonReader reader) throws IOException {
        StatusObject.Inizializate();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("CodRsp")) {
                StatusObject.codRsp = reader.nextString();
            } else if (name.equals("MsjRsp")) {
                StatusObject.msjRsp = reader.nextString();
            } else if (name.equals("ResultId")) {
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.resultId = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else if (name.equals("ResultCode")){
                StatusObject.resultCode = reader.nextString();
            }else if(name.equals("ResultStatus")){
                StatusObject.resultStatus = reader.nextString();
            }else if(name.equals("ResultDescription")){
                StatusObject.resultDescription = reader.nextString();
            }else if(name.equals("CardBrand")){
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.cardBrand = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else if(name.equals("CardBin")){
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.cardBin = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else if(name.equals("CardLast4")){
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.cardLast4 = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else if(name.equals("CardHolder")){
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.cardHolder = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else if(name.equals("DetailAcquirer")){
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.detailAcquirer = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else if(name.equals("DetailResponse")){
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.detailResponse = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else if(name.equals("DetailAuth")){
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.detailAuth = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else if(name.equals("DetailBatchNo")){
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.detailBatchNo = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else if(name.equals("DetailRef")){
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.detailRef = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else if(name.equals("DetailExtDesc")){
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.detailExtDesc = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else if(name.equals("DetailInterest")){
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.detailInterest = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else if(name.equals("DetailTotal")){
                JsonToken check = reader.peek();
                if (check != JsonToken.NULL){
                    StatusObject.detailTotal = reader.nextString();
                }else {
                    reader.nextNull();
                }
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        reader.close();
        return new PaymentStatusObject(StatusObject.codRsp,StatusObject.msjRsp,StatusObject.resultId,StatusObject.resultCode,StatusObject.resultStatus,
                StatusObject.resultDescription,StatusObject.cardBrand,StatusObject.cardBin,StatusObject.cardLast4,StatusObject.cardHolder,
                StatusObject.detailAcquirer,StatusObject.detailResponse,StatusObject.detailAuth,StatusObject.detailBatchNo,StatusObject.detailRef,
                StatusObject.detailExtDesc,StatusObject.detailInterest,StatusObject.detailTotal);
    }

    private String requestPaymentStatus(String resourcePath) {
        if (resourcePath == null) {
            return null;
        }
        URL url;
        String urlString;
        HttpURLConnection connection = null;
        String paymentStatus = null;

        try {
            urlString = Constants.URL_PAYMENT + Checkout.checkoutResponse.getCheckoutId();//URLEncoder.encode(resourcePath, "UTF-8");

            Log.d(Constants.LOG_TAG, "Status request url: " + urlString);

            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);
            /*
            JsonReader jsonReader = new JsonReader(
                    new InputStreamReader(connection.getInputStream(), "UTF-8"));
            PaymentStatusObject paymentStatusObject = ReadCheckoutResponse(jsonReader);
            PaymentStatus _paymentStatus = new PaymentStatus(paymentStatusObject);
            paymentStatus = _paymentStatus.ValidatePayemnt();*/
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            paymentStatus = response.toString();
            FlutterStatus.Companion.setFlutterMessage("OK","Peticion realizada con exito", null, null, paymentStatus);
            // print result
            System.out.println(response.toString());
            Log.d(Constants.LOG_TAG, "Status: " + paymentStatus);
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

        return paymentStatus;
    }
}
