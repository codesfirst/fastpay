class Checkout {
  String clientID;
  String firstName;
  String lastName;
  String email;
  String ip;
  String shippingAddress;
  double base0;
  double baseImp;
  double tax;
  double total;
  int typeCredit;
  int monthsCredit;
  String urlBase;
  String ipBase;

  //Constructor
  Checkout(
      this.clientID,
      this.firstName,
      this.lastName,
      this.email,
      this.ip,
      this.shippingAddress,
      this.base0,
      this.baseImp,
      this.tax,
      this.total,
      this.typeCredit,
      this.monthsCredit,
      this.urlBase,
      this.ipBase);
}