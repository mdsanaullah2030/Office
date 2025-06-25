import 'package:flutter/material.dart';
import 'package:capnest/Form/model/FormModel.dart';
import 'package:capnest/Form/servics/FormService.dart';
import 'package:capnest/Form/updatedata/Dataupdate.dart';
import 'package:capnest/Form/dashboard/DashboardView.dart';

class FormViewPage extends StatefulWidget {
  const FormViewPage({super.key});

  @override
  State<FormViewPage> createState() => _FormViewPageState();
}

class _FormViewPageState extends State<FormViewPage> {
  final GlobalKey<ScaffoldState> _scaffoldKey = GlobalKey<ScaffoldState>();
  final FormService _service = FormService();
  late Future<List<FormModel>> _formsFuture;

  int _selectedIndex = 0;

  @override
  void initState() {
    super.initState();
    _formsFuture = _service.getForms();
  }

  void _refreshForms() {
    setState(() {
      _formsFuture = _service.getForms();
    });
  }

  void _deleteForm(int id) async {
    bool success = await _service.deleteForm(id);
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(success ? 'Deleted successfully' : 'Failed to delete')),
    );
    if (success) _refreshForms();
  }

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });

    // Navigation logic
    if (index == 0) {
      Navigator.pushReplacement(
        context,
        MaterialPageRoute(builder: (context) => const DashboardView()),
      );
    }
    // Add navigation for other indices as needed
  }

  Widget _buildHeader() {
    return const Padding(
      padding: EdgeInsets.symmetric(horizontal: 16.0, vertical: 10),
      child: Text("Form Entries", style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold)),
    );
  }

  Widget _buildFormTable() {
    return FutureBuilder<List<FormModel>>(
      future: _formsFuture,
      builder: (context, snapshot) {
        if (snapshot.connectionState == ConnectionState.waiting) return const Center(child: CircularProgressIndicator());
        if (snapshot.hasError) return Center(child: Text('Error: ${snapshot.error}'));

        final forms = snapshot.data!;
        if (forms.isEmpty) return const Center(child: Text("No forms available."));

        return SingleChildScrollView(
          scrollDirection: Axis.horizontal,
          child: DataTable(
            columns: const [
              DataColumn(label: Text('Name')),
              DataColumn(label: Text('Email')),
              DataColumn(label: Text('Actions')),
            ],
            rows: forms.map((form) {
              return DataRow(
                cells: [
                  DataCell(Text(form.fullName)),
                  DataCell(Text(form.email)),
                  DataCell(Row(
                    children: [
                      IconButton(
                        icon: const Icon(Icons.edit, color: Colors.blue),
                        onPressed: () async {
                          final updated = await Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => Dataupdate(existingData: form),
                            ),
                          );
                          if (updated == true) _refreshForms();
                        },
                      ),
                      IconButton(
                        icon: const Icon(Icons.delete, color: Colors.red),
                        onPressed: () => _deleteForm(form.id!),
                      ),
                    ],
                  )),
                ],
              );
            }).toList(),
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
        BottomNavigationBarItem(icon: Icon(Icons.person), label: 'Profile'),
        BottomNavigationBarItem(icon: Icon(Icons.shopping_cart), label: 'Cart'),
        BottomNavigationBarItem(icon: Icon(Icons.chat), label: 'Chat'),
      ],
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      key: _scaffoldKey,
      appBar: AppBar(title: const Text('Forms List')),
      body: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          _buildHeader(),
          Expanded(child: _buildFormTable()),
        ],
      ),
      bottomNavigationBar: _buildBottomNavBar(),
    );
  }
}
