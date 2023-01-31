package apap.tugaskelompok.rumahsehat.dto;

import java.time.LocalDate;
import java.util.List;

import apap.tugaskelompok.rumahsehat.model.*;
import lombok.Data;

@Data
public class ResepDto {
    private Long id;
    private boolean isDone;
    private LocalDate createdAt;
    private ApotekerModel apoteker;
    private AppointmentModel appointment;
    private List<JumlahResepObatModel> listJumlahResepObat;

}
