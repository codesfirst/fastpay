package com.codesfirst.fastpay.data

import com.google.gson.annotations.SerializedName

data class FlutterObject(
        @SerializedName("CodRsp") var codRsp:String = "ERR",
        @SerializedName("Message") var message:String = "",
        @SerializedName("Detail") var details:String? = null,
        @SerializedName("CheckoutResponse") var checkoutResponse: CheckoutResponse? = null,
        @SerializedName("PaymentStatusResponse") var paymentStatusObject: String? = null
)

class FlutterStatus {
    companion object {
        lateinit var flutterObject: FlutterObject
        fun setFlutterMessage(codRsp: String, message:String,  details: String?=null, checkoutResponse: CheckoutResponse? = null, paymentStatusObject: String? = null):FlutterObject {
            flutterObject = FlutterObject(codRsp,message,details,checkoutResponse,paymentStatusObject)
            return flutterObject
        }
        fun setFlutterMessage(codRsp: String, message:String,  details: String?=null, checkoutResponse: CheckoutResponse? = null):FlutterObject {
            flutterObject = FlutterObject(codRsp,message,details,checkoutResponse,null)
            return flutterObject
        }
        fun setFlutterMessage(codRsp: String, message:String,  details: String?=null):FlutterObject {
            flutterObject = FlutterObject(codRsp,message,details,null,null)
            return flutterObject
        }
        fun setFlutterMessage(codRsp: String, message:String):FlutterObject {
            flutterObject = FlutterObject(codRsp,message,null,null,null)
            return flutterObject
        }
    }
}
