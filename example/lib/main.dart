import 'package:fastpay_example/pages/home_screen.dart';
import 'package:fastpay_example/pages/intro_screen.dart';
import 'package:fastpay_example/pages/splash_screen.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:fastpay/fastpay.dart';

var routes = <String, WidgetBuilder>{
  "/home": (BuildContext context) => HomeScreen(),
  "/intro": (BuildContext context) => IntroScreen(),
};

void main() {
  runApp(
    new MaterialApp(
      theme: ThemeData(primaryColor: Colors.blueAccent,  accentColor: Colors.yellowAccent),
      home: SplashScreen(),
      routes: routes,
    )
  );
}