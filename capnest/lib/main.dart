import 'package:capnest/Form/adddata/AddDataPage.dart';
import 'package:capnest/Form/dashboard/DashboardView.dart';
import 'package:capnest/Form/formviewpage/FormViewPage.dart';
import 'package:flutter/material.dart';


  //1st
void main() {

  //2nt
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    //3rd
    return MaterialApp(
      debugShowCheckedModeBanner: false,

     //4th  active page
      home:DashboardView(

      ),
    );
  }
}