import 'package:flutter/material.dart';
double totalImp = 0.00;
double total0 = 0.00;

class MyItemWidget extends StatefulWidget {
  final String title;
  final String subtitle;
  final double price;
  final bool isIva;
  final String img;
  final bool isIvaCal;
  const MyItemWidget({Key key, this.title, this.subtitle, this.price, this.img ,this.isIva=false, this.isIvaCal=false}) : super(key: key);

  @override
  _MyItemWidgetState createState() => _MyItemWidgetState();
}

class _MyItemWidgetState extends State<MyItemWidget> {
  bool selected = false;

  @override
  Widget build(BuildContext context) {
    return new Card(
      shape: selected
          ? new RoundedRectangleBorder(
          side: new BorderSide(color: Colors.blue, width: 2.0),
          borderRadius: BorderRadius.circular(4.0))
          : new RoundedRectangleBorder(
          side: new BorderSide(color: Colors.white, width: 2.0),
          borderRadius: BorderRadius.circular(4.0)),
      child: new Padding(
        padding: const EdgeInsets.all(4.0),
        child: new Column(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: <Widget>[
            new Text(widget.title),
            new Image(image: AssetImage("assets/images/"+widget.img)),
            new Text(widget.subtitle),
            new Checkbox(
                value: selected,
                onChanged: (value) {
                  setState(() {
                    selected = value;
                    if(selected){
                      if(widget.isIva) {
                        totalImp += widget.price;
                      } else if(widget.isIvaCal){
                        totalImp += (widget.price  / 1.12);
                      }else {
                        total0 += widget.price;
                      }
                    }else {
                      if(widget.isIva) {
                        totalImp -= widget.price;
                      }else if(widget.isIvaCal){
                        totalImp -= (widget.price  / 1.12);
                      } else {
                        total0 -= widget.price;
                      }
                    }
                  });
                })
          ],
        ),
      ),
    );
  }
}