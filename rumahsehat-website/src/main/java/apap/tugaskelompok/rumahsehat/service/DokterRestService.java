package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.DokterModel;

import java.util.List;

public interface DokterRestService {
    String encrypt(String password);
    DokterModel addDokter(DokterModel dokterModel);
    DokterModel getDokterById(String idDokter);
    List<DokterModel> getListDokter();
    DokterModel updateDokter(DokterModel dokter);
}

