import 'package:flutter/material.dart';
import "package:rumahsehat_mobile/models/tagihan_model.dart";
import 'package:rumahsehat_mobile/models/appointment_model.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';
import 'package:rumahsehat_mobile/models/pasien_model.dart';
import 'package:rumahsehat_mobile/screens/home_screen.dart';
import 'package:shared_preferences/shared_preferences.dart';

class BayarTagihan extends StatelessWidget {
  final Tagihan tagihan;

  BayarTagihan(this.tagihan);

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

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Bayar Tagihan"),
          backgroundColor: Colors.green,
        ),
        body: Center(
            child: Card(
                shadowColor: Colors.green,
                elevation: 8,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(20)),
                child: Container(
                  padding: EdgeInsets.all(30),
                  height: 270,
                  width: 300,
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      FutureBuilder<Appointment>(
                          future: fetchAppointmnetByTagihan(),
                          builder: (context, snapshot) {
                            if (snapshot.hasData) {
                              Pasien? pasien = snapshot.data?.pasien;
                              int saldo = pasien!.saldo;
                              return Center(
                                  child: Column(
                                children: [
                                  Text(
                                    "Tagihan lunas",
                                    style: TextStyle(
                                        fontSize: 20,
                                        fontWeight: FontWeight.bold),
                                  ),
                                  Text("Saldo Anda berkurang sebanyak",
                                      style: TextStyle(
                                        fontSize: 16,
                                      )),
                                  Text(
                                    tagihan.jumlahTagihan.toString(),
                                    style: TextStyle(fontSize: 16),
                                  ),
                                  ElevatedButton(
                                      onPressed: () => Navigator.push(
                                          context,
                                          MaterialPageRoute(
                                              builder: (context) =>
                                                  MainScreen())),
                                      child: const Text("Kembali")),
                                ],
                              ));
                            }
                            return CircularProgressIndicator();
                          }),
                    ],
                  ),
                ))));
  }
}

class BayarTagihanFailed extends StatelessWidget {
  final Tagihan tagihan;

  BayarTagihanFailed(this.tagihan);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Bayar Tagihan"),
          backgroundColor: Colors.green,
        ),
        body: Center(
            child: Card(
                shadowColor: Colors.green,
                elevation: 8,
                shape: RoundedRectangleBorder(
                    borderRadius: BorderRadius.circular(20)),
                child: Container(
                  padding: EdgeInsets.all(30),
                  height: 270,
                  width: 300,
                  child: Column(
                    children: [
                      const SizedBox(
                        height: 50,
                      ),
                      Text(
                        "Saldo Anda tidak cukup",
                        style: TextStyle(
                            fontSize: 18, fontWeight: FontWeight.bold),
                      ),
                      const SizedBox(
                        height: 15,
                      ),
                      Text(
                        "Silakan top-up terlebih dahulu",
                        style: TextStyle(
                          fontSize: 17,
                        ),
                      ),
                      const SizedBox(
                        height: 50,
                      ),
                      ElevatedButton(
                          onPressed: () => Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => MainScreen())),
                          child: const Text("Kembali")),
                    ],
                  ),
                ))));
  }
}
