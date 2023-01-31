package apap.tugaskelompok.rumahsehat.dto;

import java.util.List;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import lombok.Data;

@Data
public class PasienDto extends UserDto {
    private Integer saldo;
    private Integer umur;
    private List<AppointmentModel> listAppointment;

}
