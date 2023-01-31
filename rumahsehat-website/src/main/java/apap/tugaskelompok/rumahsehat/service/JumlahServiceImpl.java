package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.JumlahResepObatModel;
import apap.tugaskelompok.rumahsehat.repository.JumlahResepObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JumlahServiceImpl implements JumlahService {
    @Autowired
    JumlahResepObatDb jumlahResepObatDb;

    @Override
    public JumlahResepObatModel getJumlahResepModel(long id) {
        Optional<JumlahResepObatModel> value = jumlahResepObatDb.findById(id);
        if (value.isPresent()) {
            return value.get();
        } else
            return null;
    }

    @Override
    public void addJumlah(JumlahResepObatModel jumlah) {
        jumlahResepObatDb.save(jumlah);
    }

    @Override
    public List<JumlahResepObatModel> allJumlahResepObat() {
        return jumlahResepObatDb.findAll();
    }

    @Override
    public JumlahResepObatModel updateJumlah(long id, JumlahResepObatModel newJumlahResep) {
        var jumlahResepObatModel = getJumlahResepModel(id);
        jumlahResepObatModel.setResep(newJumlahResep.getResep());
        jumlahResepObatModel.setObat(newJumlahResep.getObat());
        jumlahResepObatModel.setKuantitas(newJumlahResep.getKuantitas());
        return jumlahResepObatDb.save(jumlahResepObatModel);
    }
}
