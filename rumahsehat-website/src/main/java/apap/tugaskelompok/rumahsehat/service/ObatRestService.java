package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.ObatModel;
import java.util.List;

public interface ObatRestService {
    void addObat(ObatModel obat);
    List<ObatModel> getListObat();
    ObatModel getObatById(String idObat);
    Boolean cekStok(ObatModel obatModel, int jumlahObat);
}
