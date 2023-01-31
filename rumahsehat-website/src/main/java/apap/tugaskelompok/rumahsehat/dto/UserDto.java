package apap.tugaskelompok.rumahsehat.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserDto implements Serializable {
    private String uuid;
    private String nama;
    private String role;
    private String username;
    private String password;
    private String email;
}