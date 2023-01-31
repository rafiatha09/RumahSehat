package apap.tugaskelompok.rumahsehat.service;
import apap.tugaskelompok.rumahsehat.model.TagihanModel;
import apap.tugaskelompok.rumahsehat.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
@Transactional
public class TagihanRestServiceImpl implements TagihanRestService{
    @Autowired
    private TagihanDb tagihanDb;

    @Override
    public List<TagihanModel> getAllTagihan(){
        return tagihanDb.findAll();
    }

    @Override
    public TagihanModel getTagihanByCode(String kode){
        Optional<TagihanModel> tagihan = tagihanDb.findById(kode);
        if(tagihan.isPresent()){
            return tagihan.get();
        }else{
            throw new NoSuchElementException();
        }
    }

    @Override
    public TagihanModel updateTagihan(String kode, TagihanModel tagihanModel, int saldo, LocalDate tanggalBayar){
        TagihanModel tagihan = getTagihanByCode(kode);
        tagihan.getAppointment().getPasien().setSaldo(saldo);
        tagihan.setPaid(tagihanModel.isPaid());
        tagihan.setTanggalBayar(tagihanModel.getTanggalBayar());
        return tagihanDb.save(tagihan);
    }


}
