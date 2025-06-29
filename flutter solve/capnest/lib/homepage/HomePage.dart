import 'dart:convert';
import 'package:capnest/homepage/model/HomeScreen.dart';
import 'package:capnest/homepage/service/HomeScreenService.dart';
import 'package:capnest/productdetailpage/ProductDetailPage.dart';
import 'package:capnest/registation/model/User.dart';
import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'package:capnest/homepage/model/Product.dart';
import 'package:capnest/homepage/service/CategoryService.dart';
import 'package:capnest/homepage/model/Category.dart';
import 'package:capnest/homepage/service/ProductService.dart';
import 'package:capnest/registation/UserRegistrationPage.dart';
import 'package:capnest/walletpage/WalletPage.dart';

class HomePage extends StatefulWidget {
  final User user;
  const HomePage({super.key, required this.user});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();
  int _selectedIndex = 0;

  final ProductService _productService = ProductService();
  late Future<List<Product>> _productsFuture;

  List<Category> _categories = [];
  bool _isLoading = true;
  int? _selectedCategoryId;

  late Future<List<HomeScreen>> _homeScreensFuture;

  final String imageBaseUrl = 'http://75.119.134.82:2030/images/';

  @override
  void initState() {
    super.initState();
    _homeScreensFuture = HomeScreenService().fetchHomeScreens();
    _fetchCategories();
    _productsFuture = _productService.fetchAllProducts();
  }

