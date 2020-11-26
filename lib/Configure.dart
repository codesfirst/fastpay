import 'package:fastpay/common/Constants.dart';

class Configure {
  String storing_payment_data = "";
  bool display_total_amount = false; //Display amount (true: yes, false: no)
  String skipping_cvv = Constants.NEVER; //Omite la solicitud del CVV (Never: Siempre pide, Stored: Omite el CVV Para tarjetas almacenadas, Always: Nunca se muestra el CVV)
  bool skipping_card_holder = false; //Oculta campo del tarjeta habiente (nombre del titular)
  List<String> payment_brands = Constants.PAYMENT_BRANDS;
  String device_authentication = Constants.NEVER; //Autenticacion del dispositivo (Never: La autenticacion del dispositivo esta desabilitada, Available: si esta configurado por parte del usuario, Always: Se requiere autenticacion del dispositivo ya sea patron, pin, contraseña o huella)
  String language = Constants.SPANISH; //Lenguaje permitidos (es: Español, en: Ingles)
}