class Constants {
  static const String NEVER = "NEVER";
  static const String STORED = "STORED";
  static const String ALWAYS = "ALWAYS";
  static const String PROMPT = "PROMPT";
  static const String SPANISH = "es";
  static const String ENGLISH = "en";
  static const String IF_AVAILABLE = "AVAILABLE";

  //Marca
  static const String VISA = "VISA";
  static const String MASTER = "MASTER";
  static const String AMEX = "AMEX";
  static const String DINERS = "DINERS";
  static const String DISCOVER = "DISCOVER";
  static const String ALIA = "ALIA";
  static const String MAESTRO = "MAESTRO";
  static const List<String> PAYMENT_BRANDS = [VISA,MASTER,AMEX,DINERS,DISCOVER,ALIA,MASTER];

  //Message
  static const String MSG_SUCCESSFUL_PAYMENT = "El pago fue exitoso";
  static const String MSG_UNSUCCESSFUL_PAYMENT  = "El pago no se realizó correctamente";
  static const String PROGRESS_MSG_PLEASE_WAIT = "Por favor espera…";
  static const String PROGRESS_MSG_CHECKOUT_ID  = "Obteniendo el ID…";
  static const String PROGRESS_MSG_CHECKOUT_INFO = "Obtener la información de pago…";
  static const String PROGRESS_MSG_PAYMENT_STATUS =  "Obtener el estado del pago…";
  static const String PROGRESS_MSG_PROCESSING_PAYMENT = "Procesando el pago…";
  static const String ERROR_MSG = "Error en el mensaje";
  static const String ERROR_EMPTY_FIELDS = "Campos Vacios";

  //LIST OF VALOR
  static const List<int> DISPLAY_INSTALLMENT = [2,3];
  static const List<int> ARR_DISPLAY_INSTALLMENT = [3,6,9];
}