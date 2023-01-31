package apap.tugaskelompok.rumahsehat.dto;

import java.time.LocalDate;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import lombok.Data;

@Data
public class TagihanDto {
    private String kode;
    private LocalDate tanggalTerbuat;
    private LocalDate tanggalBayar;
    private boolean isPaid;
    private Integer jumlahTagihan;
    private AppointmentModel appointment;
}
