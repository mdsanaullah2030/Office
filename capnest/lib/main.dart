import 'package:capnest/Form/adddata/AddDataPage.dart';
import 'package:capnest/Form/dashboard/DashboardView.dart';
import 'package:capnest/Form/formviewpage/FormViewPage.dart';
import 'package:flutter/material.dart';



void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,


      home:DashboardView(

      ),
    );
  }
}