
import 'package:flutter/material.dart';
import 'package:rumahsehat_mobile/screens/home_screen.dart';
import 'package:rumahsehat_mobile/screens/membuat_appointment.dart';
import 'package:rumahsehat_mobile/screens/melihat_appointment.dart';
import 'package:rumahsehat_mobile/screens/melihat_tagihan.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'package:rumahsehat_mobile/screens/profile_page.dart';

import '../screens/login_screen.dart';

class MainDrawer extends StatefulWidget {
  static const routeName = '/home';

  MainDrawer();

  @override
  State<MainDrawer> createState() => MainDrawerState();
}

class MainDrawerState extends State<MainDrawer> {
  Widget buildListTile(String title, IconData icon, VoidCallback handler) {
    return ListTile(
      leading: Icon(
        icon,
        size: 26,
      ),
      title: Text(
        title,
        style: TextStyle(
          fontFamily: 'RobotoCondensed',
          fontSize: 24,
          fontWeight: FontWeight.bold,
        ),
      ),
      onTap: handler,
    );
  }

  void logoutUser() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    prefs.clear();
    Navigator.of(context).pushAndRemoveUntil(
        MaterialPageRoute(builder: (BuildContext context) => LoginScreen()),
        (Route route) => route == null);
  }

  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: Column(
        children: <Widget>[
          Container(
            height: 120,
            width: double.infinity,
            padding: const EdgeInsets.all(20),
            alignment: Alignment.centerLeft,
            color: Colors.green,
            child: const Text(
              'Rumah Sehat',
              style: TextStyle(
                  fontWeight: FontWeight.w900,
                  fontSize: 30,
                  color: Colors.white),
            ),
          ),
          const SizedBox(
            height: 10,
          ),
          const SizedBox(
            height: 20,
          ),
          buildListTile('Home', Icons.home, () {
            Navigator.of(context).pushReplacementNamed(MainScreen.routeName);
          }),
          buildListTile('View Profile', Icons.person, () {
            Navigator.of(context).pushReplacementNamed(UserProfile.routeName);
          }),
          buildListTile('Create Appointment', Icons.create, () {
            Navigator.of(context)
                .pushReplacementNamed(MembuatAppointment.routeName);
          }),
          buildListTile('View Appointment', Icons.view_agenda, () {
            Navigator.of(context)
                .pushReplacementNamed(MelihatAppointment.routeName);
          }),
          buildListTile('Tagihan', Icons.monetization_on, () {
            Navigator.of(context)
                .pushReplacementNamed(MelihatTagihan.routeName);
          }),
          const SizedBox(
            height: 100,
          ),
          buildListTile('Logout', Icons.logout, () {
            logoutUser();
          })
        ],
      ),
    );
  }
}
