package apap.tugaskelompok.rumahsehat.security.jwtutils.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class JwtResponseModel {
    String username;
    String email;
    String token;
}