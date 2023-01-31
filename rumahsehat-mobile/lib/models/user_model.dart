class User {
  String jwtToken;
  String id;
  String nama;
  String username;
  String role;
  String email;

  User({
    required this.jwtToken,
    this.id = "",
    this.nama = "",
    this.username = "",
    this.role = "",
    this.email = "",
  });

  User.fromJson(Map<String, dynamic> json)
      : jwtToken = json['jwtToken'],
        id = json['id'],
        nama = json['nama'],
        username = json['username'],
        role = json['role'],
        email = json['email']
        ;

  Map<String, dynamic> toJson() => {
        'jwtToken': jwtToken,
        'id': id,
        'nama': nama,
        'username': username,
        'role': role,
        'email': email,
      };
}