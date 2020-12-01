import 'package:fastpay/fastpay.dart';
import 'package:fastpay_example/utils/fastpay.dart';
import 'package:fastpay_example/widgets/items.dart';
import 'package:fastpay_example/widgets/type_payment.dart';
import 'package:flutter/material.dart';
import 'package:toast/toast.dart';

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => new _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> with TickerProviderStateMixin {
  AnimationController animCtrl;
  Animation<double> animation;
  final _formKey = GlobalKey<FormState>();
  final myControllerName = TextEditingController();
  final myControllerApellido = TextEditingController();
  final myControllerEmail = TextEditingController();
  final myControllerEnvio = TextEditingController();

  //Focus
  final _nameFocus = FocusNode();
  final _lastNameFocus = FocusNode();
  final _adressFocus = FocusNode();
  final _emailFocus = FocusNode();

  //Radio Button
  int group = 0;

  int _selectedRadioIndex = 1;

  //Animation
  AnimationController animCtrl2;
  Animation<double> animation2;

  bool showFirst = true;
  double price = 0.00;
  String result="";

  int radioValue = -1;

  void _handleRadioValueChanged(int value) {
    setState(() {
      radioValue = value;
      print(radioValue);
    });
  }


  _fieldFocusChange(BuildContext context, FocusNode currentFocus,FocusNode nextFocus) {
    currentFocus.unfocus();
    FocusScope.of(context).requestFocus(nextFocus);
  }

  @override
  void initState() {
    super.initState();

    // Animation init
    animCtrl = new AnimationController(
        duration: new Duration(milliseconds: 500), vsync: this);
    animation = new CurvedAnimation(parent: animCtrl, curve: Curves.easeOut);
    animation.addListener(() {
      this.setState(() {});
    });
    animation.addStatusListener((AnimationStatus status) {});

    animCtrl2 = new AnimationController(
        duration: new Duration(milliseconds: 1000), vsync: this);
    animation2 = new CurvedAnimation(parent: animCtrl2, curve: Curves.easeOut);
    animation2.addListener(() {
      this.setState(() {});
    });
    animation2.addStatusListener((AnimationStatus status) {});
  }

  @override
  void dispose() {
    animCtrl.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(FastPay.name),
        actions: <Widget>[
          Padding(
            child: Icon(Icons.search),
            padding: const EdgeInsets.only(right: 10.0),
          )
        ],
      ),
      drawer: Drawer(),
      body: new Center(
          child: new Stack(
        children: <Widget>[
          new Center(
            child: new DragTarget(onWillAccept: (_) {
              print('red');
              return true;
            }, onAccept: (_) {
              setState(() => showFirst = false);
              animCtrl.forward();
              animCtrl2.forward();
            }, builder: (_, _1, _2) {
              return new SizedBox.expand(
                child: new Container(color: Colors.blueAccent),
              );
            }),
          ),
          new ListView.builder(
              itemBuilder: (BuildContext context, int index){
                if(index == 0 ) {
                  return new MyItemWidget(
                    title: "PS4 + 2 plancas",
                    subtitle: "\$4.89 incluye iva",
                    price: 4.89,
                    img: "ps4.png",
                    isIvaCal: true,
                  );
                }else if(index == 1) {
                  return new MyItemWidget(
                    title: "TV 50  Pulg",
                    subtitle: "\$3.75 + Iva",
                    price: 3.75,
                      img: "tv.png",
                    isIva: true
                  );
                }else {
                  return new MyItemWidget(
                      title: "PS5 + 3 Juegos",
                      subtitle: "\$5 Producto no posee IVA valor neto",
                      price: 5.00,
                      img: "ps5.png"
                  );
                }
              },
              itemCount: 3,
          )
        ],
      )),
      floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.amber,
        onPressed: ()  {
          if(total0 > 0 || totalImp > 0) {
            showDialog(
              context: context,
              builder: (BuildContext context) {
                return AlertDialog(
                  content: SingleChildScrollView(
                    child: Stack(
                      overflow: Overflow.visible,
                      children: <Widget>[
                        Positioned(
                          right: -40.0,
                          top: -40.0,
                          child: InkResponse(
                            onTap: () {
                              Navigator.of(context).pop();
                            },
                            child: CircleAvatar(
                              child: Icon(Icons.close),
                              backgroundColor: Colors.red,
                            ),
                          ),
                        ),
                        Form(
                          key: _formKey,
                          child: Column(
                            mainAxisSize: MainAxisSize.min,
                            children: <Widget>[
                              Padding(
                                  padding: EdgeInsets.all(8.0),
                                  child: Text("Nombre")
                              ),
                              Padding(
                                padding: EdgeInsets.all(8.0),
                                child: TextFormField(controller: myControllerName, decoration: const InputDecoration(labelText: 'Nombre'),
                                  keyboardType: TextInputType.text, focusNode: _nameFocus, onFieldSubmitted: (term){
                                    _fieldFocusChange(context, _nameFocus, _lastNameFocus);
                                  },
                                ),
                              ),
                              Padding(
                                  padding: EdgeInsets.all(8.0),
                                  child: Text("Apellido")
                              ),
                              Padding(
                                padding: EdgeInsets.all(8.0),
                                child: TextFormField(controller: myControllerApellido,decoration: const InputDecoration(labelText: 'Apellido'),
                                  keyboardType: TextInputType.text, focusNode: _lastNameFocus, onFieldSubmitted: (term){
                                  _fieldFocusChange(context, _lastNameFocus, _emailFocus);
                                  },),
                              ),
                              Padding(
                                  padding: EdgeInsets.all(8.0),
                                  child: Text("Email")
                              ),
                              Padding(
                                padding: EdgeInsets.all(8.0),
                                child: TextFormField(controller: myControllerEmail,decoration: const InputDecoration(labelText: 'Email'),
                                  keyboardType: TextInputType.emailAddress, focusNode: _emailFocus, onFieldSubmitted: (term){
                                    _fieldFocusChange(context, _emailFocus, _adressFocus);
                                  },),
                              ),
                              Padding(
                                  padding: EdgeInsets.all(8.0),
                                  child: Text("Direccion de envio")
                              ),
                              Padding(
                                padding: EdgeInsets.all(8.0),
                                child: TextFormField(controller: myControllerEnvio,decoration: const InputDecoration(labelText: 'Direccion'),
                                  keyboardType: TextInputType.text, focusNode: _adressFocus,),
                              ),
                              Padding(
                                padding: EdgeInsets.all(8.0),
                                child: new MyRadioWidget(
                                    name: "Corriente",
                                    index: 1
                                ),
                              ),
                              Padding(
                                padding: const EdgeInsets.all(8.0),
                                child: RaisedButton(
                                  child: Text("Pagar"),
                                  onPressed: () {
                                    if (_formKey.currentState.validate()) {
                                      _formKey.currentState.save();
                                      openPaymentGateway(total0, totalImp, myControllerName.text, myControllerApellido.text, myControllerEmail.text, myControllerEnvio.text, tpayment);
                                    }
                                  },
                                ),
                              )
                            ],
                          ),
                        ),
                      ],
                    ),
                  ),
                );
              });
          }else Toast.show("Elija un producto", context, duration: Toast.LENGTH_SHORT, gravity:  Toast.BOTTOM);
        },
        child: Icon(Icons.shopping_cart, color: Colors.white),
      ),
    );
  }

  Future openPaymentGateway(double base0, double baseImp, String name, String last_name, String email, String envio, int tipo_pago) async {
    double tax = baseImp * 0.12;
    double tl = base0+baseImp+tax;
    if(tl!=(base0+baseImp+tax)){
      Toast.show("Montos no cuadran", context, duration: Toast.LENGTH_SHORT, gravity:  Toast.BOTTOM);
      return;
    }
    Navigator.of(context).pop();

    if(tipo_pago < 0) {
      Toast.show("Tipo de pago invalido", context, duration: Toast.LENGTH_SHORT, gravity:  Toast.BOTTOM);
      return;
    }

    String urlC;
    if(tipo_pago == 2 || tipo_pago == 3)
      urlC = 'https://msdkprod.firmasegura.com.ec/msdkApi/checkout?ClienteDocID=0986590076&ClientePNombre='+name+'&ClientePApellido='+last_name+'&ClienteEmail='+email+'&' +
        'ClienteIP=197.72.41.140&EnvioDireccion='+envio+'&Valor_Base0='+total0.toStringAsFixed(2)+'&Valor_BaseImp='+totalImp.toStringAsFixed(2)+'&Valor_IVA='+tax.toStringAsFixed(2)+'&' +
        'Valor_Total='+tl.toStringAsFixed(2)+'&Credito_Tipo='+tipo_pago.toString();
    else
      urlC = 'https://msdkprod.firmasegura.com.ec/msdkApi/checkout?ClienteDocID=0986590076&ClientePNombre='+name+'&ClientePApellido='+last_name+'&ClienteEmail='+email+'&' +
          'ClienteIP=197.72.41.140&EnvioDireccion='+envio+'&Valor_Base0='+total0.toStringAsFixed(2)+'&Valor_BaseImp='+totalImp.toStringAsFixed(2)+'&Valor_IVA='+tax.toStringAsFixed(2)+'&' +
          'Valor_Total='+tl.toStringAsFixed(2)+'&Credito_Tipo='+tipo_pago.toString()+'&Credito_Meses=0';
    print(urlC);

    if(name.isEmpty || last_name.isEmpty){
      Toast.show("Campos vacios", context, duration: Toast.LENGTH_SHORT, gravity:  Toast.BOTTOM);
      return;
    }
    String tempResult=await Fastpay.checkoutActitvity(tl.toStringAsFixed(2), urlC, FastPay.urlPayment, tipo_pago, FastPay.configData);
    setState(()
    {
      result=tempResult;
      //Toast.show(result, context, duration: Toast.LENGTH_SHORT, gravity:  Toast.BOTTOM);

      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            content: SingleChildScrollView(
              child: Stack(
                overflow: Overflow.visible,
                children: <Widget>[
                  Positioned(
                    right: -40.0,
                    top: -40.0,
                    child: InkResponse(
                      onTap: () {
                        Navigator.of(context).pop();
                      },
                      child: CircleAvatar(
                        child: Icon(Icons.close),
                        backgroundColor: Colors.red,
                      ),
                    ),
                  ),
                  Form(
                    key: _formKey,
                    child: Column(
                      mainAxisSize: MainAxisSize.min,
                      children: <Widget>[
                        Padding(
                            padding: EdgeInsets.all(8.0),
                            child: Text("JSON RECIBIDO")
                        ),
                        Padding(
                          padding: EdgeInsets.all(8.0),
                          child: Text(result),
                        ),
                      ],
                    ),
                  ),
                ],
              ),
            ),
          );
        });
    });
  }
}

class CardView extends StatelessWidget {
  final double cardSize;
  CardView(this.cardSize);

  @override
  Widget build(BuildContext context) {
    return new Card(
        child: new SizedBox.fromSize(
      size: new Size(cardSize, cardSize),
    ));
  }
}
