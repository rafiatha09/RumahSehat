package apap.tugaskelompok.rumahsehat.security.jwtutils.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequestModel {
    String username;
    String password;
}