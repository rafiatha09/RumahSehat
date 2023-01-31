import 'dart:convert';

Apoteker apotekerFromJson(String str) => Apoteker.fromJson(json.decode(str));

String apotkerToJson(Apoteker apoteker) => json.encode(apoteker.toJson());

class Apoteker {
  late String uuid;
  late String nama;
  late String username;
  late String password;
  late String email;
  late String role;

  Apoteker(
      {required this.uuid,
        required this.nama,
        required this.username,
        required this.password,
        required this.email,
        required this.role});

  // Dari json ke object
  factory Apoteker.fromJsonList(dynamic object) {
    return Apoteker(
        uuid: object['uuid'],
        nama: object['nama'],
        username: object['username'],
        password: object['password'],
        email: object['email'],
        role: object['role']);
  }
  factory Apoteker.fromJson(Map<String, dynamic> object) {
    return Apoteker(
        uuid: object['uuid'],
        nama: object['nama'],
        username: object['username'],
        password: object['password'],
        email: object['email'],
        role: object['role'],);
  }

  Map<String, dynamic> toJson() => {
    "uuid":uuid,
    "nama": nama,
    "username": username,
    "password": password,
    "email": email,
    "role": role
  };
}
