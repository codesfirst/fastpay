package com.codesfirst.fastpay.data

import com.codesfirst.fastpay.data.ResponseCodes.COD_ERR
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName


data class CheckoutResponse(
        @SerializedName("CodRsp") var codRsp: String = "",
        @SerializedName("MsjRsp") var msjRsp: String = "",
        @SerializedName("checkoutId") var checkoutId: String? = null
)

data class CheckoutObject(
        @SerializedName("clientID") var clientID:String,
        @SerializedName("firstName") var firstName:String,
        @SerializedName("lastName") var lastName:String,
        @SerializedName("email") var email:String,
        @SerializedName("ip") var ip:String?=null,
        @SerializedName("shippingAddress") var shippingAddress:String,
        @SerializedName("base0") var base0:Double,
        @SerializedName("baseImp") var baseImp:Double,
        @SerializedName("tax") var tax:Double,
        @SerializedName("total") var total:Double,
        @SerializedName("typeCredit") var typeCredit:Int,
        @SerializedName("monthsCredit") var monthsCredit:Int,
        @SerializedName("urlBase") var urlBase:String,
        @SerializedName("ipBase") var ipBase:String
)

data class CheckoutInObject(
        @SerializedName("UrlCheckout") var urlCheckout:String,
        @SerializedName("UrlPayment") var urlPayment:String
)

class Checkout(var dataResponse: CheckoutResponse) {

    init {
        checkoutResponse = dataResponse
    }

    fun getCheckoutId():String? {
        if(checkoutResponse.codRsp == COD_ERR) {
            FlutterStatus.setFlutterMessage("ERR","Codigo de error",null, checkoutResponse)
            return null
        }
        checkoutResponse.checkoutId?.let {
            return it
        }?: run {
            FlutterStatus.setFlutterMessage("ERR","Codigo de error",null, checkoutResponse)
            return null
        }
    }

    companion object {
        lateinit var checkoutObject: CheckoutObject
        lateinit var checkoutResponse:CheckoutResponse

    }
}

object ResponseCodes {
    val COD_OK = "OK"
    val COD_ERR  = "ER"
    val AUT = "00"
    
    //COD ERROR RETURN FLUTTER
    var FLUTTER_ERR = arrayOf("ERR","No se pudo procesar el pago.","")
}
