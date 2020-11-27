# fastpay

Integration plugin for open payment platforms for Android and iOS mobile devices.

## Usage
Now the method receives 5 parameters:

```
checkoutActitvity (String amt, String urlCheckout, String urlPayment, int type_payment ,[String config])
```
* amt: Total amount of the transaction
* urlCheckout: Connection url to make the checkout request request.
* urlPayment: Connection url to verify the payment status.
* type_payment: Type of payment, example: Current (0), deferred (2).
* config: Optional parameter where the configuration can be sent in the same format as the config.json file.

Below is an example of invocation of the checkoutActitvity method:


```
Future openPaymentGateway(double base0, double baseImp, String name, String last_name, String email, String envio, int typePayment) async {
    double tax = baseImp * 0.12;
    double tl = base0+baseImp+tax;
    if(tl!=(base0+baseImp+tax)){
      Toast.show("Montos no cuadran", context, duration: Toast.LENGTH_SHORT, gravity:  Toast.BOTTOM);
      return;
    }

    Navigator.of(context).pop();
    String urlC;
    if(tipo_pago == 2 || tipo_pago == 3)
      urlC = 'https://url/msdkApi/checkout?ClienteDocID=0986590076&ClientePNombre='+name+'&ClientePApellido='+last_name+'&ClienteEmail='+email+'&' +
        'ClienteIP=197.72.41.140&EnvioDireccion='+envio+'&Valor_Base0='+total0.toStringAsFixed(2)+'&Valor_BaseImp='+totalImp.toStringAsFixed(2)+'&Valor_IVA='+tax.toStringAsFixed(2)+'&' +
        'Valor_Total='+tl.toStringAsFixed(2)+'&Credito_Tipo='+tipo_pago.toString();
    else
      urlC = 'https://url/msdkApi/checkout?ClienteDocID=0986590076&ClientePNombre='+name+'&ClientePApellido='+last_name+'&ClienteEmail='+email+'&' +
          'ClienteIP=197.72.41.140&EnvioDireccion='+envio+'&Valor_Base0='+total0.toStringAsFixed(2)+'&Valor_BaseImp='+totalImp.toStringAsFixed(2)+'&Valor_IVA='+tax.toStringAsFixed(2)+'&' +
          'Valor_Total='+tl.toStringAsFixed(2)+'&Credito_Tipo='+tipo_pago.toString()+'&Credito_Meses=0';
    print(urlC);

    if(name.isEmpty || last_name.isEmpty){
      Toast.show("Campos vacios", context, duration: Toast.LENGTH_SHORT, gravity:  Toast.BOTTOM);
      return;
    }
    String tempResult=await Fastpay.checkoutActitvity(tl.toStringAsFixed(2), urlC, FastPay.urlPayment, FastPay.configData);
    setState(()
    {
      result=tempResult
    }
}
```

## Checkout request response format

```
{
    "CodRsp": "OK",
    "MsjRsp": "successfully created checkout",
    "checkoutId": "777BC35423F94C4E292681666F724B1C.uat01-vm-tx03"
}
```

### Flutter response format

```
{
    "CodRsp":"",
    "Message":"",
    "Detail":"",
    "CheckoutResponse":"",
    "PaymentStatusResponse":""
 }
```
* CodRsp: Response code can be "OK" or "ERR".
* Message: Error or success message.
* Detail: Detail or description of the request. It can be null.
* CheckoutResponse: Checkout response format, it is sent as long as the chechout is null. It can be null.
* PaymentStatusResponse: Payment response format, it is sent as long as the payment process continues. It can be null.


