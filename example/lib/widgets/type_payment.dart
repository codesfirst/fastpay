import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
int tpayment = 0;
class TypePayment {
  String name;
  int index;
  TypePayment({this.name, this.index});
}

class MyRadioWidget extends StatefulWidget {
  String name;
  int index;
  MyRadioWidget({this.name, this.index});

  @override
  _MyRadioWidgetState createState() => _MyRadioWidgetState();
}

class _MyRadioWidgetState extends State<MyRadioWidget> {
  bool selected = false;

  // Default Radio Button Item
  String radioItem = 'Corriente';

// Group Value for Radio Button.
  int id = 1;
  int _selectedRadioIndex = 1;
  List<int> _selectedRadioIndexList = [];

  List<TypePayment> pList = [
    TypePayment(
      index: 0,
      name: "Corriente",
    ),
    TypePayment(
      index: 2,
      name: "Diferido con interes",
    ),
    TypePayment(
      index: 3,
      name: "Diferido sin interes",
    ),
  ];
  int CardNo = -1;



  @override
  Widget build(BuildContext context) {
    return Column(
      children: pList
          .map((data) => Container(
            child: RadioListTile(
              title: Text("${data.name}"),
              groupValue: id,
              activeColor: Colors.green,
              value: data.index,
              onChanged: (val) {
                setState(() {
                  radioItem = data.name;
                  id = data.index;
                  tpayment = data.index;
                  print(id);
                  print(radioItem);
                  print(val);
                });
              },
            ),
        )
      ).toList(),
    );
  }
}
