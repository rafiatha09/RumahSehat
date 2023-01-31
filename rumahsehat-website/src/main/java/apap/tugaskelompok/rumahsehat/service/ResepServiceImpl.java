package apap.tugaskelompok.rumahsehat.service;


import apap.tugaskelompok.rumahsehat.model.ResepModel;
import apap.tugaskelompok.rumahsehat.repository.ResepDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class ResepServiceImpl implements ResepService {
    @Autowired
    ResepDb resepDb;
    @Override
    public List<ResepModel> getListResep() {
        return resepDb.findAll();
    }
    @Override
    public void addResep(ResepModel resep) {
        resepDb.save(resep);
    }


    @Override
    public ResepModel getResepById(Long kode){
        Optional<ResepModel> resepModel = resepDb.findById(kode);
        if(resepModel.isPresent()){
            return resepModel.get();
        }
        return null;

    }
}
