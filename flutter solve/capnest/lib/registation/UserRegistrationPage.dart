import 'dart:convert';

import 'package:capnest/registation/LoginPage.dart';
import 'package:capnest/registation/model/User.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;


class UserRegistrationPage extends StatefulWidget {
  const UserRegistrationPage({super.key});

  @override
  State<UserRegistrationPage> createState() => _UserRegistrationPageState();
}

class _UserRegistrationPageState extends State<UserRegistrationPage> {
  final TextEditingController usernameController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();
  final TextEditingController confirmPasswordController = TextEditingController();
  final TextEditingController emailController = TextEditingController();
  final TextEditingController phoneController = TextEditingController();
  final TextEditingController nameController = TextEditingController();

  bool isCustomer = false;
  bool isReseller = false;
  bool agreeToTerms = false;

  Future<void> registerUser(User user) async {
    const url = 'http://75.119.134.82:2030/api/userRegistration';

    final response = await http.post(
      Uri.parse(url),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(user.toJson()),
    );

    if (response.statusCode == 200) {
      final data = jsonDecode(response.body);
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(" ${data['message']}")),
      );
    } else {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(" Failed: ${response.body}")),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFF5F6FA),
      body: SafeArea(
        child: SingleChildScrollView(
          padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 16),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Row(
                children: [
                  const Text(
                    'CapNEST',
                    style: TextStyle(
                      color: Colors.green,
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                    ),
                  ),
                  const SizedBox(width: 10),
                  Expanded(
                    child: TextField(
                      decoration: InputDecoration(
                        hintText: 'Search',
                        prefixIcon: const Icon(Icons.search),
                        filled: true,
                        fillColor: Colors.white,
                        contentPadding: const EdgeInsets.symmetric(vertical: 0),
                        border: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(25),
                          borderSide: BorderSide.none,
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(width: 10),
                  const CircleAvatar(
                    backgroundColor: Colors.orange,
                    child: Icon(Icons.tune, color: Colors.white),
                  ),
                ],
              ),
              const SizedBox(height: 30),

              const Center(
                child: Text(
                  'User Registration',
                  style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                ),
              ),
              const SizedBox(height: 20),

              // Customer / Reseller checkbox
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Row(
                    children: [
                      Checkbox(
                          value: isCustomer,
                          onChanged: (val) {
                            setState(() => isCustomer = val!);
                          }),
                      const Text("Customer"),
                    ],
                  ),
                  const SizedBox(width: 20),
                  Row(
                    children: [
                      Checkbox(
                          value: isReseller,
                          onChanged: (val) {
                            setState(() => isReseller = val!);
                          }),
                      const Text("Reseller"),
                    ],
                  ),
                ],
              ),
              const SizedBox(height: 10),

              _buildInputField(controller: usernameController, icon: Icons.person, hint: 'Username'),
              _buildInputField(controller: passwordController, icon: Icons.lock, hint: 'Password', obscure: true),
              _buildInputField(controller: confirmPasswordController, icon: Icons.lock_outline, hint: 'Confirm Password', obscure: true),
              _buildInputField(controller: emailController, icon: Icons.email, hint: 'Email'),
              _buildInputField(controller: phoneController, icon: Icons.phone, hint: 'Phone number'),
              _buildInputField(controller: nameController, icon: Icons.account_circle, hint: 'Name'),

              const SizedBox(height: 10),

              Row(
                children: [
                  Checkbox(
                      value: agreeToTerms,
                      onChanged: (val) {
                        setState(() => agreeToTerms = val!);
                      }),
                  const Text("I agree to the "),
                  GestureDetector(
                    onTap: () {},
                    child: const Text(
                      "Terms & Conditions",
                      style: TextStyle(color: Colors.blue),
                    ),
                  ),
                ],
              ),

              const SizedBox(height: 10),

              // Sign up button
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.blue,
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(30),
                    ),
                    padding: const EdgeInsets.symmetric(vertical: 14),
                  ),
                  onPressed: () {
                    if (!agreeToTerms) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(content: Text("Please agree to the terms")),
                      );
                      return;
                    }

                    if (passwordController.text != confirmPasswordController.text) {
                      ScaffoldMessenger.of(context).showSnackBar(
                        const SnackBar(content: Text("Passwords do not match")),
                      );
                      return;
                    }

                    final user = User(
                      name: nameController.text,
                      email: emailController.text,
                      password: passwordController.text,
                      phoneNo: phoneController.text,
                      customer: isCustomer ? "yes" : null,
                      reseller: isReseller ? "yes" : null,
                      username: usernameController.text,
                      active: true,
                      isLock: false,
                      role: "USER",
                    );

                    registerUser(user);
                  },
                  child: const Text("Sign up"),
                ),
              ),
              const SizedBox(height: 10),

              Row(
                children: const [
                  Expanded(child: Divider()),
                  Padding(
                    padding: EdgeInsets.symmetric(horizontal: 8),
                    child: Text("already has account?"),
                  ),
                  Expanded(child: Divider()),
                ],
              ),

              const SizedBox(height: 10),


            ],
          ),
        ),
      ),
    );
  }

  Widget _buildInputField({
    required TextEditingController controller,
    required IconData icon,
    required String hint,
    bool obscure = false,
  }) {
    return Container(
      margin: const EdgeInsets.only(bottom: 12),
      decoration: BoxDecoration(
        color: Colors.grey[200],
        borderRadius: BorderRadius.circular(30),
      ),
      child: TextField(
        controller: controller,
        obscureText: obscure,
        decoration: InputDecoration(
          hintText: hint,
          prefixIcon: Icon(icon),
          border: InputBorder.none,
        ),
      ),
    );
  }
}