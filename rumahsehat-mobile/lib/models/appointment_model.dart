import 'dart:convert';


import 'package:rumahsehat_mobile/models/dokter_model.dart';
import 'package:rumahsehat_mobile/models/pasien_model.dart';

Appointment appointmentFromJson(String str) => Appointment.fromJson(json.decode(str));

String appointmentToJson(Appointment appointment) => json.encode(appointment.toJson());

class Appointment {
  late String kode;
  late String tanggalDimulai;
  late String waktuDimulai;
  late bool done;
  late Dokter dokter;
  late Pasien pasien;

  Appointment(
      { required this.kode,
        required this.waktuDimulai,
        required this.tanggalDimulai,
        required this.done,
        required this.dokter,
        required this.pasien});

  // Dari List json ke object
  factory Appointment.fromJsonList(dynamic object) {
    Dokter dokter = Dokter.fromJson(object["dokter"]);
    Pasien pasien = Pasien.fromJson(object['pasien']);
    return Appointment(
        kode : object['kode'],
        waktuDimulai : object['waktuDimulai'],
        tanggalDimulai : object['tanggalDimulai'],
        done: object['done'],
        dokter : dokter,
        pasien : pasien);
  }

  // Dari json ke object
  factory Appointment.fromJson(Map<String, dynamic> object) {
    Dokter dokter = Dokter.fromJson(object['dokter']);
    Pasien pasien = Pasien.fromJson(object['pasien']);
    return Appointment(
        kode : object['kode'],
        waktuDimulai : object['waktuDimulai'],
        tanggalDimulai : object['tanggalDimulai'],
        done: object['done'],
        dokter : dokter,
        pasien : pasien
    );
  }

  Map<String, dynamic> toJson() => {
    "kode" : kode,
    "waktuDimulai" : waktuDimulai,
    "tanggalDimulai" : tanggalDimulai,
    "done" : done,
    "dokter" : dokter,
    "pasien" : pasien
  };
}
