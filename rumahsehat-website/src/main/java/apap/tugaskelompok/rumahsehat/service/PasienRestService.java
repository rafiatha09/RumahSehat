package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.PasienModel;

import java.util.List;

public interface PasienRestService {

    PasienModel createPasien(PasienModel pasienModel);
    List<PasienModel> getAllPasien();
    PasienModel getPasienById(String pasienModel);
    String encrypt(String password);
    PasienModel topUpSaldo(String uuidPasien, Integer saldo);
    PasienModel updatePasien(PasienModel pasienModel);

    PasienModel bayarTagihan(String uuidPasien, PasienModel pasienModel);
}
