import 'package:flutter/material.dart';

class OrderConfirmationPage extends StatelessWidget {
  final String userName;

  const OrderConfirmationPage({super.key, required this.userName});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFF5F6FA),
      body: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Top Bar
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 10),
              child: Row(
                children: [
                  const Text('CapNEST',
                      style: TextStyle(
                          fontSize: 20,
                          fontWeight: FontWeight.bold,
                          color: Colors.green)),
                  const SizedBox(width: 10),
                  Expanded(
                    child: Container(
                      height: 36,
                      decoration: BoxDecoration(
                        color: Colors.white,
                        borderRadius: BorderRadius.circular(25),
                      ),
                      child: const TextField(
                        decoration: InputDecoration(
                          contentPadding: EdgeInsets.only(top: 8),
                          hintText: 'Search',
                          prefixIcon: Icon(Icons.search),
                          border: InputBorder.none,
                        ),
                      ),
                    ),
                  ),
                  const SizedBox(width: 10),
                  const CircleAvatar(
                    backgroundColor: Colors.orange,
                    child: Icon(Icons.tune, color: Colors.white),
                  )
                ],
              ),
            ),

            // Breadcrumb
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: RichText(
                text: const TextSpan(
                  style: TextStyle(color: Colors.black),
                  children: [
                    TextSpan(text: 'Home'),
                    TextSpan(text: ' > Cosmetics'),
                    TextSpan(
                      text: ' > Facewash',
                      style: TextStyle(color: Colors.orange),
                    ),
                  ],
                ),
              ),
            ),

            const SizedBox(height: 10),

            // Categories
            SizedBox(
              height: 36,
              child: ListView(
                scrollDirection: Axis.horizontal,
                padding: const EdgeInsets.symmetric(horizontal: 16),
                children: const [
                  Padding(
                    padding: EdgeInsets.only(right: 16),
                    child: Column(
                      children: [
                        Text("Cosmetics",
                            style: TextStyle(
                                fontWeight: FontWeight.bold, color: Colors.orange)),
                        Icon(Icons.circle, size: 6, color: Colors.orange)
                      ],
                    ),
                  ),
                  Padding(
                    padding: EdgeInsets.only(right: 16),
                    child: Text("Fashion", style: TextStyle(fontWeight: FontWeight.bold)),
                  ),
                  Padding(
                    padding: EdgeInsets.only(right: 16),
                    child: Text("Gadget", style: TextStyle(fontWeight: FontWeight.bold)),
                  ),
                  Padding(
                    padding: EdgeInsets.only(right: 16),
                    child: Text("Trending", style: TextStyle(fontWeight: FontWeight.bold)),
                  ),
                ],
              ),
            ),

            const SizedBox(height: 20),

            // Product Detail
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Row(
                children: [
                  Expanded(
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: const [
                        Text('XYZ Daily Face Wash',
                            style: TextStyle(
                                fontSize: 16, fontWeight: FontWeight.bold)),
                        SizedBox(height: 4),
                        Chip(
                          label: Text('499 Tk',
                              style: TextStyle(
                                  color: Colors.white,
                                  fontWeight: FontWeight.bold)),
                          backgroundColor: Colors.teal,
                        ),
                      ],
                    ),
                  ),
                  Image.network(
                    'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvc1jALTaoQwM1e6RyOnGERUh9ZVyE2zFU1A&s',
                    height: 80,
                    width: 80,
                    fit: BoxFit.cover,
                  )
                ],
              ),
            ),

            const SizedBox(height: 30),

            // Confirmation Message

          ],
        ),
      ),

      // Bottom Nav
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: 0,
        selectedItemColor: Colors.orange,
        unselectedItemColor: Colors.blue.shade200,
        showUnselectedLabels: false,
        showSelectedLabels: false,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(
              icon: Icon(Icons.shopping_bag_outlined), label: 'Shop'),
          BottomNavigationBarItem(icon: Icon(Icons.person_outline), label: 'Profile'),
          BottomNavigationBarItem(icon: Icon(Icons.chat_bubble_outline), label: 'Chat'),
          BottomNavigationBarItem(icon: Icon(Icons.account_circle_outlined), label: 'Account'),
        ],
      ),
    );
  }
}