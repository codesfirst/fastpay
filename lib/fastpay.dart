
import 'dart:async';
import 'package:fastpay/core/Configure.dart';
import 'package:flutter/services.dart' show rootBundle;
import 'package:flutter/services.dart';

class Fastpay {
  static const MethodChannel _channel =
      const MethodChannel('fastpay');

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }


  static Future<String>  checkoutActitvity(String amt, String urlCheckout, String urlPayment, int typePayment,[String config]) async {
    var data ="";
    try {
      data = await rootBundle.loadString('assets/config.json');
    }catch(e){print(e);}
    final String version = await _channel.invokeMethod('checkoutActivity',{"amt":amt, "data": data, "config": config, "checkout": urlCheckout, "payment": urlPayment, "type_payment": typePayment});
    return version;
  }
}
