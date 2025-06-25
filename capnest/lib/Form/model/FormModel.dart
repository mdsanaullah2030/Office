class FormModel {
  int? id;
  String fullName;
  String email;
  String phoneNumber;
  String address;
  String gender;
  DateTime dateOfBirth;
  String nationalId;

  FormModel({
    this.id,
    required this.fullName,
    required this.email,
    required this.phoneNumber,
    required this.address,
    required this.gender,
    required this.dateOfBirth,
    required this.nationalId,
  });

  factory FormModel.fromJson(Map<String, dynamic> json) {
    return FormModel(
      id: json['id'],
      fullName: json['fullName'],
      email: json['email'],
      phoneNumber: json['phoneNumber'],
      address: json['address'],
      gender: json['gender'],
      dateOfBirth: DateTime.parse(json['dateOfBirth']),
      nationalId: json['nationalId'],
    );
  }

  Map<String, dynamic> toJson() {
    return {
      if (id != null) 'id': id,
      'fullName': fullName,
      'email': email,
      'phoneNumber': phoneNumber,
      'address': address,
      'gender': gender,
      'dateOfBirth': dateOfBirth.toIso8601String(),
      'nationalId': nationalId,
    };
  }
}
