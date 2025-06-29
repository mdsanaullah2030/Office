import 'dart:convert';
import 'package:capnest/homepage/model/Product.dart';
import 'package:http/http.dart' as http;

class ProductService {
  final String baseUrl = 'http://75.119.134.82:2030/api/product';

  // Fetch all products
  Future<List<Product>> fetchAllProducts() async {
    final response = await http.get(Uri.parse('$baseUrl/getall'));

    if (response.statusCode == 200) {
      List<dynamic> body = jsonDecode(response.body);
      return body.map((json) => Product.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load products. Status code: ${response.statusCode}');
    }
  }

  // Optional: Fetch single product by ID
  Future<Product> fetchProductById(int id) async {
    final response = await http.get(Uri.parse('$baseUrl/$id'));

    if (response.statusCode == 200) {
      return Product.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to load product with ID $id');
    }
  }



  Future<List<Product>> fetchProductsByCategory(int categoryId) async {
    final response = await http.get(
      Uri.parse('http://75.119.134.82:2030/api/product/byCategory/$categoryId'),
    );

    if (response.statusCode == 200) {
      List<dynamic> body = jsonDecode(response.body);
      return body.map((json) => Product.fromJson(json)).toList();
    } else {
      throw Exception('Failed to load products for category $categoryId');
    }
  }




  // Optional: Add product (POST)
  Future<Product> createProduct(Product product) async {
    final response = await http.post(
      Uri.parse('$baseUrl/add'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(product.toJson()),
    );

    if (response.statusCode == 201 || response.statusCode == 200) {
      return Product.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to create product');
    }
  }

  // Optional: Update product (PUT)
  Future<Product> updateProduct(int id, Product product) async {
    final response = await http.put(
      Uri.parse('$baseUrl/update/$id'),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(product.toJson()),
    );

    if (response.statusCode == 200) {
      return Product.fromJson(jsonDecode(response.body));
    } else {
      throw Exception('Failed to update product with ID $id');
    }
  }

  // Optional: Delete product
  Future<void> deleteProduct(int id) async {
    final response = await http.delete(Uri.parse('$baseUrl/delete/$id'));

    if (response.statusCode != 200) {
      throw Exception('Failed to delete product with ID $id');
    }
  }
}
