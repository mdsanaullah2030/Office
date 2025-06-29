import 'package:flutter/material.dart';
import 'package:capnest/Form/adddata/AddDataPage.dart';
import 'package:capnest/Form/formviewpage/FormViewPage.dart';
import 'package:capnest/Form/model/FormModel.dart';
import 'package:capnest/Form/model/HomeScreen.dart';
import 'package:capnest/Form/servics/FormService.dart';
import 'package:capnest/Form/servics/HomeScreenService.dart';

class DashboardView extends StatefulWidget {
  const DashboardView({super.key});

  @override
  State<DashboardView> createState() => _DashboardViewState();
}

class _DashboardViewState extends State<DashboardView> {
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();
  final FormService _formService = FormService();

  late Future<List<FormModel>> _formsFuture;
  late Future<List<HomeScreen>> _homeScreensFuture;

  final String imageBaseUrl = 'http://75.119.134.82:2030/images/';

  int _selectedIndex = 0;

  @override
  void initState() {
    super.initState();
    _formsFuture = _formService.getForms();
    _homeScreensFuture = HomeScreenService().fetchHomeScreens(); //  Fetch banners
  }

  //  Drawer UI
  Widget _buildDrawer() {
    return Drawer(
      child: ListView(
        children: <Widget>[
          const UserAccountsDrawerHeader(
            decoration: BoxDecoration(color: Colors.green),
            accountName: Text("MD SANAULLAH"),
            accountEmail: Text("sanaullah@gmail.com"),
            currentAccountPicture: CircleAvatar(
              backgroundImage: NetworkImage("https://avatars.githubusercontent.com/u/158471899?v=4"),
            ),
          ),
          _drawerItem(Icons.dashboard, "Dashboard", page: const DashboardView()),
          _drawerItem(Icons.save_as, "Save", page: const AddDataPage()),
          _drawerItem(Icons.update, "Update", page: const FormViewPage()),
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

  //  Banner Builder
  Widget _buildBanner() {
    return FutureBuilder<List<HomeScreen>>(
      future: _homeScreensFuture,
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) {
          return const SizedBox(
            height: 180,
            child: Center(child: CircularProgressIndicator()),
          );
        } else if (snapshot.hasError) {
          return const SizedBox(
            height: 180,
            child: Center(child: Text('Error loading banners')),
          );
        } else if (!snapshot.hasData || snapshot.data!.isEmpty) {
          return const SizedBox(
            height: 150,
            child: Center(child: Text('No banners found')),
          );
        }

        final banners = snapshot.data!;
        return SizedBox(
          height: 180,
          child: ListView.builder(
            scrollDirection: Axis.horizontal,
            padding: const EdgeInsets.symmetric(horizontal: 16),
            itemCount: banners.length,
            itemBuilder: (context, index) {
              final banner = banners[index];
              final imageUrl = banner.imagea != null ? imageBaseUrl + banner.imagea! : '';
              return Card(
                margin: const EdgeInsets.only(right: 12),
                shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(12)),
                clipBehavior: Clip.antiAlias,
                child: imageUrl.isNotEmpty
                    ? Image.network(
                  imageUrl,
                  fit: BoxFit.cover,
                  width: 350,
                  errorBuilder: (context, error, stackTrace) =>
                  const Icon(Icons.broken_image, size: 120),
                )
                    : const SizedBox(width: 350, child: Icon(Icons.broken_image)),
              );
            },
          ),
        );
      },
    );
  }

  //  Bottom Navigation
  Widget _buildBottomNavBar() {
    return BottomNavigationBar(
      currentIndex: _selectedIndex,
      selectedItemColor: Colors.blue,
      unselectedItemColor: Colors.grey,
      showSelectedLabels: false,
      showUnselectedLabels: false,
      onTap: (index) => setState(() => _selectedIndex = index),
      items: const [
        BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
        BottomNavigationBarItem(icon: Icon(Icons.shopping_cart), label: 'Cart'),
        BottomNavigationBarItem(icon: Icon(Icons.chat), label: 'Chat'),
        BottomNavigationBarItem(icon: Icon(Icons.person), label: 'Profile'),
      ],
    );
  }

  //  UI
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      drawer: _buildDrawer(),
      appBar: AppBar(
        title: const Text(''),
        leading: IconButton(
          icon: const Icon(Icons.menu),
          onPressed: () => _scaffoldKey.currentState?.openDrawer(),
        ),
      ),
      body: Column(
        children: [
          _buildBanner(), //  Show Banner
          const SizedBox(height: 10),
          //  Table Data
          Expanded(
            child: FutureBuilder<List<FormModel>>(
              future: _formsFuture,
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const Center(child: CircularProgressIndicator());
                }
                if (snapshot.hasError) {
                  return Center(child: Text('Error: ${snapshot.error}'));
                }

                final forms = snapshot.data!;
                if (forms.isEmpty) {
                  return const Center(child: Text("No data available."));
                }

                return SingleChildScrollView(
                  scrollDirection: Axis.horizontal,
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Container(
                        color: Colors.grey[300],
                        child: Row(
                          children: const [
                            _HeaderBox("Full Name"),
                            _HeaderBox("Email"),
                            _HeaderBox("Phone"),
                            _HeaderBox("Address"),
                            _HeaderBox("Gender"),
                            _HeaderBox("Date of Birth"),
                            _HeaderBox("National ID"),
                          ],
                        ),
                      ),
                      const SizedBox(height: 5),
                      ...forms.map((form) => Row(
                        children: [
                          _DataBox(form.fullName),
                          _DataBox(form.email),
                          _DataBox(form.phoneNumber),
                          _DataBox(form.address),
                          _DataBox(form.gender),
                          _DataBox(form.dateOfBirth
                              .toLocal()
                              .toIso8601String()
                              .split('T')[0]),
                          _DataBox(form.nationalId),
                        ],
                      )),
                    ],
                  ),
                );
              },
            ),
          ),
        ],
      ),
      bottomNavigationBar: _buildBottomNavBar(),
    );
  }
}

//  Reusable Header Cell
class _HeaderBox extends StatelessWidget {
  final String title;
  const _HeaderBox(this.title);
  @override
  Widget build(BuildContext context) {
    return Container(
      width: 130,
      padding: const EdgeInsets.all(8),
      alignment: Alignment.center,
      child: Text(title, style: const TextStyle(fontWeight: FontWeight.bold)),
    );
  }
}

//  Reusable Data Cell
class _DataBox extends StatelessWidget {
  final String value;
  const _DataBox(this.value);
  @override
  Widget build(BuildContext context) {
    return Container(
      width: 130,
      padding: const EdgeInsets.all(8),
      alignment: Alignment.centerLeft,
      child: Text(value, maxLines: 2, overflow: TextOverflow.ellipsis),
    );
  }
}
