import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:rumahsehat_mobile/models/pasien_model.dart';
import 'package:rumahsehat_mobile/screens/home_screen.dart';
import 'package:http/http.dart' as http;
import 'package:rumahsehat_mobile/screens/top-up-saldo.dart';

class UserProfile extends StatelessWidget {
  static const routeName = '/profile';

  const UserProfile({Key? key}) : super(key: key);

  Future<Pasien> fetchPasien() async {
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
      return Pasien.fromJson(body);
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
        title: Text("Profile Pasien"),
        backgroundColor: Colors.green,
      ),
      body: Center(
          child: Card(
        shadowColor: Colors.green,
        elevation: 8,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
        child: Container(
          padding: EdgeInsets.all(30),
          height: 300,
          width: 400,
          child: FutureBuilder<Pasien>(
            future: fetchPasien(),
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                return Column(
                  mainAxisAlignment: MainAxisAlignment.center,
                  children: [
                    Row(
                      children: [
                        Text("Nama pasien : ", style: TextStyle(fontSize: 20)),
                        Text(snapshot.data!.nama, style: TextStyle(fontSize: 20)),
                      ],
                    ),
                    Row(
                      children: [
                        Text("Email pasien : ", style: TextStyle(fontSize: 20)),
                        Text(snapshot.data!.email, style: TextStyle(fontSize: 20)),
                      ],
                    ),
                    Row(
                      children: [
                        Text("Username pasien : ", style: TextStyle(fontSize: 20)),
                        Text(snapshot.data!.username, style: TextStyle(fontSize: 20)),
                      ],
                    ),
                    Row(
                      children: [
                        Text("Saldo pasien : ", style: TextStyle(fontSize: 20)),
                        Text(snapshot.data!.saldo.toString(), style: TextStyle(fontSize: 20)),
                      ],
                    ),
                    Row(
                      children: [
                        Text("Umur pasien : ", style: TextStyle(fontSize: 20)),
                        Text(snapshot.data!.umur.toString(), style: TextStyle(fontSize: 20)),
                      ],
                    ),
                    Row(
                      mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        TextButton(
                          style: TextButton.styleFrom(
                            backgroundColor: Colors.green,
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(20),
                            ),
                          ),
                          onPressed: () {
                            // Navigator.of(context).pushReplacementNamed(TopUpSaldo.routeName);
                            Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (context) => TopUpSaldo()));
                          },
                          child: Text(
                            "Top Up Saldo",
                            style: TextStyle(fontSize: 20,
                              color: Color(0xffffffff),
                            ),
                          ),
                        ),
                      ],
                    )
                  ],
                );
              }
              return CircularProgressIndicator();
            },
          ),
        ),
      )),
    );
  }
}
