import 'dart:convert';

import 'package:rumahsehat_mobile/models/obat_mode.dart';

JumlahObat jumlahobatFromJson(String str) =>
    JumlahObat.fromJson(json.decode(str));

String jumlahobatToJson(JumlahObat jumlahObat) =>
    json.encode(jumlahObat.toJson());

class JumlahObat {
  late int id;
  late int kuantitas;
  late Obat obat;

  JumlahObat({required this.id, required this.kuantitas, required this.obat});

  // Dari json ke object
  factory JumlahObat.fromJsonList(dynamic object) {
    Obat obat = Obat.fromJson(object['obat']);
    return JumlahObat(
      id: object['id'],
      kuantitas: object['kuantitas'],
      obat: obat,
    );
  }

  factory JumlahObat.fromJson(Map<String, dynamic> object) {
    Obat obat = Obat.fromJson(object['obat']);
    return JumlahObat(
      id: object['id'],
      kuantitas: object['kuantitas'],
      obat: obat,
    );
  }

  Map<String, dynamic> toJson() =>
      {"id": id, "kuantitas": kuantitas, "obat": obat};
}
