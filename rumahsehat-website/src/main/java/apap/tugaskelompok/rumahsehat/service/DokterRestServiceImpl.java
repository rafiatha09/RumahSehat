package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.repository.DokterDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class DokterRestServiceImpl implements DokterRestService {
    @Autowired
    DokterDb dokterDb;
    
    @Override
    public String encrypt(String password) {
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public DokterModel updateDokter(DokterModel dokter){
        return dokterDb.save(dokter);
    }

    @Override
    public DokterModel addDokter(DokterModel dokter){
        String pass = encrypt(dokter.getPassword());
        dokter.setPassword(pass);
        return dokterDb.save(dokter);
    }

    @Override
    public DokterModel getDokterById(String idDokter){
        Optional<DokterModel> dokterModel = dokterDb.findById(idDokter);
        if(dokterModel.isPresent()){
            return dokterModel.get();
        } else return null;
    }

    @Override
    public List<DokterModel> getListDokter(){
        return dokterDb.findAll();
    }
}

