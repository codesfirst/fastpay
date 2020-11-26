package com.codesfirst.fastpay.data


import com.codesfirst.fastpay.data.ResponseCodes.AUT
import com.codesfirst.fastpay.data.ResponseCodes.COD_ERR
import com.codesfirst.fastpay.data.ResponseCodes.COD_OK
import com.google.gson.annotations.SerializedName

data class PaymentStatusObject(
        @SerializedName("CodRsp") var codRsp:String,
        @SerializedName("MsjRsp") var msjRsp:String,
        @SerializedName("ResultId") var resultId:String? = null,
        @SerializedName("ResultCode") var resultCode:String,
        @SerializedName("ResultStatus") var resultStatus:String,
        @SerializedName("ResultDescription") var resultDescription:String,
        @SerializedName("CardBrand") var cardBrand:String? = null,
        @SerializedName("CardBin") var cardBin:String? = null,
        @SerializedName("CardLast4") var cardLast4:String? = null,
        @SerializedName("CardHolder") var cardHolder:String?=null,
        @SerializedName("DetailAcquirer") var detailAcquirer:String? = null,
        @SerializedName("DetailResponse") var detailResponse:String? = null,
        @SerializedName("DetailAuth") var detailAuth:String? = null,
        @SerializedName("DetailBatchNo") var detailBatchNo:String? = null,
        @SerializedName("DetailRef") var detailRef:String? = null,
        @SerializedName("DetailExtDesc") var detailExtDesc:String? = null,
        @SerializedName("DetailInterest") var detailInterest:String? = null,
        @SerializedName("DetailTotal") var detailTotal:String? = null
)


class PaymentStatus(pSt:PaymentStatusObject) {
    init {
        paymentStatusObject=pSt
    }
    fun ValidatePayemnt():String?{
        if(paymentStatusObject.codRsp == COD_ERR) return null
        paymentStatusObject.detailResponse?.let {
            if(it.equals(AUT)) return COD_OK
            return null
        }?: run {
            return null
        }
    }
    companion object {
        lateinit var paymentStatusObject: PaymentStatusObject
    }
}

