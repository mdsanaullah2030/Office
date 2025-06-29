import 'package:capnest/withdrawuser/WithdrawUser.dart';
import 'package:flutter/material.dart';


// Dummy page - Replace with actual implementation
class WalletPageWithdrawUser extends StatelessWidget {
  const WalletPageWithdrawUser({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text('Withdraw Page')),
      body: const Center(child: Text('This is the Withdraw Page')),
    );
  }
}

class WalletPageOrder extends StatelessWidget {
  const WalletPageOrder({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Row(
          children: [
            Text(
              "Cap",
              style: TextStyle(color: Colors.green, fontWeight: FontWeight.bold),
            ),
            Text(
              "NEST",
              style: TextStyle(color: Colors.orange, fontWeight: FontWeight.bold),
            ),
          ],
        ),
        actions: const [
          Icon(Icons.search, color: Colors.orange),
          SizedBox(width: 10),
          Icon(Icons.filter_alt, color: Colors.orange),
          SizedBox(width: 10),
        ],
        backgroundColor: Colors.white,
        elevation: 0,
      ),
      body: Column(
        children: [
          _buildSummaryCard(),
          _buildActionButtons(context),
          _transactionHeader(),
          _transactionRow("May 12, 2025", "46882", "1000.00", "Delivered"),
          _transactionRow("May 18, 2025", "45780", "578.00", "Processing"),
        ],
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: 0,
        type: BottomNavigationBarType.fixed,
        selectedItemColor: Colors.orange,
        unselectedItemColor: Colors.blue,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: ""),
          BottomNavigationBarItem(icon: Icon(Icons.shopping_bag), label: ""),
          BottomNavigationBarItem(icon: Icon(Icons.chat_bubble_outline), label: ""),
          BottomNavigationBarItem(icon: Icon(Icons.person_outline), label: ""),
        ],
      ),
    );
  }

  Widget _buildSummaryCard() {
    return Container(
      padding: const EdgeInsets.all(12),
      color: Colors.blue,
      child: Row(
        children: [
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: const [
                Text("Total Earned", style: TextStyle(color: Colors.white)),
                SizedBox(height: 4),
                Text("৳ 25.00", style: TextStyle(color: Colors.white, fontSize: 18, fontWeight: FontWeight.bold)),
                SizedBox(height: 4),
                Text("May 23, 2025", style: TextStyle(color: Colors.white70, fontSize: 12)),
              ],
            ),
          ),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: const [
                Text("Total Spend", style: TextStyle(color: Colors.white)),
                SizedBox(height: 4),
                Text("৳ 1800.00", style: TextStyle(color: Colors.white, fontSize: 18, fontWeight: FontWeight.bold)),
                SizedBox(height: 4),
                Text("Md Sanaullah", style: TextStyle(color: Colors.white70, fontSize: 12)),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildActionButtons(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 8),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          _actionButton(Icons.account_balance_wallet, "Earning", Colors.blue, () {
            // TODO: Add earning action here
          }),
          _actionButton(Icons.shopping_cart, "Order", Colors.blue, () {
            // TODO: Add order action here
          }),
          _actionButton(Icons.attach_money, "Withdraw", Colors.deepOrange, () {
            Navigator.push(
              context,
              MaterialPageRoute(builder: (context) => const WithdrawUser()),
            );
          }),
        ],
      ),
    );
  }

  Widget _actionButton(IconData icon, String label, Color bgColor, VoidCallback onPressed) {
    return Expanded(
      child: Container(
        margin: const EdgeInsets.symmetric(horizontal: 4),
        child: ElevatedButton.icon(
          onPressed: onPressed,
          icon: Icon(icon, size: 18),
          label: Text(label),
          style: ElevatedButton.styleFrom(
            backgroundColor: bgColor,
            foregroundColor: Colors.white,
            padding: const EdgeInsets.symmetric(vertical: 8),
          ),
        ),
      ),
    );
  }

  Widget _transactionHeader() {
    return Container(
      color: Colors.blue,
      padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 4),
      child: const Row(
        children: [
          Expanded(child: Text("Date", style: TextStyle(color: Colors.white, fontSize: 12))),
          Expanded(child: Text("Order Number", style: TextStyle(color: Colors.white, fontSize: 12))),
          Expanded(child: Text("Amount", style: TextStyle(color: Colors.white, fontSize: 12))),
          Expanded(child: Text("Status", style: TextStyle(color: Colors.white, fontSize: 12))),
        ],
      ),
    );
  }

  Widget _transactionRow(String date, String orderNo, String amount, String status) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 4),
      decoration: const BoxDecoration(
        border: Border(bottom: BorderSide(color: Colors.grey)),
      ),
      child: Row(
        children: [
          Expanded(child: Text(date, style: const TextStyle(fontSize: 12))),
          Expanded(child: Text(orderNo, style: const TextStyle(fontSize: 12))),
          Expanded(child: Text(amount, style: const TextStyle(fontSize: 12))),
          Expanded(
            child: Text(
              status,
              style: TextStyle(
                fontSize: 12,
                fontWeight: FontWeight.bold,
                color: status == "Delivered" ? Colors.green : Colors.orange,
              ),
            ),
          ),
        ],
      ),
    );
  }
}