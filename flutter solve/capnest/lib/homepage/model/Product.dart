import 'Category.dart';

class Product {
  final int id;
  final String productname;
  final double specialprice;
  final double regularprice;
  final int quantity;
  final String model;
  final int rating;
  final String description;
  final int warranty;
  final String salesservice;
  final String policy;
  final double offer;
  final String imagea;
  final String imageb;
  final String imagec;
  final String imaged;
  final Category? category;

  Product({
    required this.id,
    required this.productname,
    required this.specialprice,
    required this.regularprice,
    required this.quantity,
    required this.model,
    required this.rating,
    required this.description,
    required this.warranty,
    required this.salesservice,
    required this.policy,
    required this.offer,
    required this.imagea,
    required this.imageb,
    required this.imagec,
    required this.imaged,
    this.category,
  });

  factory Product.fromJson(Map<String, dynamic> json) {
    return Product(
      id: json['id'] ?? 0,
      productname: json['productname'] ?? '',
      specialprice: (json['specialprice'] ?? 0).toDouble(),
      regularprice: (json['regularprice'] ?? 0).toDouble(),
      quantity: json['quantity'] ?? 0,
      model: json['model'] ?? '',
      rating: json['rating'] ?? 0,
      description: json['description'] ?? '',
      warranty: json['warranty'] ?? 0,
      salesservice: json['salesservice'] ?? '',
      policy: json['policy'] ?? '',
      offer: (json['offer'] ?? 0).toDouble(),
      imagea: json['imagea'] ?? '',
      imageb: json['imageb'] ?? '',
      imagec: json['imagec'] ?? '',
      imaged: json['imaged'] ?? '',
      category: json['catagory'] != null
          ? Category.fromJson(json['catagory'])
          : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'productname': productname,
      'specialprice': specialprice,
      'regularprice': regularprice,
      'quantity': quantity,
      'model': model,
      'rating': rating,
      'description': description,
      'warranty': warranty,
      'salesservice': salesservice,
      'policy': policy,
      'offer': offer,
      'imagea': imagea,
      'imageb': imageb,
      'imagec': imagec,
      'imaged': imaged,
      'catagory': category?.toJson(),
    };
  }
}