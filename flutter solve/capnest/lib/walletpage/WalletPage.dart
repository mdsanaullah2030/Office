import 'package:capnest/walletpagewithdrawuser/WalletPageWithdrawUser.dart';
import 'package:flutter/material.dart';

// Import your WalletPageOrder screen

class WalletPage extends StatelessWidget {
  const WalletPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: const Color(0xFFF5F6FA),
      appBar: AppBar(
        backgroundColor: Colors.white,
        title: const Text('CapNEST',
            style: TextStyle(color: Colors.green, fontWeight: FontWeight.bold)),
        actions: const [
          Icon(Icons.search, color: Colors.black),
          SizedBox(width: 12),
          Icon(Icons.tune, color: Colors.orange),
          SizedBox(width: 12),
        ],
        elevation: 0,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            // Top Cards
            Padding(
              padding: const EdgeInsets.all(16),
              child: Row(
                children: [
                  _infoCard(
                      "Total Earned", "৳ 25.00", "May 23, 2025", Colors.blue),
                  const SizedBox(width: 10),
                  _infoCard("Total Spend", "৳ 1800.00", "Md Sanaullah",
                      Colors.blue),
                ],
              ),
            ),

            // Buttons with navigation
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceAround,
                children: [
                  _actionButton(
                      icon: Icons.account_balance_wallet,
                      label: "Earning",
                      color: Colors.blue,
                      onPressed: () {
                        // Earning tapped
                      }),
                  _actionButton(
                      icon: Icons.shopping_cart,
                      label: "Order",
                      color: Colors.orange,
                      onPressed: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => const WalletPageOrder()),
                        );
                      }),
                  _actionButton(
                      icon: Icons.attach_money,
                      label: "Withdraw",
                      color: Colors.deepOrange,
                      onPressed: () {
                        // Withdraw tapped
                      }),
                ],
              ),
            ),
            const SizedBox(height: 16),

            // Transaction Table
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  const Text("Transaction",
                      style: TextStyle(fontWeight: FontWeight.bold)),
                  const SizedBox(height: 10),
                  _transactionHeader(),
                  _transactionRow("May 12, 2025", "46882", "10.00", "10.00"),
                  _transactionRow("May 18, 2025", "45780", "15.00", "25.00"),
                ],
              ),
            ),
          ],
        ),
      ),

      // Bottom Nav Bar
      bottomNavigationBar: BottomNavigationBar(
        selectedItemColor: Colors.orange,
        unselectedItemColor: Colors.grey,
        currentIndex: 2,
        showSelectedLabels: false,
        showUnselectedLabels: false,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: 'Home'),
          BottomNavigationBarItem(icon: Icon(Icons.shopping_bag), label: 'Shop'),
          BottomNavigationBarItem(icon: Icon(Icons.chat), label: 'Chat'),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: 'Profile'),
        ],
      ),
    );
  }

  Widget _infoCard(
      String title, String amount, String sub, Color color) {
    return Expanded(
      child: Container(
        padding: const EdgeInsets.all(12),
        decoration: BoxDecoration(
          color: color,
          borderRadius: BorderRadius.circular(10),
        ),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(title,
                style: const TextStyle(color: Colors.white, fontSize: 14)),
            const SizedBox(height: 6),
            Text(amount,
                style: const TextStyle(
                    color: Colors.white,
                    fontSize: 20,
                    fontWeight: FontWeight.bold)),
            const SizedBox(height: 6),
            Text(sub,
                style: const TextStyle(color: Colors.white70, fontSize: 12)),
          ],
        ),
      ),
    );
  }

  Widget _actionButton(
      {required IconData icon,
        required String label,
        required Color color,
        required VoidCallback onPressed}) {
    return Expanded(
      child: Container(
        margin: const EdgeInsets.symmetric(horizontal: 4),
        child: ElevatedButton.icon(
          style: ElevatedButton.styleFrom(
            foregroundColor: Colors.white,
            backgroundColor: color,
            shape:
            RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
          ),
          icon: Icon(icon, size: 16),
          label: Text(label, style: const TextStyle(fontSize: 12)),
          onPressed: onPressed,
        ),
      ),
    );
  }

  Widget _transactionHeader() {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 8),
      color: Colors.blue,
      child: const Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          Expanded(
              child: Text("Date", style: TextStyle(color: Colors.white))),
          Expanded(
              child:
              Text("Order Number", style: TextStyle(color: Colors.white))),
          Expanded(
              child: Text("Amount", style: TextStyle(color: Colors.white))),
          Expanded(
              child: Text("Total", style: TextStyle(color: Colors.white))),
        ],
      ),
    );
  }

  Widget _transactionRow(
      String date, String orderNo, String amount, String total) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 8),
      decoration: const BoxDecoration(
          border: Border(bottom: BorderSide(color: Colors.grey))),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceAround,
        children: [
          Expanded(child: Text(date, style: const TextStyle(fontSize: 12))),
          Expanded(child: Text(orderNo, style: const TextStyle(fontSize: 12))),
          Expanded(child: Text(amount, style: const TextStyle(fontSize: 12))),
          Expanded(child: Text(total, style: const TextStyle(fontSize: 12))),
        ],
      ),
    );
  }
}