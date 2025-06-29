import 'package:flutter/material.dart';

class ResellerPageWithdraw extends StatelessWidget {
  const ResellerPageWithdraw({super.key});

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
          Icon(Icons.filter_alt, color: Colors.orange),
          SizedBox(width: 10),
        ],
        backgroundColor: Colors.white,
        elevation: 0,
      ),
      body: SingleChildScrollView(
        child: Column(
          children: [
            _buildSummaryCard(),
            _buildActionButtons(),
            _buildTransactionHeader(),
            _buildTransactionRow("May 12, 2025", "UWD012", "1000.00", "Success"),
            _buildTransactionRow("May 18, 2025", "UWD013", "578.00", "Pending"),
            _buildTransactionRow("May 18, 2025", "UWD015", "578.00", "Rejected"),
            const SizedBox(height: 10),
            _buildWithdrawSection(),
          ],
        ),
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

  Widget _buildActionButtons() {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 8),
      child: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          _actionButton(Icons.account_balance_wallet, "Earning"),
          _actionButton(Icons.shopping_cart, "Order"),
          _actionButton(Icons.attach_money, "Withdraw", Colors.blue),
        ],
      ),
    );
  }

  Widget _actionButton(IconData icon, String label, [Color bgColor = Colors.orange]) {
    return Expanded(
      child: Container(
        margin: const EdgeInsets.symmetric(horizontal: 4),
        child: ElevatedButton.icon(
          onPressed: () {},
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

  Widget _buildTransactionHeader() {
    return Container(
      color: Colors.blue,
      padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 4),
      child: const Row(
        children: [
          Expanded(child: Text("Date", style: TextStyle(color: Colors.white, fontSize: 12))),
          Expanded(child: Text("Request ID", style: TextStyle(color: Colors.white, fontSize: 12))),
          Expanded(child: Text("Amount", style: TextStyle(color: Colors.white, fontSize: 12))),
          Expanded(child: Text("Status", style: TextStyle(color: Colors.white, fontSize: 12))),
        ],
      ),
    );
  }

  Widget _buildTransactionRow(String date, String id, String amount, String status) {
    Color statusColor;
    switch (status) {
      case "Success":
        statusColor = Colors.green;
        break;
      case "Pending":
        statusColor = Colors.orange;
        break;
      case "Rejected":
        statusColor = Colors.red;
        break;
      default:
        statusColor = Colors.grey;
    }

    return Container(
      padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 4),
      decoration: const BoxDecoration(
        border: Border(bottom: BorderSide(color: Colors.grey)),
      ),
      child: Row(
        children: [
          Expanded(child: Text(date, style: const TextStyle(fontSize: 12))),
          Expanded(child: Text(id, style: const TextStyle(fontSize: 12))),
          Expanded(child: Text(amount, style: const TextStyle(fontSize: 12))),
          Expanded(child: Text(status, style: TextStyle(fontSize: 12, fontWeight: FontWeight.bold, color: statusColor))),
        ],
      ),
    );
  }

  Widget _buildWithdrawSection() {
    return Padding(
      padding: const EdgeInsets.all(12),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          ElevatedButton.icon(
            onPressed: () {},
            icon: const Icon(Icons.add),
            label: const Text("Request New Withdraw"),
            style: ElevatedButton.styleFrom(backgroundColor: Colors.blue),
          ),
          const SizedBox(height: 10),
          const Text("Earned Balance:", style: TextStyle(fontWeight: FontWeight.bold)),
          const TextField(
            decoration: InputDecoration(
              hintText: "1200.00",
              enabled: false,
              filled: true,
              fillColor: Colors.white,
            ),
          ),
          const SizedBox(height: 10),
          const Text("Withdrawable Balance:", style: TextStyle(fontWeight: FontWeight.bold)),
          const TextField(
            decoration: InputDecoration(
              hintText: "1200.00 (minimum withdrawable limit 500)",
              enabled: false,
              filled: true,
              fillColor: Colors.white,
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
          const SizedBox(height: 10),
          const Text.rich(
            TextSpan(
              children: [
                TextSpan(text: "Your withdraw request for 1200.00 has been submitted successfully which ID: "),
                TextSpan(
                    text: "UWD015",
                    style: TextStyle(fontWeight: FontWeight.bold, color: Colors.black)),
                TextSpan(text: ". It will be perform within next 24 working hours. thank you!"),
              ],
            ),
            style: TextStyle(fontSize: 12),
          ),
        ],
      ),
    );
  }
}