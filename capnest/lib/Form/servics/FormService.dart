import 'dart:convert';
import 'package:capnest/Form/model/FormModel.dart';

import 'package:http/http.dart' as http;


class FormService {
  final String baseUrl = 'http://75.119.134.82:2030/api/forms';

  Future<List<FormModel>> getForms() async {
    final response = await http.get(Uri.parse('$baseUrl/get'));
    if (response.statusCode == 200) {
      List<dynamic> data = jsonDecode(response.body);
      return data.map((json) => FormModel.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load forms');
    }
  }

  Future<FormModel> getFormById(int id) async {
    final response = await http.get(Uri.parse('$baseUrl/$id'));
    if (response.statusCode == 200) {
      return FormModel.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Form not found');
    }
  }

  Future<bool> saveForm(FormModel form) async {
    final response = await http.post(
      Uri.parse('$baseUrl/save'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(form.toJson()),
    );
    return response.statusCode == 200 || response.statusCode == 201;
  }

  Future<bool> updateForm(FormModel form) async {
    final response = await http.put(
      Uri.parse('$baseUrl/update/${form.id}'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(form.toJson()),
    );
    return response.statusCode == 200;
  }

  Future<bool> deleteForm(int id) async {
    final response = await http.delete(Uri.parse('$baseUrl/delete/$id'));
    return response.statusCode == 200;
  }
}
