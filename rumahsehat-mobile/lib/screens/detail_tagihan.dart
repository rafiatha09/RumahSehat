import 'package:flutter/material.dart';
import "package:rumahsehat_mobile/models/tagihan_model.dart";
import 'package:rumahsehat_mobile/models/appointment_model.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:rumahsehat_mobile/models/pasien_model.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:rumahsehat_mobile/screens/bayar_tagihan.dart';

class DetailTagihan extends StatelessWidget {
  final int index;
  final Tagihan tagihan;

  DetailTagihan(this.tagihan, this.index);

  Future<Appointment> fetchAppointmnetByTagihan() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String _jwtToken = prefs.get("token").toString();
    final response = await http.get(
        Uri.parse("https://apap-054.cs.ui.ac.id/api/v1/appointment/tagihan/" +
            tagihan.kode.toString()),
        headers: <String, String>{
          'Content-Type': 'application/json;charset=UTF-8',
          'Authorization': 'Bearer $_jwtToken'
        });
    if (response.statusCode == 200) {
      final body = jsonDecode(response.body);
      return Appointment.fromJson(body);
    } else {
      throw Exception('Failed to load Appointment');
    }
  }

  Future<void> updateTagihan(String kodeTagihan, int saldo, int jumlahTagihan,
      String tanggalBayar) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String _jwtToken = prefs.get("token").toString();
    final response = await http.put(
      Uri.parse("https://apap-054.cs.ui.ac.id/api/v1/tagihan/update"),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer $_jwtToken'
      },
      body: jsonEncode(<String, dynamic>{
        "kode": kodeTagihan,
        "jumlahTagihan": jumlahTagihan,
        "tanggalBayar": DateTime.now().toString().substring(0, 11),
        "paid": true,
        "appointment": {
          "pasien": {"saldo": saldo - jumlahTagihan}
        }
      }),
    );
    if (response.statusCode != 200) {
      throw Exception('Failed to load Appointment');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Detail Tagihan"),
          backgroundColor: Colors.green,
        ),
        body: Center(
          child: Card(
            shadowColor: Colors.green,
            elevation: 8,
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
            child: Container(
              padding: EdgeInsets.all(30),
              height: 350,
              width: 300,
              child: FutureBuilder<Appointment>(
                future: fetchAppointmnetByTagihan(),
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    Pasien? pasien = snapshot.data?.pasien;
                    int saldo = pasien!.saldo;
                    return Column(
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Text(
                          "Tagihan",
                          style: TextStyle(
                              fontSize: 20, fontWeight: FontWeight.bold),
                        ),
                        const SizedBox(height: 4),
                        const Divider(
                          height: 5,
                          thickness: 1,
                          indent: 0,
                          endIndent: 0,
                          color: Colors.grey,
                        ),
                        const SizedBox(height: 8),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            Text(tagihan.kode,
                                style: TextStyle(
                                    fontSize: 20, fontWeight: FontWeight.w600)),
                          ],
                        ),
                        const SizedBox(height: 2),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text("Tanggal Terbuat",
                                style: TextStyle(
                                  fontSize: 16,
                                )),
                            Text(
                              tagihan.tanggalTerbuat,
                              style: TextStyle(fontSize: 16),
                            )
                          ],
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text("Jumlah Tagihan",
                                style: TextStyle(
                                  fontSize: 16,
                                )),
                            Text(
                              tagihan.jumlahTagihan.toString(),
                              style: TextStyle(fontSize: 16),
                            )
                          ],
                        ),
                        const SizedBox(height: 15),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            Text("Status",
                                style: TextStyle(
                                    fontSize: 18, fontWeight: FontWeight.w600)),
                          ],
                        ),
                        const SizedBox(height: 2),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            if (tagihan.paid == true) ...[
                              Text("Sudah dibayar",
                                  style: TextStyle(
                                      fontSize: 14, color: Colors.green)),
                            ] else ...[
                              Text(
                                'Belum dibayar',
                                style:
                                    TextStyle(fontSize: 14, color: Colors.red),
                              ),
                            ]
                          ],
                        ),
                        const SizedBox(height: 2),
                        Row(
                          children: [
                            if (tagihan.paid) ...[
                              Row(
                                mainAxisAlignment:
                                    MainAxisAlignment.spaceBetween,
                                children: [
                                  Text("Tanggal Bayar",
                                      style: TextStyle(
                                        fontSize: 16,
                                      )),
                                  Text(
                                    tagihan.tanggalBayar,
                                    style: TextStyle(fontSize: 16),
                                  )
                                ],
                              ),
                            ] else ...[
                              const SizedBox(height: 50),
                              (tagihan.paid
                                  ? Text("")
                                  : Row(
                                      mainAxisAlignment:
                                          MainAxisAlignment.center,
                                      children: [
                                        TextButton(
                                            style: ButtonStyle(
                                              foregroundColor:
                                                  MaterialStateProperty.all(
                                                      Colors.white),
                                              backgroundColor:
                                                  MaterialStateProperty.all(
                                                      Colors.green),
                                              shape: MaterialStateProperty.all(
                                                  RoundedRectangleBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              10))),
                                            ),
                                            onPressed: () {
                                              showDialog<String>(
                                                  context: context,
                                                  builder:
                                                      (BuildContext context) =>
                                                          AlertDialog(
                                                            title: Text(
                                                                "Perhatian!"),
                                                            content: Text(
                                                                "Apakah Anda yakin untuk membayar tagihan ini?"),
                                                            actions: [
                                                              TextButton(
                                                                  onPressed: () =>
                                                                      Navigator.pop(
                                                                          context,
                                                                          "Batal"),
                                                                  child: const Text(
                                                                      "Batal")),
                                                              TextButton(
                                                                  onPressed:
                                                                      () {
                                                                    if (saldo >=
                                                                        tagihan
                                                                            .jumlahTagihan) {
                                                                      updateTagihan(
                                                                          tagihan
                                                                              .kode,
                                                                          saldo,
                                                                          tagihan
                                                                              .jumlahTagihan,
                                                                          tagihan
                                                                              .tanggalBayar);
                                                                      Navigator.push(
                                                                          context,
                                                                          MaterialPageRoute(
                                                                              builder: (context) => BayarTagihan(tagihan)));
                                                                    } else {
                                                                      Navigator.push(
                                                                          context,
                                                                          MaterialPageRoute(
                                                                              builder: (context) => BayarTagihanFailed(tagihan)));
                                                                    }
                                                                  },
                                                                  child: const Text(
                                                                      "Konfirmasi"))
                                                            ],
                                                          ));
                                            },
                                            child: Text("Bayar Tagihan")),
                                      ],
                                    ))
                            ]
                          ],
                        ),
                      ],
                    );
                  }
                  return CircularProgressIndicator();
                },
              ),
            ),
          ),
        ));
  }
}
