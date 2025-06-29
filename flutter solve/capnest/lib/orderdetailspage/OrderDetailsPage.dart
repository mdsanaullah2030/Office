import 'package:flutter/material.dart';

class OrderDetailsPage extends StatelessWidget {
  const OrderDetailsPage({super.key});

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
            _buildOrderSummaryTable(),
            const SizedBox(height: 10),
            _buildOrderDetails(),
            const SizedBox(height: 10),
            _buildShippingAndBillingInfo(),
          ],
        ),
      ),
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        selectedItemColor: Colors.orange,
        unselectedItemColor: Colors.grey,
        currentIndex: 1,
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
                Text("Total Commission", style: TextStyle(color: Colors.white, fontSize: 12)),
                Text("৳2490.00", style: TextStyle(color: Colors.white, fontSize: 20, fontWeight: FontWeight.bold)),
                Text("Withdrawable ৳200.00", style: TextStyle(color: Colors.white70, fontSize: 12)),
                Text("May 23, 2025", style: TextStyle(color: Colors.white70, fontSize: 12)),
              ],
            ),
          ),
          Expanded(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.end,
              children: const [
                Text("Total Sale", style: TextStyle(color: Colors.white, fontSize: 12)),
                Text("৳514680.00", style: TextStyle(color: Colors.white, fontSize: 20, fontWeight: FontWeight.bold)),
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
        _tabButton("Order", Icons.shopping_cart, Colors.blue),
        _tabButton("Withdraw", Icons.attach_money, Colors.orange),
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

  Widget _buildOrderSummaryTable() {
    return Column(
      children: [
        Container(
          color: Colors.blue,
          padding: const EdgeInsets.symmetric(vertical: 8),
          child: const Row(
            children: [
              Expanded(child: Center(child: Text("Date", style: TextStyle(color: Colors.white, fontSize: 12)))),
              Expanded(child: Center(child: Text("Order Number", style: TextStyle(color: Colors.white, fontSize: 12)))),
              Expanded(child: Center(child: Text("Amount", style: TextStyle(color: Colors.white, fontSize: 12)))),
              Expanded(child: Center(child: Text("Status", style: TextStyle(color: Colors.white, fontSize: 12)))),
            ],
          ),
        ),
        Container(
          padding: const EdgeInsets.symmetric(vertical: 8),
          decoration: const BoxDecoration(border: Border(bottom: BorderSide(color: Colors.grey))),
          child: const Row(
            children: [
              Expanded(child: Center(child: Text("May 12, 2025", style: TextStyle(fontSize: 12)))),
              Expanded(child: Center(child: Text("45682", style: TextStyle(color: Colors.red, fontSize: 12)))),
              Expanded(child: Center(child: Text("10.00", style: TextStyle(fontSize: 12)))),
              Expanded(child: Center(child: Text("Hold", style: TextStyle(fontSize: 12, fontWeight: FontWeight.bold)))),
            ],
          ),
        ),
      ],
    );
  }

  Widget _buildOrderDetails() {
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.all(10),
      decoration: BoxDecoration(
        border: Border.all(color: Colors.grey.shade300),
        borderRadius: BorderRadius.circular(5),
      ),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text("Order Details", style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 5),
          const Text("Status: Hold/Processing/"),
          const SizedBox(height: 10),

          // Order Number, Date, Invoice
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: const [
              Expanded(child: Text("Order Number: 45682")),
              Expanded(child: Text("Order Date: 20 May 2025")),
            ],
          ),
          const SizedBox(height: 4),
          const Text("Invoice: SFLCPS20001"),

          const SizedBox(height: 12),
          const Text("Item Details:", style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),

          // Items
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: const [
              Expanded(child: Text("XYZ Daily Face Wash (250 ml)")),
              Text("2x499.00 = 998.00"),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: const [
              Expanded(child: Text("Regal Rocking Chair (Blue)")),
              Text("1x1000.00 = 1000.00"),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: const [
              Text("Shipping: Dhaka"),
              Text("= 70.00"),
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: const [
              Text("Others:"),
              Text("-"),
            ],
          ),

          const SizedBox(height: 10),
          const Divider(),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: const [
              Text("Total Order Value:", style: TextStyle(fontWeight: FontWeight.bold)),
              Text("2068.00", style: TextStyle(fontWeight: FontWeight.bold)),
            ],
          ),

          const SizedBox(height: 10),
          const Text("Payment: Gateway Name/CoD  Success/Failed/Pending"),
          const SizedBox(height: 6),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: const [
              Text("Phone"),
              Text("Email"),
              Text("Order Note"),
            ],
          ),
        ],
      ),
    );
  }

  Widget _buildShippingAndBillingInfo() {
    return Container(
      width: double.infinity,
      padding: const EdgeInsets.symmetric(vertical: 10),
      child: const Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(height: 10),
          Text("Shipping Address:", style: TextStyle(fontWeight: FontWeight.bold)),
          Text("Villageakjsajsakjsajslasjla, akhsksahksahksa, kahksakhk, Dhaka"),
          SizedBox(height: 10),
          Text("Billing Address:", style: TextStyle(fontWeight: FontWeight.bold)),
          Text("Villageakjsajsakjsajslasjla, akhsksahksahksa, kahksakhk, Dhaka"),
        ],
      ),
    );
  }
}