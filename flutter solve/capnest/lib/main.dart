import 'package:flutter/material.dart';
import 'package:capnest/registation/model/User.dart'; // import User class
import 'package:capnest/homepage/HomePage.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    final dummyUser = User(
      id: 1,
      name: 'Test User',
      email: 'test@example.com',
      password: '',
      phoneNo: '',
      username: 'testuser',
      customer: 'yes',
      reseller: 'no',
      active: true,
      isLock: false,
      role: 'USER',
    );

    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: HomePage(user: dummyUser), // pass user here
    );
  }
}
