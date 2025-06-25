
class HomeScreen {
  int? id;
  String? imagea;

  HomeScreen({this.id, this.imagea});

  // Convert JSON to HomeScreen object
  factory HomeScreen.fromJson(Map<String, dynamic> json) {
    return HomeScreen(
      id: json['id'],
      imagea: json['imagea'],
    );
  }

  // Convert HomeScreen object to JSON
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'imagea': imagea,
    };
  }
}