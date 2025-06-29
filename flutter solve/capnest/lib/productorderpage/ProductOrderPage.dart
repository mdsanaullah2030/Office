import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:capnest/homepage/model/Product.dart';
import 'package:capnest/registation/model/User.dart';
import 'package:capnest/orderconfirmationpage/OrderConfirmationPage.dart';

class ProductOrderPage extends StatefulWidget {
  final Product product;
  final User user;

  const ProductOrderPage({super.key, required this.product, required this.user});

  @override
  State<ProductOrderPage> createState() => _ProductOrderPageState();
}

class _ProductOrderPageState extends State<ProductOrderPage> {
  final _formKey = GlobalKey<FormState>();
  final TextEditingController _addressController = TextEditingController();
  final TextEditingController _quantityController = TextEditingController(text: '1');

  String? selectedDistrictId;
  String? selectedDistrictName;
  String? selectedUpazila;

  List<Map<String, String>> districts = [];
  List<String> upazilas = [];

  final String orderApiUrl = 'http://75.119.134.82:2030/api/orders/save';

  @override
  void initState() {
    super.initState();
    fetchDistricts();
  }

  Future<void> fetchDistricts() async {
    final response = await http.get(Uri.parse('https://sohojapi.vercel.app/api/districts'));
    if (response.statusCode == 200) {
      final List data = json.decode(response.body);
      setState(() {
        districts = data.map<Map<String, String>>((e) {
          return {'id': e['id'].toString(), 'name': e['name'].toString()};
        }).toList();
      });
    } else {
      throw Exception('Failed to load districts');
    }
  }

  Future<void> fetchUpazilas(String districtId) async {
    final response = await http.get(Uri.parse('https://sohojapi.vercel.app/api/upzilas/$districtId'));
    if (response.statusCode == 200) {
      final List data = json.decode(response.body);
      setState(() {
        upazilas = data.map<String>((e) => e['name'].toString()).toList();
        selectedUpazila = null;
      });
    } else {
      throw Exception('Failed to load upazilas');
    }
  }

  Future<void> submitOrder() async {
    if (!_formKey.currentState!.validate()) return;

    final response = await http.post(
      Uri.parse(orderApiUrl),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode({
        "quantity": int.tryParse(_quantityController.text),
        "districts": selectedDistrictName,
        "upazila": selectedUpazila,
        "address": _addressController.text,
        "user": {"id": widget.user.id},
        "productList": [
          {"id": widget.product.id}
        ]
      }),
    );

    if (response.statusCode == 200 || response.statusCode == 201) {
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (_) => OrderConfirmationPage(userName: widget.user.name ?? 'User'),
        ),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text('Failed to submit order: ${response.body}')),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFF5F6FA),
      appBar: AppBar(
        backgroundColor: Colors.white,
        title: Row(
          children: const [
            FlutterLogo(),
            SizedBox(width: 8),
            Text('CapNEST', style: TextStyle(color: Colors.green)),
          ],
        ),
        actions: const [
          Icon(Icons.search, color: Colors.black),
          SizedBox(width: 10),
          CircleAvatar(
            backgroundColor: Colors.orange,
            child: Icon(Icons.tune, color: Colors.white),
          ),
          SizedBox(width: 10),
        ],
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(16),
        child: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              // Breadcrumb
              Row(
                children: [
                  const Text("Home > ", style: TextStyle(color: Colors.grey)),
                  Text(
                    "${widget.product.category?.categoryName ?? 'Category'} > ",
                    style: const TextStyle(color: Colors.grey),
                  ),
                  Text(widget.product.productname, style: const TextStyle(color: Colors.orange)),
                ],
              ),
              const SizedBox(height: 20),

              // Product Info
              Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  Text(
                    widget.product.productname,
                    style: const TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
                  ),
                  Image.network(
                    'http://75.119.134.82:2030/images/${widget.product.imagea}',
                    height: 150,
                    width: 200,
                    fit: BoxFit.cover,
                    errorBuilder: (context, error, stackTrace) =>
                    const Icon(Icons.broken_image, size: 50),
                  ),
                ],
              ),

              const SizedBox(height: 8),
              Container(
                padding: const EdgeInsets.symmetric(horizontal: 10, vertical: 4),
                decoration: BoxDecoration(
                  color: Colors.teal,
                  borderRadius: BorderRadius.circular(5),
                ),
                child: Text(
                  "${widget.product.specialprice.toStringAsFixed(0)} Tk",
                  style: const TextStyle(color: Colors.white),
                ),
              ),
              const SizedBox(height: 20),

              buildReadOnlyField('Name', widget.user.name ?? ''),
              buildReadOnlyField('Email', widget.user.email ?? ''),
              buildReadOnlyField('Phone Number', widget.user.phoneNo ?? ''),
              buildTextField('Quantity', _quantityController),

              // Dropdown for District
              DropdownButtonFormField<String>(
                decoration: const InputDecoration(labelText: 'District'),
                value: selectedDistrictId,
                items: districts.map((district) {
                  return DropdownMenuItem<String>(
                    value: district['id'],
                    child: Text(district['name']!),
                  );
                }).toList(),
                onChanged: (value) {
                  setState(() {
                    selectedDistrictId = value!;
                    selectedDistrictName =
                    districts.firstWhere((e) => e['id'] == value)['name'];
                    fetchUpazilas(value);
                  });
                },
                validator: (value) => value == null ? 'Please select a district' : null,
              ),

              // Dropdown for Upazila
              DropdownButtonFormField<String>(
                decoration: const InputDecoration(labelText: 'Upazila'),
                value: selectedUpazila,
                items: upazilas.map((upazila) {
                  return DropdownMenuItem<String>(
                    value: upazila,
                    child: Text(upazila),
                  );
                }).toList(),
                onChanged: (value) => setState(() => selectedUpazila = value),
                validator: (value) => value == null ? 'Please select an upazila' : null,
              ),

              buildTextField('Address', _addressController),

              const SizedBox(height: 20),
              ElevatedButton(
                onPressed: submitOrder,
                child: const Text("Submit Order"),
              ),
            ],
          ),
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        selectedItemColor: Colors.orange,
        unselectedItemColor: Colors.grey,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: ""),
          BottomNavigationBarItem(icon: Icon(Icons.shopping_bag), label: ""),
          BottomNavigationBarItem(icon: Icon(Icons.message), label: ""),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: ""),
        ],
      ),
    );
  }

  Widget buildReadOnlyField(String label, String value) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 6.0),
      child: TextField(
        readOnly: true,
        controller: TextEditingController(text: value),
        decoration: InputDecoration(
          labelText: label,
          border: OutlineInputBorder(),
        ),
      ),
    );
  }

  Widget buildTextField(String label, TextEditingController controller) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 6.0),
      child: TextFormField(
        controller: controller,
        decoration: InputDecoration(
          labelText: label,
          border: OutlineInputBorder(),
        ),
        validator: (value) {
          if (value == null || value.isEmpty) {
            return 'Please enter $label';
          }
          return null;
        },
      ),
    );
  }

  @override
  void dispose() {
    _addressController.dispose();
    _quantityController.dispose();
    super.dispose();
  }
}
