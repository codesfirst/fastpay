import 'package:fastpay/common/Constants.dart';

class Configure {
  //Message
  String message_successful_payment = Constants.MSG_SUCCESSFUL_PAYMENT;
  String message_unsuccessful_payment = Constants.MSG_UNSUCCESSFUL_PAYMENT;
  String progress_message_please_wait = Constants.PROGRESS_MSG_PLEASE_WAIT;
  String progress_message_checkout_id = Constants.PROGRESS_MSG_CHECKOUT_ID;
  String progress_message_checkout_info = Constants.PROGRESS_MSG_CHECKOUT_INFO;
  String progress_message_payment_status = Constants.PROGRESS_MSG_PAYMENT_STATUS;
  String progress_message_processing_payment = Constants.PROGRESS_MSG_PROCESSING_PAYMENT;
  String error_message = Constants.ERROR_MSG;
  String error_empty_fields = Constants.ERROR_EMPTY_FIELDS;

  //Config
  String storing_payment_data = Constants.PROMPT;
  bool display_total_amount = false; //Display amount (true: yes, false: no)
  String skipping_cvv = Constants.NEVER; //Omite la solicitud del CVV (Never: Siempre pide, Stored: Omite el CVV Para tarjetas almacenadas, Always: Nunca se muestra el CVV)
  String device_authentication = Constants.NEVER; //Autenticacion del dispositivo (Never: La autenticacion del dispositivo esta desabilitada, Available: si esta configurado por parte del usuario, Always: Se requiere autenticacion del dispositivo ya sea patron, pin, contraseña o huella)
  String language = Constants.SPANISH; //Lenguaje permitidos (es: Español, en: Ingles)
  List<int> display_installment = Constants.DISPLAY_INSTALLMENT;
  List<int> arr_display_installment = Constants.ARR_DISPLAY_INSTALLMENT;
  bool mode_test = true;
  List<String> arr_brand_payment = Constants.PAYMENT_BRANDS;


  Configure(
      this.message_successful_payment,
      this.message_unsuccessful_payment,
      this.progress_message_please_wait,
      this.progress_message_checkout_id,
      this.progress_message_checkout_info,
      this.progress_message_payment_status,
      this.progress_message_processing_payment,
      this.error_message,
      this.error_empty_fields,
      this.storing_payment_data,
      this.display_total_amount,
      this.skipping_cvv,
      this.device_authentication,
      this.language,
      this.display_installment,
      this.arr_display_installment,
      this.mode_test,
      this.arr_brand_payment);


  //Get Ans Setter
  //Config
  set setStoringPayment(String value) => this.storing_payment_data = value;
  String get getStoringPayment => this.storing_payment_data;

  set setDisplayTotal(bool value) => this.display_total_amount = value;
  bool get getDisplayTotal => this.display_total_amount;

  set setSkippingCvv(String value) => this.skipping_cvv = value;
  String get getSkippingCvv => this.skipping_cvv;

  set setDeviceAuth(String value) => this.device_authentication = value;
  String get getDeviceAuth => this.device_authentication;

  set setLanguage(String value) => this.language = value;
  String get getLanguage => this.language;

  set setArrDisplayInstallment(List<int> value) => this.arr_display_installment = value;
  List<int> get getArrDisplayInstallment => this.arr_display_installment;

  set setDisplayInstallment(List<int> value) => this.display_installment = value;
  List<int> get getDisplayInstallment => this.display_installment;

  set setModeTest(bool value) => this.mode_test = value;
  bool get getModeTest => this.mode_test;

  set setBrandPayment(List<String> value) => this.arr_brand_payment = value;
  List<String> get getBrandPayment => this.arr_brand_payment;

  //Message
  set setMessageSuccess(String value) => this.message_successful_payment = value;
  String get getMessageSuccess => this.message_successful_payment;

  set setMessageUnsuccess(String value) => this.message_unsuccessful_payment = value;
  String get getMessageUnsuccess => this.message_unsuccessful_payment;

  set setMessageCheckout(String value) => this.progress_message_checkout_id = value;
  String get getMessageCheckout => this.progress_message_checkout_id;

  set setMessageCheckoutInfo(String value) => this.progress_message_checkout_info = value;
  String get getMessageCheckoutInfo => this.progress_message_checkout_info;

  set setMessagePaymentStatus(String value) => this.progress_message_payment_status = value;
  String get getMessagePaymentStatus => this.progress_message_payment_status;

  set setMessageProcessingPayment(String value) => this.progress_message_processing_payment = value;
  String get getMessageProcessingPayment => this.progress_message_processing_payment;

  set setMessageError(String value) => this.error_message = value;
  String get getMessageError => this.error_message;

  set setMessageErrorEmpty(String value) => this.error_empty_fields = value;
  String get getMessageErrorEmpty => this.error_empty_fields;

}