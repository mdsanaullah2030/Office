import 'dart:convert';
import 'package:capnest/homepage/model/HomeScreen.dart';
import 'package:http/http.dart' as http;
// Assuming your model file is named like this

class HomeScreenService {
  final String _baseUrl = 'http://75.119.134.82:2030/api/homeScreen/get';

  Future<List<HomeScreen>> fetchHomeScreens() async {
    final response = await http.get(Uri.parse(_baseUrl));

    if (response.statusCode == 200) {
      List<dynamic> jsonList = json.decode(response.body);

      return jsonList.map((json) => HomeScreen.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load home screens');
    }
  }
}