import 'dart:convert';
import 'package:rumahsehat_mobile/models/apoteker_model.dart';


Resep resepFromJson(String str) => Resep.fromJson(json.decode(str));

String resepToJson(Resep resep) => json.encode(resep.toJson());

class Resep {
  late int id;
  late bool isDone;
  late String createdAt;
  late Apoteker apoteker;

  Resep(
      {required this.id,
      required this.isDone,
      required this.createdAt,
      required this.apoteker});

  // Dari json ke object
  factory Resep.fromJson(Map<String, dynamic> object) {
    if(object['apoteker'] == null){
      Apoteker dummmy = Apoteker(uuid: "uuid", nama: "dummy", username: "dummy", password: "dummy", email: "dummy", role: "Apoteker");
      return Resep(
        id: object['id'],
        isDone: object['done'],
        createdAt: object['createdAt'],
        apoteker: dummmy,
      );
    }
    Apoteker apoteker = Apoteker.fromJson(object['apoteker']);
    return Resep(
      id: object['id'],
      isDone: object['done'],
      createdAt: object['createdAt'],
      apoteker: apoteker,
    );
  }

  factory Resep.fromJsonList(dynamic object) {
    Apoteker apoteker = Apoteker.fromJson(object['apoteker']);
    return Resep(
      id: object['id'],
      isDone: object['done'],
      createdAt: object['createdAt'],
      apoteker: apoteker,
    );
  }

  Map<String, dynamic> toJson() => {
        "id": id,
        "isDone": isDone,
        "createdAt": createdAt,
        "apoteker": apoteker
      };
}
