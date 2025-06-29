import 'package:flutter/material.dart';

class WithdrawUser extends StatelessWidget {
  const WithdrawUser({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Row(
          children: [
            Text("Cap", style: TextStyle(color: Colors.green, fontWeight: FontWeight.bold)),
            Text("NEST", style: TextStyle(color: Colors.orange, fontWeight: FontWeight.bold)),
          ],
        ),
        actions: const [
          Icon(Icons.search, color: Colors.orange),
          SizedBox(width: 10),
          Icon(Icons.menu, color: Colors.orange),
          SizedBox(width: 10),
        ],
        backgroundColor: Colors.white,
        elevation: 0,
      ),
      body: SingleChildScrollView(
        padding: const EdgeInsets.all(10),
        child: Column(
          children: [
            _buildTopSummary(),
            const SizedBox(height: 10),
            _buildTabButtons(),
            const SizedBox(height: 10),
            _buildTransactionTable(),
            const SizedBox(height: 10),
            _buildWithdrawForm(),
            const SizedBox(height: 20),
            const Text.rich(
              TextSpan(
                children: [
                  TextSpan(text: "Your withdraw request for "),
                  TextSpan(text: "1200.00", style: TextStyle(fontWeight: FontWeight.bold)),
                  TextSpan(text: " has been submitted successfully which ID: "),
                  TextSpan(text: "UWD015", style: TextStyle(fontWeight: FontWeight.bold)),
                  TextSpan(text: ". It will be perform within next 24 working hours. Thank you!"),
                ],
              ),
              style: TextStyle(fontSize: 12),
              textAlign: TextAlign.justify,
            ),
          ],
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        currentIndex: 0,
        selectedItemColor: Colors.orange,
        unselectedItemColor: Colors.grey,
        items: const [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: ''),
          BottomNavigationBarItem(icon: Icon(Icons.shopping_bag), label: ''),
          BottomNavigationBarItem(icon: Icon(Icons.chat), label: ''),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: ''),
        ],
      ),
    );
  }

  Widget _buildTopSummary() {
    return Container(
      decoration: BoxDecoration(
        color: Colors.blue,
        borderRadius: BorderRadius.circular(8),
      ),
      padding: const EdgeInsets.all(12),
      child: Row(
        children: [
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: const [
                Text("Total Earned", style: TextStyle(color: Colors.white, fontSize: 12)),
                SizedBox(height: 4),
                Text("৳25.00", style: TextStyle(color: Colors.white, fontSize: 20, fontWeight: FontWeight.bold)),
                Text("May 23, 2025", style: TextStyle(color: Colors.white70, fontSize: 12)),
              ],
            ),
          ),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: const [
                Text("Total Spend", style: TextStyle(color: Colors.white, fontSize: 12)),
                SizedBox(height: 4),
                Text("৳1800.00", style: TextStyle(color: Colors.white, fontSize: 20, fontWeight: FontWeight.bold)),
                Text("Md Sanaullah", style: TextStyle(color: Colors.white70, fontSize: 12)),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildTabButtons() {
    return Row(
      children: [
        _tabButton("Earning", Icons.wallet, Colors.orange),
        _tabButton("Order", Icons.shopping_cart, Colors.orange),
        _tabButton("Withdraw", Icons.attach_money, Colors.blue),
      ],
    );
  }

  Widget _tabButton(String label, IconData icon, Color color) {
    return Expanded(
      child: Container(
        margin: const EdgeInsets.symmetric(horizontal: 4),
        child: ElevatedButton.icon(
          onPressed: () {},
          icon: Icon(icon, size: 16),
          label: Text(label),
          style: ElevatedButton.styleFrom(
            backgroundColor: color,
            foregroundColor: Colors.white,
            padding: const EdgeInsets.symmetric(vertical: 10),
          ),
        ),
      ),
    );
  }

  Widget _buildTransactionTable() {
    return Column(
      children: [
        Container(
          color: Colors.blue,
          padding: const EdgeInsets.symmetric(vertical: 8),
          child: const Row(
            children: [
              Expanded(child: Center(child: Text("Date", style: TextStyle(color: Colors.white, fontSize: 12)))),
              Expanded(child: Center(child: Text("Request ID", style: TextStyle(color: Colors.white, fontSize: 12)))),
              Expanded(child: Center(child: Text("Amount", style: TextStyle(color: Colors.white, fontSize: 12)))),
              Expanded(child: Center(child: Text("Status", style: TextStyle(color: Colors.white, fontSize: 12)))),
            ],
          ),
        ),
        _transactionRow("May 12, 2025", "UWD012", "1000.00", "Success", Colors.green),
        _transactionRow("May 18, 2025", "UWD013", "578.00", "Pending", Colors.orange),
        _transactionRow("May 18, 2025", "UWD015", "578.00", "Rejected", Colors.red),
      ],
    );
  }

  Widget _transactionRow(String date, String id, String amount, String status, Color color) {
    return Container(
      padding: const EdgeInsets.symmetric(vertical: 8),
      decoration: const BoxDecoration(
        border: Border(bottom: BorderSide(color: Colors.grey)),
      ),
      child: Row(
        children: [
          Expanded(child: Center(child: Text(date, style: const TextStyle(fontSize: 12)))),
          Expanded(child: Center(child: Text(id, style: const TextStyle(fontSize: 12)))),
          Expanded(child: Center(child: Text(amount, style: const TextStyle(fontSize: 12)))),
          Expanded(child: Center(child: Text(status, style: TextStyle(fontSize: 12, fontWeight: FontWeight.bold, color: color)))),
        ],
      ),
    );
  }

  Widget _buildWithdrawForm() {
    return Column(
      crossAxisAlignment: CrossAxisAlignment.start,
      children: [
        ElevatedButton.icon(
          onPressed: () {},
          icon: const Icon(Icons.request_page),
          label: const Text("Request New Withdraw"),
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.blue,
            foregroundColor: Colors.white,
            padding: const EdgeInsets.symmetric(vertical: 10),
          ),
        ),
        const SizedBox(height: 10),
        const Text("Earned Balance:", style: TextStyle(fontWeight: FontWeight.bold)),
        const TextField(
          enabled: false,
          decoration: InputDecoration(
            filled: true,
            fillColor: Colors.blue,
            hintText: "1200.00",
            hintStyle: TextStyle(color: Colors.white),
          ),
        ),
        const SizedBox(height: 10),
        const Text("Withdrawable Balance:", style: TextStyle(fontWeight: FontWeight.bold)),
        const TextField(
          enabled: false,
          decoration: InputDecoration(
            filled: true,
            fillColor: Colors.blue,
            hintText: "1200.00 (minimum withdrawable limit 500)",
            hintStyle: TextStyle(color: Colors.white),
          ),
        ),
        const SizedBox(height: 20),
        Center(
          child: ElevatedButton(
            onPressed: () {},
            child: const Text("Submit Request"),
            style: ElevatedButton.styleFrom(
              backgroundColor: Colors.blue,
              padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 12),
            ),
          ),
        ),
      ],
    );
  }
}