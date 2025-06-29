import 'dart:convert';
import 'package:capnest/addtocart/model/AddToCart.dart';
import 'package:capnest/homepage/model/Product.dart';
import 'package:capnest/registation/model/User.dart';

class OrderModel {
  final int? id;
  final String? phonenumber;
  final String? email;
  final String? mfs;
  final String? name;
  final String? productid;
  final String? productname;
  final String? accountnumber;
  final int? quantity;
  final double? price;
  final String? districts;
  final String? upazila;
  final String? address;
  final String? status;
  final DateTime? requestdate;

  final User? user;
  final AddToCart? addToCart;
  final List<Product>? productList;

  OrderModel({
    this.id,
    this.phonenumber,
    this.email,
    this.mfs,
    this.name,
    this.productid,
    this.productname,
    this.accountnumber,
    this.quantity,
    this.price,
    this.districts,
    this.upazila,
    this.address,
    this.status,
    this.requestdate,
    this.user,
    this.addToCart,
    this.productList,
  });

  factory OrderModel.fromJson(Map<String, dynamic> json) {
    return OrderModel(
      id: json['id'],
      phonenumber: json['phonenumber'],
      email: json['email'],
      mfs: json['mfs'],
      name: json['name'],
      productid: json['productid'],
      productname: json['productname'],
      accountnumber: json['accountnumber'],
      quantity: json['quantity'],
      price: (json['price'] != null) ? json['price'].toDouble() : null,
      districts: json['districts'],
      upazila: json['upazila'],
      address: json['address'],
      status: json['status'],
      requestdate: json['requestdate'] != null
          ? DateTime.parse(json['requestdate'])
          : null,
      user: json['user'] != null ? User.fromJson(json['user']) : null,
      addToCart:
      json['addToCart'] != null ? AddToCart.fromJson(json['addToCart']) : null,
      productList: json['productList'] != null
          ? (json['productList'] as List)
          .map((e) => Product.fromJson(e))
          .toList()
          : [],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'phonenumber': phonenumber,
      'email': email,
      'mfs': mfs,
      'name': name,
      'productid': productid,
      'productname': productname,
      'accountnumber': accountnumber,
      'quantity': quantity,
      'price': price,
      'districts': districts,
      'upazila': upazila,
      'address': address,
      'status': status,
      'requestdate': requestdate?.toIso8601String(),
      'user': user?.toJson(),
      'addToCart': addToCart?.toJson(),
      'productList': productList?.map((p) => p.toJson()).toList(),
    };
  }
}
