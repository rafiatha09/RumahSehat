import 'dart:convert';



Dokter dokterFromJson(String str) => Dokter.fromJson(json.decode(str));

String dokterToJson(Dokter dokter) => json.encode(dokter.toJson());

class Dokter {
  late String uuid;
  late String nama;
  late String username;
  late String password;
  late String email;
  late String role;
  late int tarif;

  Dokter(
      {required this.uuid,
        required this.nama,
        required this.username,
        required this.password,
        required this.email,
        required this.role,
        required this.tarif});

  // Dari json ke object
  factory Dokter.fromJsonList(dynamic object) {
    return Dokter(
        uuid: object['uuid'],
        nama: object['nama'],
        username: object['username'],
        password: object['password'],
        email: object['email'],
        role: object['role'],
        tarif: object['tarif']);
  }
  factory Dokter.fromJson(Map<String, dynamic> object) {
    return Dokter(
        uuid: object['uuid'],
        nama: object['nama'],
        username: object['username'],
        password: object['password'],
        email: object['email'],
        role: object['role'],
        tarif: object['tarif']);
  }

  Map<String, dynamic> toJson() => {
    "uuid":uuid,
    "nama": nama,
    "username": username,
    "password": password,
    "email": email,
    "role": role,
    "tarif": tarif
  };
}
