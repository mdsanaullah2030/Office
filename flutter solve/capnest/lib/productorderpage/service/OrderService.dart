import 'dart:convert';
import 'package:capnest/productorderpage/model/OrderModel.dart';
import 'package:http/http.dart' as http;


class OrderService {
  final String apiUrl = 'http://75.119.134.82:2030/api/orders/save';

  Future<bool> submitOrder(OrderModel order) async {
    final response = await http.post(
      Uri.parse(apiUrl),
      headers: {"Content-Type": "application/json"},
      body: jsonEncode(order.toJson()),
    );

    if (response.statusCode == 200) {
      return true;
    } else {
      print("Failed: ${response.body}");
      return false;
    }
  }
}
