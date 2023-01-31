package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.ObatModel;
import apap.tugaskelompok.rumahsehat.repository.ObatDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.Optional;
import java.util.List;

@Service
@Transactional
public class ObatRestServiceImpl implements ObatRestService {
    @Autowired
    ObatDb obatDb;
    
    @Override
    public void addObat(ObatModel obat){
        obatDb.save(obat);
    }

    @Override
    public ObatModel getObatById(String idObat){
        Optional<ObatModel> obat = obatDb.findById(idObat);
        if(obat.isPresent()){
            return obat.get();
        } else return null;
    }

    @Override
    public List<ObatModel> getListObat(){
        return obatDb.findAll();
    }

    @Override
    public Boolean cekStok(ObatModel obatModel, int jumlahObat) {
        if (obatModel==null) {
            return false;
        }
        int stokObatGudang = obatModel.getStok();
        if (stokObatGudang>=jumlahObat) {
            return true;
        }
        return false;
    }
}