  Future<void> _fetchCategories() async {
    try {
      final fetchedCategories = await CategoryService().fetchCategories();
      setState(() {
        _categories = fetchedCategories;
        _isLoading = false;
      });
    } catch (e) {
      setState(() => _isLoading = false);
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(e.toString())),
      );
    }
  }

  void _filterByCategory(int? categoryId) {
    setState(() {
      _selectedCategoryId = categoryId;
      if (categoryId == null) {
        _productsFuture = _productService.fetchAllProducts();
      } else {
        _productsFuture = _productService.fetchProductsByCategory(categoryId);
      }
    });
  }

  void _onItemTapped(int index) {
    if (index == 3) {
      Navigator.push(context, MaterialPageRoute(
          builder: (context) => const UserRegistrationPage()));
    } else {
      setState(() {
        _selectedIndex = index;
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      drawer: _buildDrawer(),
      backgroundColor: const Color(0xFFF5F6FA),
      body: Column(
        children: [
          _buildHeader(),
          Expanded(
            child: CustomScrollView(
              slivers: [
                SliverToBoxAdapter(child: _buildBanner()),
                SliverToBoxAdapter(child: _buildCategoryList()),
                SliverToBoxAdapter(child: _buildProductList()),
              ],
            ),
          ),
        ],
      ),
      bottomNavigationBar: _buildBottomNavBar(),
    );
  }

  Widget _buildDrawer() {
    return Drawer(
      child: ListView(
        children: <Widget>[
          UserAccountsDrawerHeader(
            decoration: const BoxDecoration(color: Colors.green),
            accountName: const Text("MD SANAULLAH"),
            accountEmail: const Text("john@example.com"),
            currentAccountPicture: const CircleAvatar(
              backgroundImage: NetworkImage("https://avatars.githubusercontent.com/u/158471899?v=4"),
            ),
          ),
          _drawerItem(Icons.dashboard, "Dashboard"),
          _drawerItem(Icons.person, "Profile", page: const UserRegistrationPage()),
          _drawerItem(Icons.account_balance_wallet, "Wallet", page: const WalletPage()),
          _drawerItem(Icons.notifications, "Notifications"),
          _drawerItem(Icons.card_giftcard, "Referral Code"),
          _drawerItem(Icons.call, "Call Center"),
          _drawerItem(Icons.help, "Help"),
          _drawerItem(Icons.logout, "Logout"),
        ],
      ),
    );
  }

  Widget _drawerItem(IconData icon, String title, {Widget? page}) {
    return ListTile(
      leading: Icon(icon),
      title: Text(title),
      onTap: () {
        Navigator.pop(context);
        if (page != null) {
          Navigator.push(context, MaterialPageRoute(builder: (context) => page));
        }
      },
    );
  }

  Widget _buildHeader() {
    return Padding(
      padding: const EdgeInsets.symmetric(horizontal: 16.0, vertical: 10),
      child: Row(
        children: [
          IconButton(icon: const Icon(Icons.menu, color: Colors.green),
              onPressed: () => _scaffoldKey.currentState?.openDrawer()),
          const Text('CapNEST', style: TextStyle(
              fontSize: 20, fontWeight: FontWeight.bold, color: Colors.green)),
          const Spacer(),
          const CircleAvatar(backgroundColor: Colors.orange,
              child: Icon(Icons.tune, color: Colors.white)),
        ],
      ),
    );
  }

  Widget _buildBanner() {
    return FutureBuilder<List<HomeScreen>>(
      future: _homeScreensFuture,
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Center(child: CircularProgressIndicator());
        } else if (snapshot.hasError) {
          return Center(child: Text('Error loading banners'));
        } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
          return const SizedBox();
        }

        final banners = snapshot.data!;
        return SizedBox(
          height: 150,
          child: ListView.builder(
            scrollDirection: Axis.horizontal,
            padding: const EdgeInsets.symmetric(horizontal: 16),
            itemCount: banners.length,
            itemBuilder: (context, index) {
              final banner = banners[index];
              return Card(
                margin: const EdgeInsets.only(right: 12),
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12)),
                clipBehavior: Clip.antiAlias,
                child: Image.network(
                  imageBaseUrl + banner.imagea!,
                  fit: BoxFit.cover,
                  width: 350,
                  errorBuilder: (context, error, stackTrace) =>
                  const Icon(Icons.broken_image, size: 100),
                ),
              );
            },
          ),
        );
      },
    );
  }

  Widget _buildCategoryList() {
    return Column(
      children: [
        Padding(
          padding: const EdgeInsets.symmetric(horizontal: 16),
          child: Row(children: const [
            Text('Available Category',
                style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold))
          ]),
        ),
        _isLoading
            ? const Center(child: CircularProgressIndicator())
            : SizedBox(
          height: 40,
          child: ListView.builder(
            scrollDirection: Axis.horizontal,
            padding: const EdgeInsets.symmetric(horizontal: 16),
            itemCount: _categories.length,
            itemBuilder: (context, index) {
              final category = _categories[index];
              final isSelected = _selectedCategoryId == category.id;
              return GestureDetector(
                onTap: () => _filterByCategory(
                    isSelected ? null : category.id),
                child: Container(
                  margin: const EdgeInsets.only(right: 12),
                  padding: const EdgeInsets.symmetric(
                      horizontal: 12, vertical: 6),
                  decoration: BoxDecoration(
                    color: isSelected ? Colors.red : Colors.grey[300],
                    borderRadius: BorderRadius.circular(20),
                  ),
                  child: Text(
                    category.categoryName,
                    style: TextStyle(
                      fontWeight: FontWeight.bold,
                      color: isSelected ? Colors.white : Colors.black,
                    ),
                  ),
                ),
              );
            },
          ),
        ),
      ],
    );
  }

  Widget _buildProductList() {
    return FutureBuilder<List<Product>>(
      future: _productsFuture,
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const Center(child: CircularProgressIndicator());
        } else if (snapshot.hasError) {
          return Center(child: Text('Error: ${snapshot.error}'));
        } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
          return const Center(child: Text('No products found'));
        }

        final products = snapshot.data!;

        return Padding(
          padding: const EdgeInsets.all(16.0),
          child: GridView.builder(
            physics: const NeverScrollableScrollPhysics(),
            shrinkWrap: true,
            gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 2,
              crossAxisSpacing: 12,
              mainAxisSpacing: 12,
              childAspectRatio: 0.75,
            ),
            itemCount: products.length,
            itemBuilder: (context, index) {
              final product = products[index];
              return GestureDetector(
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => ProductDetailPage(productId: product.id, user: widget.user),
                    ),
                  );
                },
                child: Card(
                  elevation: 2,
                  shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(12),
                  ),
                  child: Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: [
                        Expanded(
                          child: ClipRRect(
                            borderRadius: BorderRadius.circular(8),
                            child: Image.network(
                              imageBaseUrl + product.imagea,
                              width: double.infinity,
                              fit: BoxFit.cover,
                              errorBuilder: (context, error, stackTrace) {
                                return const Icon(Icons.broken_image, size: 60);
                              },
                            ),
                          ),
                        ),
                        const SizedBox(height: 8),
                        Text(
                          product.productname,
                          style: const TextStyle(fontWeight: FontWeight.bold),
                          maxLines: 1,
                          overflow: TextOverflow.ellipsis,
                        ),
                        Text(
                          "৳ ${product.specialprice.toStringAsFixed(2)}",
                          style: const TextStyle(color: Colors.green),
                        ),
                        Row(
                          children: List.generate(
                            5,
                                (starIndex) => Icon(
                              starIndex < product.rating
                                  ? Icons.star
                                  : Icons.star_border,
                              size: 14,
                              color: Colors.orange,
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                ),
              );
            },
          ),
        );
      },
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
