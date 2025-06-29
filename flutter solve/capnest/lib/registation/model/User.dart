class User {
  final int? id; // ✅ Add this line
  final String name;
  final String email;
  final String password;
  final String? phoneNo;
  final String? customer;
  final String? reseller;
  final String? username;
  final bool active;
  final bool isLock;
  final String role;

  User({
    this.id, // ✅ Add this line
    required this.name,
    required this.email,
    required this.password,
    this.phoneNo,
    this.customer,
    this.reseller,
    this.username,
    required this.active,
    required this.isLock,
    required this.role,
  });

  factory User.fromJson(Map<String, dynamic> json) {
    return User(
      id: json['id'], // ✅ Add this line
      name: json['name'] ?? '',
      email: json['email'],
      password: json['password'],
      phoneNo: json['phoneNo'],
      customer: json['customer'],
      reseller: json['reseller'],
      username: json['username'],
      active: json['active'],
      isLock: json['isLock'],
      role: json['role'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id, // ✅ Add this line
      'name': name,
      'email': email,
      'password': password,
      'phoneNo': phoneNo,
      'customer': customer,
      'reseller': reseller,
      'username': username,
      'active': active,
      'isLock': isLock,
      'role': role,
    };
  }
}
