import 'dart:convert';


Pasien pasienFromJson(String str) => Pasien.fromJson(json.decode(str));

String pasienToJson(Pasien pasien) => json.encode(pasien.toJson());

class Pasien {
  late String uuid;
  late String nama;
  late String role;
  late String username;
  late String password;
  late String email;
  late int saldo;
  late int umur;

  Pasien({
    required this.uuid,
    required this.nama,
    required this.role,
    required this.username,
    required this.password,
    required this.email,
    required this.saldo,
    required this.umur,
  });

  // Dari json ke object
  factory Pasien.fromJson(Map<String, dynamic> object) {
    return Pasien(
        uuid: object['uuid'],
        nama: object['nama'],
        role: object['role'],
        username: object['username'],
        password: object['password'],
        email: object['email'],
        saldo: object['saldo'],
        umur: object['umur']);
  }

  factory Pasien.fromJsonList(dynamic object) {
    return Pasien(
        uuid: object['uuid'],
        nama: object['nama'],
        role: object['role'],
        username: object['username'],
        password: object['password'],
        email: object['email'],
        saldo: object['saldo'],
        umur: object['umur']);
  }

  Map<String, dynamic> toJson() => {
        "uuid": uuid,
        "nama": nama,
        "role": role,
        "username": username,
        "password": password,
        "email": email,
        "saldo": saldo,
        "umur": umur
      };
}
// factory Pasien.createPostResult(Map<String, dynamic> object) {
//   return Pasien(
//       nama: object['nama'],
//       role: object['role'],
//       username: object['username'],
//       password: object['password'],
//       email: object['email'],
//       saldo: object['saldo'],
//       umur: object['umur']);
// }
// Future<Pasien> connectToAPI(Map<String, dynamic> data) async{
//   var apiUrl = Uri.parse("localhost:8080/api/v1/pasien/add");
//   var apiResult = await http.post(apiUrl, body: data);
//   var jsonObject = json.decode(apiResult.toString());
//   return Pasien.createPostResult(jsonObject);

// Future<Pasien> connectToAPI(String name, String job) async{
//  var apiUrl = Uri.parse("localhost:8080/api/v1/pasien/add");
//  var apiResult = await http.post(apiUrl, body: {
//    "nama": nama,
//    "role": role,
//    "username": username,
//    "password": password,
//    "email": email,
//    "saldo": saldo,
//    "umur": umur
//  });
//  var jsonObject = json.decode(apiResult.toString());
//  return Pasien.createPostResult(jsonObject);
// }
