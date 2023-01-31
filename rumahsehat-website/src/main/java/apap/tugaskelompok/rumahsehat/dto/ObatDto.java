package apap.tugaskelompok.rumahsehat.dto;

import java.util.List;

import apap.tugaskelompok.rumahsehat.model.JumlahResepObatModel;
import lombok.Data;

@Data
public class ObatDto {
    private String id;
    private String nama_obat;
    private Integer stok;
    private Integer harga;
    private List<JumlahResepObatModel> listJumlahResepObat;
}
