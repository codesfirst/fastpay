class FastPay {
  static const String name = "FASTPAY";
  static const String name_description = "DEMO";
  static const String store = "Pagos Online\n Pagos Seguros";
  static const String wt1 = "Bienvenidos";
  static const String wc1 = "Esta aplicación se creó exclusivamente como un DEMO guía en la implementación del plugin de FastPay en Flutter.";
  static const String wt2 = "Escenarios de \nintegración";
  static const String wc2 = "Los detalles, interacciones, flujos pre y post autorización, son de exclusiva responsabilidad y desarrollo del comercio que lo implemente.";
  static const String wt3 = "Agregar al carrito";
  static const String wc3 = "Agregue al carrito todo el producto que necesita \ny realice el pedido";
  static const String wt4 = "Compra segura";
  static const String wc4 =
      "Con este demo y la documentación compartida esperamos pueda implementar la solución de forma sencilla, rápida y sobre todo... segura!";
  static const String skip = "Saltar";
  static const String next = "Siguiente";
  static const String gotIt = "Comenzar";
  static const String jsonCheckoutExample = '{' +
      '"clientID":"0986590076",' +
      '"firstName":"Peter Wilfrido",' +
      '"lastName": "Herrera Quimis",' +
      '"email": "peterherrera22@gmail.com",' +
      '"ip":"",' +
      '"shippingAddress": "Isla Trinitaria",' +
      '"base0": 0.00,' +
      '"baseImp":50.00,' +
      '"tax":6.00,' +
      '"total":56.00,' +
      '"typeCredit":0,' +
      '"monthsCredit":0,' +
      '"urlBase":"http://204.93.161.130:8097",' +
      '"ipBase":"204.93.161.130"' +
      '}';
  static const String urlPayment = 'https://msdk.firmasegura.com.ec/msdkApi/status?checkoutId=';
  static const String configData = "{\n\t\"message\": {\n\t\t\"message_successful_payment\": \"El pago fue exitoso\",\n\t\t\"message_unsuccessful_payment\": \"El pago no se realizó correctamente\",\n\t\t\"progress_message_please_wait\":  \"Por favor espera…\",\n\t\t\"progress_message_checkout_id\": \"Obteniendo el ID…\",\n\t\t\"progress_message_checkout_info\": \"Obtener la información de pago…\",\n\t\t\"progress_message_payment_status\": \"Obtener el estado del pago…\",\n\t\t\"progress_message_processing_payment\": \"Procesando el pago…\",\n\t\t\"error_message\": \"Error en el mensaje\",\n\t\t\"error_empty_fields\": \"Campos Vacios\"\n\t},\n\t\"setting\": {\n\t\t\"theme\": \"Light\",\n\t\t\"theme_customize\": \"\",\n\t\t\"storing_payment_data\": \"prompt\",\n\t\t\"display_total_amount\": true,\n\t\t\"skipping_cvv\": \"never\",\n\t\t\"device_authentication\": \"never\",\n\t\t\"language\": \"es\",\n\t\t\"display_installment\": [2,3],\n\t\t\"arr_display_installment\": [3,6,9]\n\t}\n}";
}
