import 'dart:async';
import 'dart:convert';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:flutter/material.dart';
import 'package:rumahsehat_mobile/screens/home_screen.dart';
import 'package:http/http.dart' as http;
import 'package:fluttertoast/fluttertoast.dart';

class TopUpSaldo extends StatelessWidget {
  TopUpSaldo();

  late bool triger = false;

  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  Future<bool?> toast(String message) {
    return Fluttertoast.showToast(
        msg: message,
        toastLength: Toast.LENGTH_LONG,
        gravity: ToastGravity.BOTTOM,
        timeInSecForIosWeb: 4,
        backgroundColor: Colors.redAccent,
        textColor: Colors.white,
        fontSize: 15.0);
  }

  Future<http.Response> postData(String nominalSaldo) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    String idUser = prefs.get('id').toString();
    String _jwtToken = prefs.get("token").toString();
    var response = await http.put(
        Uri.parse('https://apap-054.cs.ui.ac.id/api/v1/topup-saldo/' + idUser),
        headers: <String, String>{
          'Content-Type': 'application/json',
          'Authorization': 'Bearer $_jwtToken'
        },
        body: jsonEncode(<String, String>{"saldo": nominalSaldo}));
    if (response.statusCode == 200) {
      toast("Successfully top up");
      triger = true;
    } else {
      toast("gagal top up");
    }
    return response;
  }

  @override
  Widget build(BuildContext context) {
    var nominal = "0";
    return Scaffold(
        appBar: AppBar(
          title: Text("Top Up Saldo"),
          backgroundColor: Colors.green,
        ),
        body: Form(
          key: _formKey,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              TextFormField(
                decoration: const InputDecoration(
                  hintText: 'Jumlah saldo',
                ),
                validator: (String? value) {
                  if (value == null || value.isEmpty) {
                    return 'Mohon masukkan jumlah saldo top up yang anda inginkan';
                  }
                  nominal = value;
                  return null;
                },
              ),
              Padding(
                padding: const EdgeInsets.symmetric(vertical: 16.0),
                child: ElevatedButton(
                  onPressed: () {
                    // Validate will return true if the form is valid, or false if
                    // the form is invalid.
                    if (_formKey.currentState!.validate()) {
                      // Process data.
                      postData(nominal);
                      Timer(Duration(seconds: 2), () {
                        if (triger) {
                          print("Yeah, this line is printed after 2 seconds");
                          Navigator.push(
                              context,
                              MaterialPageRoute(
                                  builder: (context) => MainScreen()));
                        }
                      });
                      // }
                      // Navigator.pop(context);
                    }
                  },
                  child: const Text('TOP UP'),
                ),
              ),
            ],
          ),
        ));
  }
}
