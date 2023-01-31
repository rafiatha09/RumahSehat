import 'package:flutter/material.dart';
import 'package:http/http.dart' as http;
import 'dart:convert';
import 'dart:async';
import 'package:rumahsehat_mobile/models/resep_model.dart';
import 'package:flutter/foundation.dart';
import 'package:rumahsehat_mobile/models/pasien_model.dart';
import 'package:rumahsehat_mobile/models/dokter_model.dart';
import 'package:rumahsehat_mobile/models/jumlahobat_model.dart';
import 'package:shared_preferences/shared_preferences.dart';

class DetailResep extends StatelessWidget {
  final Resep resep;
  final Pasien pasien;
  final Dokter dokter;

  DetailResep(this.resep, this.pasien, this.dokter);

  late int jumlahObat = 0;

  Future<List<JumlahObat>> fetchListObat() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String idUser = prefs.get('id').toString();
    String _jwtToken = prefs.get("token").toString();
    final response = await http.get(
        Uri.parse("https://apap-054.cs.ui.ac.id/api/v1/list-jumlah/resep/" +
            resep.id.toString()),
        headers: <String, String>{
          'Content-Type': 'application/json;charset=UTF-8',
          'Authorization': 'Bearer $_jwtToken'
        });
    if (response.statusCode == 200) {
      final body = jsonDecode(response.body);
      return body.map<JumlahObat>(JumlahObat.fromJsonList).toList();
    } else {
      throw Exception('Failed to load Appointment');
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: Text("Detail Resep"),
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
              width: 600,
              height: MediaQuery.of(context).size.height,
              child: Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    Text(
                      "Resep",
                      style:
                          TextStyle(fontSize: 20, fontWeight: FontWeight.w600),
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
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text("Pasien",
                            style: TextStyle(
                              fontSize: 16,
                            )),
                        Text(
                          pasien.nama,
                          style: TextStyle(fontSize: 16),
                        )
                      ],
                    ),
                    const SizedBox(height: 4),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text("Dokter",
                            style: TextStyle(
                              fontSize: 16,
                            )),
                        Text(
                          dokter.nama,
                          style: TextStyle(fontSize: 16),
                        )
                      ],
                    ),
                    const SizedBox(height: 4),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text("Apoteker",
                            style: TextStyle(
                              fontSize: 16,
                            )),
                        Text(
                          (resep.apoteker.nama == "dummy"
                              ? "-"
                              : resep.apoteker.nama),
                          style: TextStyle(fontSize: 16),
                        )
                      ],
                    ),
                    const SizedBox(height: 2),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.spaceBetween,
                      children: [
                        Text("Status",
                            style: TextStyle(
                              fontSize: 16,
                            )),
                        (resep.isDone
                            ? Text(
                                'Terverifikasi',
                                style: TextStyle(
                                    fontSize: 14, color: Colors.green),
                              )
                            : Text(
                                'Belum Terverifikasi',
                                style:
                                    TextStyle(fontSize: 14, color: Colors.red),
                              ))
                      ],
                    ),
                    const SizedBox(height: 10),
                    const Divider(
                      height: 5,
                      thickness: 1,
                      indent: 0,
                      endIndent: 0,
                      color: Colors.grey,
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.start,
                      children: [
                        Text("Daftar Obat",
                            style: TextStyle(
                                fontSize: 20, fontWeight: FontWeight.w600)),
                      ],
                    ),
                    FutureBuilder<List<JumlahObat>>(
                        future: fetchListObat(),
                        builder: (context, snapshot) {
                          if (snapshot.hasData) {
                            List<JumlahObat>? listJumlahObat = snapshot.data;
                            // jumlahObat = (listJumlahObat!.length + 1) * 900;
                            return ListView.builder(
                              scrollDirection: Axis.vertical,
                              shrinkWrap: true,
                              itemCount: listJumlahObat?.length,
                              itemBuilder: (BuildContext context, index) {
                                return ObatCard(
                                  nama: listJumlahObat![index].obat.nama_obat,
                                  kuantitas: listJumlahObat[index].kuantitas,
                                  harga:
                                      listJumlahObat[index].obat.harga as int,
                                );
                              },
                            );
                          }
                          return CircularProgressIndicator();
                        }),
                  ])),
        )));
  }
}

class ObatCard extends StatelessWidget {
  const ObatCard({
    Key? key,
    required this.nama,
    required this.kuantitas,
    required this.harga,
  }) : super(key: key);

  final String nama;
  final int kuantitas;
  final int harga;

  @override
  Widget build(BuildContext context) {
    return Container(
      width: 150,
      // height: 400,
      margin: EdgeInsets.only(right: 15),
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(10),
      ),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          SizedBox(
            height: 4,
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text("Nama Obat",
                  style: TextStyle(
                    fontSize: 16,
                  )),
              Text(
                nama,
                style: TextStyle(fontSize: 16),
              )
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text("Harga Satuan",
                  style: TextStyle(
                    fontSize: 16,
                  )),
              Text(
                harga.toString(),
                style: TextStyle(fontSize: 16),
              )
            ],
          ),
          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              Text("Kuantitas",
                  style: TextStyle(
                    fontSize: 16,
                  )),
              Text(
                kuantitas.toString(),
                style: TextStyle(fontSize: 16),
              )
            ],
          )
        ],
      ),
    );
  }
}
