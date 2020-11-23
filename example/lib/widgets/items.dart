import 'package:flutter/material.dart';
double total=0.0;

class MyItemWidget extends StatefulWidget {
  final String title;
  final String subtitle;
  final double price;
  final bool isIva;
  final String img;
  const MyItemWidget({Key key, this.title, this.subtitle, this.price, this.img ,this.isIva=false}) : super(key: key);

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
                      if(widget.isIva) total += widget.price * 1.12; else total += widget.price;
                    }else {
                      if(widget.isIva) total -= widget.price * 1.12; else total -= widget.price;
                    }
                  });
                })
          ],
        ),
      ),
    );
  }
}