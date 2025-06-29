class Category {
  final int id;
  final String categoryName;

  Category({
    required this.id,
    required this.categoryName,
  });

  factory Category.fromJson(Map<String, dynamic> json) {
    return Category(
      id: json['id'] ?? 0,
      categoryName: json['categoryname'] ?? '',
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'categoryname': categoryName,
    };
  }
}