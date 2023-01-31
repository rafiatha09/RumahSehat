import 'package:flutter/material.dart';
import "package:rumahsehat_mobile/models/appointment_model.dart";
import 'package:rumahsehat_mobile/models/resep_model.dart';
import "package:rumahsehat_mobile/screens/detail_resep.dart";
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';

import 'package:shared_preferences/shared_preferences.dart';

class DetailAppointment extends StatelessWidget {
  final int index;
  final Appointment appointment;
  final String time;

  DetailAppointment(this.appointment, this.index, this.time);

  Future<Resep> getResepFromAppoinment() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String idUser = prefs.get('id').toString();
    String _jwtToken = prefs.get("token").toString();
    final response = await http.get(
        Uri.parse("https://apap-054.cs.ui.ac.id/api/v1/resep/appointment/" +
            appointment.kode.toString()),
        headers: <String, String>{
          'Content-Type': 'application/json;charset=UTF-8',
          'Authorization': 'Bearer $_jwtToken'
        });
    if (response.statusCode == 200) {
      final body = jsonDecode(response.body);
      print(body);
      return Resep.fromJson(body);
    } else {
      throw Exception('Failed to load Appointment');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Detail Appointment"),
          backgroundColor: Colors.green,
        ),
        body: Center(
          child: Card(
            shadowColor: Colors.green,
            elevation: 8,
            shape:
                RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
            child: Container(
              padding: EdgeInsets.all(15),
              height: 455,
              width: 300,
              // width: MediaQuery.of(context).size.width / 2,
              // padding: const EdgeInsets.symmetric(vertical: 10, horizontal: 90),
              child: Column(
                // mainAxisAlignment: MainAxisAlignment.center,
                crossAxisAlignment: CrossAxisAlignment.center,
                children: [
                  Text(
                    "Appointment",
                    style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
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
                      Text(appointment.kode,
                          style: TextStyle(
                              fontSize: 20, fontWeight: FontWeight.w600)),
                    ],
                  ),
                  const SizedBox(height: 2),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text("Tanggal",
                          style: TextStyle(
                            fontSize: 16,
                          )),
                      Text(
                        appointment.tanggalDimulai,
                        style: TextStyle(fontSize: 16),
                      )
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text("Waktu",
                          style: TextStyle(
                            fontSize: 16,
                          )),
                      Text(
                        appointment.waktuDimulai +
                            " - " +
                            time.substring(10, 15),
                        style: TextStyle(fontSize: 16),
                      )
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text("Status",
                          style: TextStyle(
                            fontSize: 16,
                          )),
                      (appointment.done
                          ? Text(
                              'Done',
                              style:
                                  TextStyle(fontSize: 14, color: Colors.green),
                            )
                          : Text(
                              'Not Yet',
                              style: TextStyle(fontSize: 14, color: Colors.red),
                            ))
                    ],
                  ),
                  const SizedBox(height: 8),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      Text("Pasien",
                          style: TextStyle(
                              fontSize: 20, fontWeight: FontWeight.w600)),
                    ],
                  ),
                  const SizedBox(height: 2),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text("Nama",
                          style: TextStyle(
                            fontSize: 16,
                          )),
                      Text(
                        appointment.pasien.nama,
                        style: TextStyle(fontSize: 16),
                      )
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text("Email",
                          style: TextStyle(
                            fontSize: 16,
                          )),
                      Text(
                        appointment.pasien.email.toString(),
                        style: TextStyle(fontSize: 16),
                      )
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text("Umur",
                          style: TextStyle(
                            fontSize: 16,
                          )),
                      Text(
                        appointment.pasien.umur.toString(),
                        style: TextStyle(fontSize: 16),
                      )
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text("Saldo",
                          style: TextStyle(
                            fontSize: 16,
                          )),
                      Text(
                        appointment.pasien.saldo.toString(),
                        style: TextStyle(fontSize: 16),
                      )
                    ],
                  ),
                  const SizedBox(height: 8),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.start,
                    children: [
                      Text("Dokter",
                          style: TextStyle(
                              fontSize: 20, fontWeight: FontWeight.w600)),
                    ],
                  ),
                  const SizedBox(height: 2),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text("Nama",
                          style: TextStyle(
                            fontSize: 16,
                          )),
                      Text(
                        appointment.dokter.nama,
                        style: TextStyle(fontSize: 16),
                      )
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text("Email",
                          style: TextStyle(
                            fontSize: 16,
                          )),
                      Text(
                        appointment.dokter.email,
                        style: TextStyle(fontSize: 16),
                      )
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text("Tarif",
                          style: TextStyle(
                            fontSize: 16,
                          )),
                      Text(
                        appointment.dokter.tarif.toString(),
                        style: TextStyle(fontSize: 16),
                      )
                    ],
                  ),
                  const SizedBox(height: 10),
                  FutureBuilder<Resep>(
                      future: getResepFromAppoinment(),
                      builder: (context, snapshot) {
                        return SizedBox(
                          height: 40,
                          child: TextButton(
                              style: ButtonStyle(
                                foregroundColor:
                                    MaterialStateProperty.all(Colors.white),
                                backgroundColor:
                                    MaterialStateProperty.all(Colors.green),
                                shape: MaterialStateProperty.all(
                                    RoundedRectangleBorder(
                                        borderRadius:
                                            BorderRadius.circular(10))),
                              ),
                              onPressed: () {
                                print(snapshot.data);
                                if (snapshot.hasData) {
                                  Resep? resep = snapshot.data;
                                  Navigator.push(
                                      context,
                                      MaterialPageRoute(
                                          builder: (context) => DetailResep(
                                              resep!,
                                              appointment.pasien,
                                              appointment.dokter)));
                                  // print(snapshot.data?
                                } else {
                                  showDialog(
                                    context: context,
                                    builder: (context) => AlertDialog(
                                      title: Text('Attention'),
                                      content: Text(
                                          'Your detail resep is not exist'),
                                      actions: [
                                        ElevatedButton(
                                            onPressed: () {
                                              Navigator.pop(context);
                                            },
                                            child: Text('Back'))
                                      ],
                                    ),
                                  );
                                }
                              },
                              child: Text("Detail Resep")),
                        );
                      }),
                ],
              ),
            ),
          ),
        ));
  }
}
