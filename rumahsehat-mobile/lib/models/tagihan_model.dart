import 'dart:convert';

import 'package:rumahsehat_mobile/models/appointment_model.dart';

Tagihan tagihanFromJson(String str) => Tagihan.fromJson(json.decode(str));

String tagihanToJson(Tagihan tagihan) => json.encode(tagihan.toJson());

class Tagihan {
  late String kode;
  late String tanggalTerbuat;
  late String tanggalBayar;
  late int jumlahTagihan;
  late bool paid;
  late Appointment appointment;
  Tagihan(
      {required this.kode,
      required this.tanggalTerbuat,
      required this.tanggalBayar,
      required this.jumlahTagihan,
      required this.paid,});

  // Dari List json ke object
  factory Tagihan.fromJsonList(dynamic object) {
    if(object['tanggalBayar'] == null){
      object['tanggalBayar'] = "null";
    }
    return Tagihan(
      kode: object['kode'],
      tanggalTerbuat: object['tanggalTerbuat'],
      tanggalBayar: object['tanggalBayar'],
      jumlahTagihan: object['jumlahTagihan'],
      paid: object['paid'],
    );
  }

// Dari json ke object
  factory Tagihan.fromJson(Map<String, dynamic> object) {
    // Appointment appointment = Appointment.fromJson(object['appointment']);
    return Tagihan(
      kode: object['kode'],
      tanggalTerbuat: object['tanggalTerbuat'],
      tanggalBayar: object['tanggalBayar'],
      jumlahTagihan: object['jumlahTagihan'],
      paid: object['paid'],
    );
  }

  Map<String, dynamic> toJson() => {
        "kode": kode,
        "tanggalTerbuat": tanggalTerbuat,
        "tanggalBayar": tanggalBayar,
        "jumlahTagihan": jumlahTagihan,
      };
}
