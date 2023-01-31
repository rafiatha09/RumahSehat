import 'dart:async';
import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:rumahsehat_mobile/models/pasien_model.dart';
import 'package:http/http.dart' as http;
import 'package:fluttertoast/fluttertoast.dart';
import 'package:rumahsehat_mobile/screens/login_screen.dart';
import 'package:shared_preferences/shared_preferences.dart';

class RegisterScreen extends StatefulWidget {
  static const routeName = '/register-pasien';

  const RegisterScreen({super.key});

  @override
  State<RegisterScreen> createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  bool isPasswrodVisibile = false;
  bool isPasswordvis = false;
  late Pasien pasienModel;
  late String confirmPassword;

  var dataPasien = {
    "nama": "",
    "username": "",
    "role": "Pasien",
    "password": "",
    "email": "",
    "saldo": 0,
    "umur": 0
  };


  // Khsusus password form yang inputannya String
  Widget passwordForm(String hint, String label, String data) => TextField(
        onChanged: (value) => setState(() => (data == "password"
            ? dataPasien["password"] = value
            : this.confirmPassword = value)),
        decoration: InputDecoration(
          // errorText: (isSubmit? (validatePassword(data) == "" ? null: validatePassword(data)): null),
          hintText: hint,
          labelText: label,
          border: OutlineInputBorder(),
          suffixIcon: IconButton(
            icon: (data == "password"
                ? (isPasswrodVisibile
                    ? Icon(Icons.visibility_off)
                    : Icon(Icons.visibility))
                : (isPasswordvis
                    ? Icon(Icons.visibility_off)
                    : Icon(Icons.visibility))),
            onPressed: () => setState(() => (data == "password"
                ? isPasswrodVisibile = !isPasswrodVisibile
                : isPasswordvis = !isPasswordvis)),
          ),
        ),
        obscureText: (data == "password" ? isPasswrodVisibile : isPasswordvis),
      );

  bool validateConfirm(String password, String cpassword) {
    if (password == cpassword) {
      return true;
    }
    return false;
  }

  Future<Pasien> submitData(Map<String, dynamic> object) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    final response = await http.post(
      Uri.parse("https://apap-054.cs.ui.ac.id/api/v1/pasien/add"),
      headers: <String, String>{
        'Content-Type': 'application/json;charset=UTF-8',
      },
      body: jsonEncode(<String, String>{
        "nama": object['nama'],
        "username": object['username'],
        "password": object['password'],
        "email": object['email'],
        "role": object['role'],
        "saldo": object['saldo'],
        "umur": object['umur']
      }),
    );

    if (response.statusCode == 200) {
      toast('Successfully added new pasien');
      return Pasien.fromJson(jsonDecode(response.body));
    } else {
      toast('Failed to create Pasien');
      throw Exception("Failed to create Pasien");
    }
  }

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
            MaterialPageRoute(builder: (context) => LoginScreen()),
          ),
        ),
        title: Text("Register Pasien"),
        backgroundColor: Colors.green,
      ),
      body: Center(
        child: ListView(
          padding: EdgeInsets.all(32),
          children: [
            TextField(
              onChanged: (value) => setState(() => dataPasien["nama"] = value),
              decoration: InputDecoration(
                hintText: "Nama Lengkap",
                labelText: "Nama",
                prefixIcon: Icon(IconData(0xe042, fontFamily: 'MaterialIcons')),
                border: OutlineInputBorder(),
              ),
              keyboardType: TextInputType.text,
              textInputAction: TextInputAction.done,
            ),
            const SizedBox(height: 20),
            TextField(
              onChanged: (value) =>
                  setState(() => dataPasien["username"] = value),
              decoration: InputDecoration(
                hintText: "Username",
                labelText: "Username",
                prefixIcon: Icon(IconData(0xe69a, fontFamily: 'MaterialIcons')),
                border: OutlineInputBorder(),
              ),
              keyboardType: TextInputType.text,
              textInputAction: TextInputAction.done,
            ),
            const SizedBox(height: 20),
            TextField(
              onChanged: (value) => setState(() => dataPasien["saldo"] = value),
              decoration: InputDecoration(
                hintText: "xxRp",
                labelText: "Saldo",
                prefixIcon: Icon(IconData(0xe041, fontFamily: 'MaterialIcons')),
                border: OutlineInputBorder(),
              ),
              keyboardType: TextInputType.number,
            ),
            const SizedBox(height: 20),
            TextField(
              onChanged: (value) => setState(() => dataPasien["umur"] = value),
              decoration: InputDecoration(
                hintText: "xxTahun",
                labelText: "Umur",
                prefixIcon: Icon(IconData(0xe403, fontFamily: 'MaterialIcons')),
                border: OutlineInputBorder(),
              ),
              keyboardType: TextInputType.number,
            ),
            const SizedBox(height: 20),
            TextField(
              onChanged: (value) => setState(() => dataPasien["email"] = value),
              decoration: InputDecoration(
                hintText: "username@example.com",
                labelText: "Email",
                prefixIcon: Icon(Icons.mail),
                border: OutlineInputBorder(),
              ),
              keyboardType: TextInputType.emailAddress,
              textInputAction: TextInputAction.done,
            ),
            const SizedBox(height: 20),
            passwordForm("Password", "Password", "password"),
            const SizedBox(height: 20),
            passwordForm(
                "Confirmation Password", "Confirmation Password", "confirm"),
            const SizedBox(height: 25),
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
                    if (validateConfirm(
                        dataPasien["password"].toString(), confirmPassword)) {
                      submitData(dataPasien);

                    } else {
                      final snackBar = SnackBar(
                        content: Text(
                          'Password and confirmation password are not match',
                          style: TextStyle(color: Colors.red),
                        ),
                      );
                      ScaffoldMessenger.of(context).showSnackBar(snackBar);
                    }
                  },
                  child: Text("Submit")),
            )
          ],
        ),
      ),
    );
  }
}
