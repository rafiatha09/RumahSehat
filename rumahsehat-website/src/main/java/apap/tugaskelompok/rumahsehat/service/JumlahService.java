package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.JumlahResepObatModel;

import java.util.List;

public interface JumlahService {
    void addJumlah(JumlahResepObatModel jumlah);
    List<JumlahResepObatModel> allJumlahResepObat();
    JumlahResepObatModel getJumlahResepModel(long id);
    JumlahResepObatModel updateJumlah(long id, JumlahResepObatModel newJumlahResep);

}
