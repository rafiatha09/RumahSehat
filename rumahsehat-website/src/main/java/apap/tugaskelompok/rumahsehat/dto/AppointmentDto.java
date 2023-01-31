package apap.tugaskelompok.rumahsehat.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import apap.tugaskelompok.rumahsehat.model.*;
import lombok.Data;

@Data
public class AppointmentDto {
    private String kode;
    private LocalDate tanggalDimulai;
    private LocalTime waktuDimulai;
    private boolean isDone;
    private DokterModel dokter;
    private PasienModel pasien;
    private TagihanModel tagihan;
    private ResepModel resep;
}
