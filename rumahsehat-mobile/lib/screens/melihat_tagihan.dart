import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:rumahsehat_mobile/models/tagihan_model.dart';
import 'package:rumahsehat_mobile/screens/home_screen.dart';
import 'package:http/http.dart' as http;
import 'package:rumahsehat_mobile/screens/detail_tagihan.dart';
import 'package:shared_preferences/shared_preferences.dart';

class MelihatTagihan extends StatefulWidget {
  static const routeName = '/melihat-tagihan';

  const MelihatTagihan({super.key});

  @override
  State<MelihatTagihan> createState() => _MelihatTagihanState();
}

class _MelihatTagihanState extends State<MelihatTagihan> {
  @override
  void initState() {
    super.initState();
  }

  Future<List<Tagihan>> fetchListTagihan() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String idUser = prefs.get('id').toString();
    String _jwtToken = prefs.get("token").toString();
    final response = await http.get(
        Uri.parse("https://apap-054.cs.ui.ac.id/api/v1/list-tagihan/pasien/" +
            idUser),
        headers: <String, String>{
          'Content-Type': 'application/json;charset=UTF-8',
          'Authorization': 'Bearer $_jwtToken'
        });
    if (response.statusCode == 200) {
      final body = jsonDecode(response.body);
      return body.map<Tagihan>(Tagihan.fromJsonList).toList();
    } else {
      throw Exception('Failed to load Appointment');
    }
  }

  Future<Tagihan> updateStatusTagihan() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String _jwtToken = prefs.get("token").toString();
    final response = await http.put(
        Uri.parse("https://apap-054.cs.ui.ac.id/api/v1/list-tagihan"),
        headers: <String, String>{
          'Content-Type': 'application/json;charset=UTF-8',
          'Authorization': 'Bearer $_jwtToken'
        });
    if (response.statusCode == 200) {
      final body = jsonDecode(response.body);
      return body.map<Tagihan>(Tagihan.fromJsonList).toList();
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
        title: Text("Melihat Tagihan"),
        backgroundColor: Colors.green,
      ),
      body: Center(
        child: FutureBuilder<List<Tagihan>>(
          future: fetchListTagihan(),
          builder: (context, snapshot) {
            if (snapshot.hasData) {
              return CustomeCard(
                listTagihan: snapshot.data!,
              );
            }
            return CircularProgressIndicator();
          },
        ),
      ),
    );
  }
}

class CustomeCard extends StatelessWidget {
  CustomeCard({super.key, required this.listTagihan});

  late List<Tagihan> listTagihan;

  @override
  Widget build(BuildContext context) {
    return ListView.builder(
      itemCount: listTagihan.length,
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
                      builder: (context) => DetailTagihan(
                            listTagihan[index],
                            index,
                          )));
            },
            child: Container(
              padding: EdgeInsets.all(25),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text(
                    listTagihan[index].kode,
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
                        "Tanggal Terbuat",
                        style: TextStyle(fontSize: 20),
                      ),
                      Text(
                        listTagihan[index].tanggalTerbuat,
                        style: TextStyle(fontSize: 20),
                      ),
                    ],
                  ),
                  const SizedBox(height: 2),
                  Text(
                    listTagihan[index].paid ? "Terbayar" : "Belum Terbayar",
                    style: TextStyle(
                        color:
                            listTagihan[index].paid ? Colors.green : Colors.red,
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
