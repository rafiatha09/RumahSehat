package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.repository.PasienDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PasienRestServiceImpl implements PasienRestService {
    @Autowired
    private PasienDb pasienDb;

    @Override
    public String encrypt(String password) {
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public PasienModel updatePasien(PasienModel pasienModel) {
        return pasienDb.save(pasienModel);
    }

    @Override
    public PasienModel createPasien(PasienModel pasienModel) {
        String pass = encrypt(pasienModel.getPassword());
        pasienModel.setPassword(pass);
        return pasienDb.save(pasienModel);
    }

    @Override
    public List<PasienModel> getAllPasien() {
        return pasienDb.findAll();
    }

    @Override
    public PasienModel getPasienById(String pasienId) {
        List<PasienModel> pasienlist = getAllPasien();
        PasienModel pasienModel = null;
        for (PasienModel pasien : pasienlist) {
            if (pasien.getUuid().equals(pasienId)) {
                pasienModel = pasien;
                break;
            }
        }
        if (pasienModel != null) {
            return pasienModel;
        } else
            return null;
    }

    @Override
    public PasienModel topUpSaldo(String uuidPasien, Integer saldo) {
        PasienModel updatePasien = getPasienById(uuidPasien);
        updatePasien.setSaldo(updatePasien.getSaldo() + saldo);
        return pasienDb.save(updatePasien);
    }

    @Override
    public PasienModel bayarTagihan(String uuidPasien, PasienModel pasienModel) {
        PasienModel updatePasien = getPasienById(uuidPasien);
        updatePasien.setSaldo(pasienModel.getSaldo());
        return pasienDb.save(updatePasien);
    }
}
