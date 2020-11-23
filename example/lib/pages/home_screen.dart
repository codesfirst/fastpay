import 'package:fastpay/fastpay.dart';
import 'package:fastpay_example/utils/fastpay.dart';
import 'package:fastpay_example/widgets/items.dart';
import 'package:flutter/material.dart';
import 'package:toast/toast.dart';

class HomeScreen extends StatefulWidget {
  @override
  _HomeScreenState createState() => new _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> with TickerProviderStateMixin {
  AnimationController animCtrl;
  Animation<double> animation;

  AnimationController animCtrl2;
  Animation<double> animation2;

  bool showFirst = true;
  double price = 0.00;
  String result="";


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
                    subtitle: "\$45 incluye iva",
                    price: 45.59,
                    img: "ps4.png"
                  );
                }else {
                  return new MyItemWidget(
                    title: "TV 50  Pulg",
                    subtitle: "\$679 + Iva",
                    price: 679.00,
                      img: "tv.png",
                    isIva: true
                  );
                }
              },
              itemCount: 2,
          )
        ],
      )),
      floatingActionButton: FloatingActionButton(
        backgroundColor: Colors.amber,
        onPressed: ()  {
          if(total > 0)  openPaymentGateway(total); else Toast.show("Elija al menos 1 articulo", context, duration: Toast.LENGTH_SHORT, gravity:  Toast.BOTTOM);
          print(total);
        },
        child: Icon(Icons.shopping_cart, color: Colors.white),
      ),
    );
  }

  Future openPaymentGateway(double tl) async {
    String tempResult=await Fastpay.checkoutActitvity(tl.toStringAsFixed(2));
    setState(()
    {
      result=tempResult;
      Toast.show(result, context, duration: Toast.LENGTH_SHORT, gravity:  Toast.BOTTOM);
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
