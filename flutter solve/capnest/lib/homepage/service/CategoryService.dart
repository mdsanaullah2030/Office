import 'dart:convert';
import 'package:http/http.dart' as http;
import 'package:capnest/homepage/model/Category.dart';

class CategoryService {
  final String _baseUrl = 'http://75.119.134.82:2030/api/categories/get';

  Future<List<Category>> fetchCategories() async {
    try {
      final response = await http.get(Uri.parse(_baseUrl));

      if (response.statusCode == 200) {
        List<dynamic> jsonList = json.decode(response.body);
        return jsonList.map((json) => Category.fromJson(json)).toList();
      } else {
        throw Exception('Failed to load categories: ${response.statusCode}');
      }
    } catch (e) {
      throw Exception('Error fetching categories: $e');
    }
  }
}
