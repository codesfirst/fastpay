package com.codesfirst.fastpay.task;

import static com.codesfirst.fastpay.common.Constants.StringEmpty;
import static com.codesfirst.fastpay.common.Constants.StringNull;

public class StatusObject {
    public static String codRsp;
    public static String msjRsp;
    public static String resultId;
    public static String resultCode;
    public static String resultStatus;
    public static String resultDescription;
    public static String cardBrand;
    public static String cardBin;
    public static String cardLast4;
    public static String cardHolder;
    public static String detailAcquirer;
    public static String detailResponse;
    public static String detailAuth;
    public static String detailBatchNo;
    public static String detailRef;
    public static String detailExtDesc;
    public static String detailInterest;
    public static String detailTotal;

    public static void Inizializate(){
        codRsp=StringEmpty;
        msjRsp=StringEmpty;
        resultId=StringNull;
        resultCode=StringEmpty;
        resultStatus=StringEmpty;
        resultDescription=StringEmpty;
        cardBrand=StringNull;
        cardBin=StringNull;
        cardLast4=StringNull;
        cardHolder=StringNull;
        detailAcquirer=StringNull;
        detailResponse=StringNull;
        detailAuth=StringNull;
        detailBatchNo=StringNull;
        detailRef=StringNull;
        detailExtDesc=StringNull;
        detailInterest=StringNull;
        detailTotal=StringNull;
    }
}
