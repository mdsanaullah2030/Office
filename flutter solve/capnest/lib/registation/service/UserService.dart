import 'dart:convert';
import 'package:capnest/registation/model/User.dart';
import 'package:http/http.dart' as http;


Future<void> registerUser(User user) async {
  const url = 'http://75.119.134.82:2030/api/userRegistration';

  final response = await http.post(
    Uri.parse(url),
    headers: {'Content-Type': 'application/json'},
    body: jsonEncode(user.toJson()),
  );

  if (response.statusCode == 200) {
    final data = jsonDecode(response.body);
    print(" Registered: ${data['message']}");
  } else {
    print(" Failed: ${response.statusCode}, ${response.body}");
  }
}
