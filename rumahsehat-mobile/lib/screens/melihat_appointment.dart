import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter/material.dart';
import 'package:rumahsehat_mobile/models/appointment_model.dart';
import 'package:rumahsehat_mobile/models/pasien_model.dart';
import 'package:rumahsehat_mobile/screens/home_screen.dart';
import 'package:http/http.dart' as http;
import 'package:rumahsehat_mobile/screens/detail_appointment.dart';

class MelihatAppointment extends StatefulWidget {
  static const routeName = '/melihat-apointment';

  const MelihatAppointment({super.key});

  @override
  State<MelihatAppointment> createState() => _MelihatAppointmentState();
}

class _MelihatAppointmentState extends State<MelihatAppointment> {
  @override
  void initState() {
    super.initState();
  }

  Future<List<Appointment>> fetchListAppointment() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String idUser = prefs.get('id').toString();
    String _jwtToken = prefs.get("token").toString();
    final response = await http.get(
        Uri.parse(
            "https://apap-054.cs.ui.ac.id/api/v1/list-appointment/pasien/" +
                idUser),
        headers: <String, String>{
          'Content-Type': 'application/json;charset=UTF-8',
          'Authorization': 'Bearer $_jwtToken'
        });
    if (response.statusCode == 200) {
      final body = jsonDecode(response.body);
      return body.map<Appointment>(Appointment.fromJsonList).toList();
    } else {
      throw Exception('Failed to load Appointment');
    }
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
        title: Text("Melihat Appointment"),
        backgroundColor: Colors.green,
      ),
      body: Center(
        child: FutureBuilder<List<Appointment>>(
          future: fetchListAppointment(),
          builder: (context, snapshot) {
            if (snapshot.hasData) {
              return CustomeCard(
                listAppointment: snapshot.data!,
              );
            }
            return CircularProgressIndicator();
          },
        ),
        // child: CustomeCard(title: "title"),
      ),
    );
  }
}

class CustomeCard extends StatelessWidget {
  CustomeCard({super.key, required this.listAppointment});

  late List<Appointment> listAppointment;

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: listAppointment.length,
      itemBuilder: (context, index) {
        return Card(
          shadowColor: Colors.green,
          elevation: 8,
          shape:
              RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
          child: InkWell(
            onTap: () {
              Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => DetailAppointment(
                          listAppointment[index],
                          index,
                          TimeOfDay(
                                  hour: int.parse(listAppointment[index]
                                          .waktuDimulai
                                          .substring(0, 2)) +
                                      1,
                                  minute: int.parse(listAppointment[index]
                                      .waktuDimulai
                                      .substring(3, 5)))
                              .toString())));
            },
            child: Container(
              padding: EdgeInsets.all(25),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    listAppointment[index].kode,
                    style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
                  ),
                  const SizedBox(height: 2),
                  const Divider(
                    height: 5,
                    thickness: 1,
                    indent: 0,
                    endIndent: 0,
                    color: Colors.grey,
                  ),
                  const SizedBox(height: 2),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        "Pasien",
                        style: TextStyle(fontSize: 20),
                      ),
                      Text(
                        listAppointment[index].pasien.nama,
                        style: TextStyle(fontSize: 20),
                      ),
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        "Dokter",
                        style: TextStyle(fontSize: 20),
                      ),
                      Text(
                        listAppointment[index].dokter.nama,
                        style: TextStyle(fontSize: 20),
                      ),
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        "Tanggal Mulai",
                        style: TextStyle(fontSize: 20),
                      ),
                      Text(
                        listAppointment[index].tanggalDimulai,
                        style: TextStyle(fontSize: 20),
                      ),
                    ],
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: [
                      Text(
                        "Durasi",
                        style: TextStyle(fontSize: 20),
                      ),
                      Text(
                        listAppointment[index].waktuDimulai.substring(0, 5) +
                            "-" +
                            TimeOfDay(
                                    hour: int.parse(listAppointment[index]
                                            .waktuDimulai
                                            .substring(0, 2)) +
                                        1,
                                    minute: int.parse(listAppointment[index]
                                        .waktuDimulai
                                        .substring(3, 5)))
                                .toString()
                                .substring(10, 15),
                        style: TextStyle(fontSize: 20),
                      ),
                    ],
                  ),
                  const SizedBox(height: 2),
                  Text(
                    listAppointment[index].done ? "Done" : "Not Yet",
                    style: TextStyle(
                        color: listAppointment[index].done
                            ? Colors.green
                            : Colors.red,
                        fontSize: 14),
                  ),
                ],
              ),
            ),
          ),
        );
      },
    );
  }
}
