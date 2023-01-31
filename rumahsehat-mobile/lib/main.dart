import 'package:flutter/material.dart';

import 'package:rumahsehat_mobile/screens/melihat_tagihan.dart';
import 'package:rumahsehat_mobile/screens/home_screen.dart';
import 'package:rumahsehat_mobile/screens/login_screen.dart';
import 'package:rumahsehat_mobile/screens/register_pasien.dart';
import 'package:rumahsehat_mobile/screens/melihat_appointment.dart';
import 'package:rumahsehat_mobile/screens/membuat_appointment.dart';
import 'package:rumahsehat_mobile/screens/profile_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Rumah Sehat',
      theme: ThemeData(
        primarySwatch: Colors.green,
      ),
      initialRoute: '/',
      routes: {
        '/': (ctx) => const LoginScreen(),
        MainScreen.routeName: (context) => MainScreen(),
        RegisterScreen.routeName: (context) => const RegisterScreen(),
        MembuatAppointment.routeName: (context) => const MembuatAppointment(),
        MelihatAppointment.routeName: (context) => const MelihatAppointment(),
        MelihatTagihan.routeName: (context) => const MelihatTagihan(),
        UserProfile.routeName: (context) => const UserProfile(),
      },
      onUnknownRoute: (settings) {
        return MaterialPageRoute(
          builder: (ctx) => const LoginScreen(),
        );
      },
    );
  }
}
