import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import '../widgets/main_drawer.dart';

class MainScreen extends StatefulWidget {
  static const routeName = '/home';

  MainScreen();

  @override
  State<MainScreen> createState() => MainScreenState();
}

class MainScreenState extends State<MainScreen> {
  String? user = '';

  @override
  void initState() {
    super.initState();
    _loadUser();
  }

  void _loadUser() async {
    final prefs = await SharedPreferences.getInstance();
    setState(() {
      user = prefs.getString('username');
    });
  }

  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Rumah Sehat'),
          backgroundColor: Colors.green,
        ),
        drawer: MainDrawer(),
        body: SingleChildScrollView(
            child: SingleChildScrollView(
                child: Padding(
          padding: EdgeInsets.all(40),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget>[
              Text(
                'Hi, $user!',
                style: TextStyle(
                    fontSize: 30,
                    fontWeight: FontWeight.bold,
                    fontFamily: "Montserrat-Regular",
                    color: Color(0xFF111111)),
              ),
              Text(
                "Selamat Datang di Rumah Sehat",
                style: TextStyle(
                    fontSize: 30,
                    fontWeight: FontWeight.bold,
                    fontFamily: "Montserrat-Regular",
                    color: Color(0xFF111111)),
              ),
              SizedBox(
                height: 32,
              ),
              SizedBox(
                height: 30,
              )
            ],
          ),
        ))));
  }
}
