import 'dart:async';
import 'dart:convert';
import 'dart:js_util';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter/material.dart';
import 'package:rumahsehat_mobile/models/appointment_model.dart';
import 'package:rumahsehat_mobile/models/pasien_model.dart';
import 'package:rumahsehat_mobile/screens/home_screen.dart';
import 'package:rumahsehat_mobile/models/dokter_model.dart';
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';
import 'package:fluttertoast/fluttertoast.dart';

class MembuatAppointment extends StatefulWidget {
  static const routeName = '/membuat-apointment';

  const MembuatAppointment({super.key});

  @override
  State<MembuatAppointment> createState() => _MembuatAppointmentState();
}

class _MembuatAppointmentState extends State<MembuatAppointment> {
  TextEditingController timeinput = TextEditingController();
  TextEditingController dateinput = TextEditingController();
  TextEditingController dokterinput = TextEditingController();
  TextEditingController pasieninput = TextEditingController();
  Pasien? pasien;
  Dokter? dokter;
  var variable = 0;
  late Future<List<Dokter>> _futurelistFutureDokter;

  var dataAppointment = {
    "kode": "",
    "waktuDimulai": "",
    "tanggalDimulai": "",
    "dokter": objectPrototype,
    "pasien": objectPrototype,
  };

  Future<void> logedUser() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String idUser = prefs.get('id').toString();
    String _jwtToken = prefs.get("token").toString();
    final response = await http.get(
      Uri.parse("https://apap-054.cs.ui.ac.id/api/v1/pasien/" + idUser),
      headers: <String, String>{
        'Content-Type': 'application/json;charset=UTF-8',
        'Authorization': 'Bearer $_jwtToken'
      },
    );
    if (response.statusCode == 200) {
      final body = jsonDecode(response.body);
      Pasien pasien = Pasien.fromJson(body);
      dataAppointment['pasien'] = pasien;
    } else {
      throw Exception('Failed to load Pasien');
    }
  }

  Future<List<Dokter>> fetchListDokter() async {
    logedUser();
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String _jwtToken = prefs.get("token").toString();
    final response = await http.get(
      Uri.parse("https://apap-054.cs.ui.ac.id/api/v1/list-dokter"),
      headers: <String, String>{
        'Content-Type': 'application/json;charset=UTF-8',
        'Authorization': 'Bearer $_jwtToken'
      },
    );
    if (response.statusCode == 200) {
      final body = jsonDecode(response.body);
      return body.map<Dokter>(Dokter.fromJsonList).toList();
    } else {
      throw Exception('Failed to load Dokter');
    }
  }

  Future<bool?> toast(String message) {
    return Fluttertoast.showToast(
        msg: message,
        toastLength: Toast.LENGTH_LONG,
        gravity: ToastGravity.BOTTOM,
        timeInSecForIosWeb: 4,
        backgroundColor: Colors.blueAccent,
        textColor: Colors.white,
        fontSize: 15.0);
  }

  Future<Object> submitData(
      Map<String, dynamic> object, String time, String tanggal) async {
    pasien = object['pasien'];
    dokter = object['dokter'];
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String _jwtToken = prefs.get("token").toString();
    final response = await http.post(
      Uri.parse("https://apap-054.cs.ui.ac.id/api/v1/appointment/add"),
      headers: <String, String>{
        'Content-Type': 'application/json; charset=UTF-8',
        'Authorization': 'Bearer $_jwtToken'
      },
      body: jsonEncode(<String, dynamic>{
        "kode": object['kode'],
        "waktuDimulai": dataAppointment['waktuDimulai'],
        "tanggalDimulai": dataAppointment['tanggalDimulai'],
        "dokter": dokter,
        "pasien": pasien,
      }),
    );
    if (response.statusCode == 200) {
      toast("Successfully created appointment");
      Timer(Duration(seconds: 2), () {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => MainScreen()),
        );
      });
      return Appointment.fromJson(jsonDecode(response.body));
    } else {
      toast("Sudah ada appointment ditanggal tersebut!");
      return ArgumentError.notNull();
      // throw Exception("Tanggal atau waktu tidak valid!");
    }
  }

  //text editing controller for text field

  @override
  void initState() {
    timeinput.text = ""; //set the initial value of text field
    dateinput.text = ""; //set the initial value of text field
    dokterinput.text = "";
    _futurelistFutureDokter = fetchListDokter();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(
            Icons.arrow_back,
            color: Colors.white,
          ),
          onPressed: () => Navigator.push(
            context,
            MaterialPageRoute(builder: (context) => MainScreen()),
          ),
        ),
        title: Text("Membuat Appointment"),
        backgroundColor: Colors.green,
      ),
      body: Center(
        child: ListView(
          padding: EdgeInsets.all(32),
          children: [
            FutureBuilder<List<Dokter>>(
                future: _futurelistFutureDokter, // memanggil data data dokter
                builder: (context, snapshot) {
                  if (snapshot.hasData) {
                    final dokter = snapshot.data!;
                    return DropdownButtonFormField(
                      items: dokter.map((Dokter dok) {
                        return DropdownMenuItem(
                          value: dok,
                          child: Text(dok.nama + " - " + dok.tarif.toString()),
                        );
                      }).toList(),
                      onChanged: (Dokter? value) {
                        dataAppointment['dokter'] = value;
                      },
                      decoration: InputDecoration(
                        prefixIcon: Icon(
                            IconData(0xf06fc, fontFamily: 'MaterialIcons')),
                        labelText: "Cari Dokter",
                        border: OutlineInputBorder(),
                      ),
                    );
                  }
                  return CircularProgressIndicator();
                }),
            const SizedBox(height: 20),
            TextField(
              controller: dateinput,
              //editing controller of this TextField
              decoration: InputDecoration(
                prefixIcon: Icon(Icons.calendar_today), //icon of text field
                hintText: dateinput.text,
                labelText: "Tanggal Mulai", //label text of field
                border: OutlineInputBorder(),
              ),
              readOnly: true,
              onTap: () async {
                DateTime? pickedDate = await showDatePicker(
                    context: context,
                    initialDate: DateTime.now(),
                    firstDate: DateTime(2000),
                    lastDate: DateTime(2101));

                if (pickedDate != null) {
                  String formattedDate =
                      DateFormat('yyyy-MM-dd').format(pickedDate);
                  dataAppointment['tanggalDimulai'] = formattedDate as String;
                  setState(
                    () {
                      dateinput.text = formattedDate
                          as String; //set output date to TextField value.
                    },
                  );
                } else {
                  print("Date is not selected");
                }
              },
            ),
            const SizedBox(height: 20),
            TextField(
              controller: timeinput,
              decoration: InputDecoration(
                prefixIcon: Icon(Icons.timer), //icon of text field
                labelText: "Watku Mulai", //label text of field
                // hintText:  setState(() =>),
                border: OutlineInputBorder(),
              ),
              readOnly: true,
              onTap: () async {
                TimeOfDay? pickedTime = await showTimePicker(
                  initialTime: TimeOfDay.now(),
                  context: context,
                );

                if (pickedTime != null) {
                  DateTime parsedTime = DateFormat.jm()
                      .parse(pickedTime.format(context).toString());
                  String formattedTime = DateFormat('HH:mm').format(parsedTime);
                  dataAppointment['waktuDimulai'] = formattedTime as String;
                  setState(
                    () {
                      timeinput.text = formattedTime as String;
                    },
                  );
                } else {
                  print("Time is not selected");
                }
              },
            ),
            const SizedBox(height: 20),
            SizedBox(
              height: 40,
              child: TextButton(
                  style: ButtonStyle(
                    foregroundColor: MaterialStateProperty.all(Colors.white),
                    backgroundColor: MaterialStateProperty.all(Colors.green),
                    shape: MaterialStateProperty.all(RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(10))),
                  ),
                  onPressed: () {
                    submitData(dataAppointment, timeinput.text, dateinput.text);
                  },
                  child: Text("Submit")),
            )
          ],
        ),
      ),
    );
  }
}
