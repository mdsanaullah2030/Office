import 'package:capnest/homepage/model/Product.dart';
import 'package:capnest/registation/model/User.dart';



class AddToCart {
  final int? id;
  final int? totalprice;
  final User? user;
  final Product? product;
  final int? quantity;
  final double? price;

  AddToCart({
    this.id,
    this.totalprice,
    this.user,
    this.product,
    this.quantity,
    this.price,
  });

  factory AddToCart.fromJson(Map<String, dynamic> json) {
    return AddToCart(
      id: json['id'],
      totalprice: json['totalprice'],
      user: json['user'] != null ? User.fromJson(json['user']) : null,
      product: json['product'] != null ? Product.fromJson(json['product']) : null,
      quantity: json['quantity'],
      price: (json['price'] != null) ? json['price'].toDouble() : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'totalprice': totalprice,
      'user': user?.toJson(),
      'product': product?.toJson(),
      'quantity': quantity,
      'price': price,
    };
  }
}
