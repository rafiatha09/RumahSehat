package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.ApotekerModel;
import apap.tugaskelompok.rumahsehat.repository.ApotekerDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class ApotekerRestServiceImpl implements ApotekerRestService {
    @Autowired
    ApotekerDb apotekerDb;

    @Override
    public String encrypt(String password) {
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
    }

    @Override
    public void addApoteker(ApotekerModel apoteker) {
        String pass = encrypt(apoteker.getPassword());
        apoteker.setPassword(pass);
        apotekerDb.save(apoteker);
    }

    @Override
    public void updateApoterker(ApotekerModel apotekerModel) {
        apotekerDb.save(apotekerModel);
    }

    @Override
    public ApotekerModel getApotekerById(String idApoteker) {
        Optional<ApotekerModel> apoteker = apotekerDb.findById(idApoteker);
        if (apoteker.isPresent()) {
            return apoteker.get();
        } else
            return null;
    }

    @Override
    public List<ApotekerModel> getListApoteker() {
        return apotekerDb.findAll();
    }
}
