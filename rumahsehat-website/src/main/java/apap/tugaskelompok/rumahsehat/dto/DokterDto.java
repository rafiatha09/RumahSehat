package apap.tugaskelompok.rumahsehat.dto;

import java.util.List;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import lombok.Data;

@Data
public class DokterDto extends UserDto {
    private Integer tarif;
    private List<AppointmentModel> listAppointment;
}
