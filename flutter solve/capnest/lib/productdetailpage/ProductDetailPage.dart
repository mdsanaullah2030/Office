import 'package:capnest/registation/model/User.dart';
import 'package:flutter/material.dart';
import 'package:capnest/homepage/model/Product.dart';
import 'package:capnest/homepage/service/ProductService.dart';
import '../productorderpage/ProductOrderPage.dart';

class ProductDetailPage extends StatefulWidget {
  final int productId;
  final User user;
  const ProductDetailPage({super.key, required this.productId, required this.user});

  @override
  State<ProductDetailPage> createState() => _ProductDetailPageState();
}

class _ProductDetailPageState extends State<ProductDetailPage> {
  late Future<Product> _productFuture;
  String _mainImageUrl = '';
  final String imageBaseUrl = 'http://75.119.134.82:2030/images/';

  int _selectedIndex = 0;

  @override
  void initState() {
    super.initState();
    _productFuture = ProductService().fetchProductById(widget.productId);
  }

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
      // Handle bottom nav tap actions if needed
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      bottomNavigationBar: _buildBottomNavBar(),
      body: FutureBuilder<Product>(
        future: _productFuture,
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          } else if (snapshot.hasError) {
            return Center(child: Text("Error: ${snapshot.error}"));
          } else if (!snapshot.hasData) {
            return const Center(child: Text("Product not found"));
          }

          Product product = snapshot.data!;
          List<String> imageUrls = [
            product.imagea,
            product.imageb,
            product.imagec,
            product.imaged,
          ].where((img) => img.isNotEmpty).toList();

          _mainImageUrl = _mainImageUrl.isNotEmpty
              ? _mainImageUrl
              : (imageUrls.isNotEmpty ? imageUrls[0] : '');

          return SingleChildScrollView(
            child: Column(
              children: [
                // Breadcrumb
                Padding(
                  padding: const EdgeInsets.all(12.0),
                  child: Row(
                    children: const [
                      Text("Home > ", style: TextStyle(color: Colors.grey)),
                      Text("Category > ", style: TextStyle(color: Colors.grey)),
                      Text("Product", style: TextStyle(color: Colors.orange)),
                    ],
                  ),
                ),

                // Product Images
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 12.0),
                  child: Row(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      SizedBox(
                        width: 60,
                        child: Column(
                          children: imageUrls.map((url) {
                            return Padding(
                              padding: const EdgeInsets.all(4.0),
                              child: GestureDetector(
                                onTap: () {
                                  setState(() {
                                    _mainImageUrl = url;
                                  });
                                },
                                child: Image.network(
                                  '$imageBaseUrl$url',
                                  height: 40,
                                  width: 40,
                                  fit: BoxFit.cover,
                                  errorBuilder: (context, error, stackTrace) =>
                                  const Icon(Icons.broken_image),
                                ),
                              ),
                            );
                          }).toList(),
                        ),
                      ),
                      const SizedBox(width: 10),
                      Expanded(
                        child: Image.network(
                          '$imageBaseUrl$_mainImageUrl',
                          height: 180,
                          fit: BoxFit.contain,
                          errorBuilder: (context, error, stackTrace) =>
                          const Icon(Icons.broken_image, size: 100),
                        ),
                      ),
                    ],
                  ),
                ),

                const SizedBox(height: 10),
                Text(product.productname,
                    style: const TextStyle(
                        fontSize: 18, fontWeight: FontWeight.bold)),
                const SizedBox(height: 4),
                Container(
                  padding:
                  const EdgeInsets.symmetric(horizontal: 10, vertical: 5),
                  decoration: BoxDecoration(
                      color: Colors.teal,
                      borderRadius: BorderRadius.circular(5)),
                  child: Text("${product.specialprice.toStringAsFixed(0)} Tk",
                      style: const TextStyle(color: Colors.white)),
                ),

                const SizedBox(height: 10),

                // Icons Row
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 20.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: [
                      Column(children: [
                        const Icon(Icons.bar_chart),
                        Text("Profit ${product.offer.toStringAsFixed(0)} Tk")
                      ]),
                      Column(children: [
                        const Icon(Icons.access_time),
                        Text("${product.warranty} Months")
                      ]),
                      Column(children: [
                        const Icon(Icons.redeem),
                        Text(
                            "Earn ${(product.regularprice - product.specialprice).toStringAsFixed(0)} Tk")
                      ]),
                    ],
                  ),
                ),

                const SizedBox(height: 10),

                // Feature Buttons
                Padding(
                  padding: const EdgeInsets.symmetric(horizontal: 20.0),
                  child: Column(
                    children: [
                      featureButton("Product Description", product.description),
                      featureButton("Guarantee / Warranty", "${product.warranty} months"),
                      featureButton("After Sales Service", product.salesservice),
                      featureButton("Terms & Policy", product.policy),
                    ],
                  ),
                ),


                const SizedBox(height: 10),

                // Order Buttons
                Row(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    ElevatedButton(
                      style: ElevatedButton.styleFrom(
                        backgroundColor: Colors.orange,
                        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(30)),
                      ),
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => ProductOrderPage(product: product, user: widget.user),


                          ),
                        );
                      },
                      child: const Text("ORDER NOW"),
                    ),




                    const SizedBox(width: 10),
                    OutlinedButton.icon(
                      onPressed: () {
                        // Call or contact action
                      },
                      icon: const Icon(Icons.phone),
                      label: const Text("Call for Order"),
                    )
                  ],
                ),

                const SizedBox(height: 20),
              ],
            ),
          );
        },
      ),
    );
  }

  Widget featureButton(String title, String content) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 4.0),
      child: ElevatedButton(
        style: ElevatedButton.styleFrom(
          backgroundColor: Colors.cyan,
          shape:
          RoundedRectangleBorder(borderRadius: BorderRadius.circular(30)),
          padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 12),
        ),
        onPressed: () {
          showDialog(
            context: context,
            builder: (_) => AlertDialog(
              title: Text(title),
              content: Text(content),
              actions: [
                TextButton(
                  onPressed: () => Navigator.pop(context),
                  child: const Text("Close"),
                )
              ],
            ),
          );
        },
        child: Row(
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
          children: [
            Text(title),
            const Icon(Icons.open_in_new),
          ],
        ),
      ),
    );
  }

  Widget _buildBottomNavBar() {
    return BottomNavigationBar(
      currentIndex: _selectedIndex,
      selectedItemColor: Colors.blue,
      unselectedItemColor: Colors.grey,
      showSelectedLabels: false,
      showUnselectedLabels: false,
      onTap: _onItemTapped,
      items: const [
        BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
        BottomNavigationBarItem(icon: Icon(Icons.shopping_cart), label: 'Cart'),
        BottomNavigationBarItem(icon: Icon(Icons.chat), label: 'Chat'),
        BottomNavigationBarItem(icon: Icon(Icons.person), label: 'Profile'),
      ],
    );
  }
}
