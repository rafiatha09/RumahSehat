import 'dart:convert';


Obat obatFromJson(String str) => Obat.fromJson(json.decode(str));

String obatToJson(Obat obat) => json.encode(obat.toJson());

class Obat {
  late String id;
  late String nama_obat;
  late int stok;
  late int harga;

  Obat({
    required this.id,
    required this.nama_obat,
    required this.stok,
    required this.harga,
  });

  // Dari json ke object
  factory Obat.fromJsonList(dynamic object) {
    return Obat(
        id: object['id'],
        nama_obat: object['nama_obat'],
        stok: object['stok'],
        harga: object['harga']);
  }

  factory Obat.fromJson(Map<String, dynamic> object) {
    return Obat(
        id: object['id'],
        nama_obat: object['nama_obat'],
        stok: object['stok'],
        harga: object['harga']);
  }

  Map<String, dynamic> toJson() => {
        "id": id,
        "nama_obat": nama_obat,
        "stok": stok,
        "harga": harga,
      };
}
