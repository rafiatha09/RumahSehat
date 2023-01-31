package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.model.TagihanModel;
import apap.tugaskelompok.rumahsehat.repository.TagihanDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TagihanServiceImpl implements TagihanService {
    @Autowired
    private TagihanDb tagihanDb;

    @Override
    public TagihanModel createTagihan(AppointmentModel appointment, int hargaObat) {
        var tagihanModel = new TagihanModel();
        var idTagihan = String.valueOf((getAllTagihan().size() + 1));
        tagihanModel.setKode("BILL-" + idTagihan);
        tagihanModel.setPaid(false);
        tagihanModel.setTanggalBayar(null);
        tagihanModel.setAppointment(appointment);
        if (appointment.getResep() == null) {
            tagihanModel.setTanggalTerbuat(appointment.getTanggalDimulai());
            tagihanModel.setJumlahTagihan(appointment.getDokter().getTarif());
        } else {
            tagihanModel.setTanggalTerbuat(LocalDate.now());
            tagihanModel.setJumlahTagihan(appointment.getDokter().getTarif() + Integer.valueOf(hargaObat));

        }
        return tagihanDb.save(tagihanModel);
    }

    @Override
    public List<TagihanModel> getAllTagihan() {
        return tagihanDb.findAll();
    }

    @Override
    public TagihanModel getTagihan(String kode) {
        Optional<TagihanModel> value = tagihanDb.findById(kode);
        if (value.isPresent()) {
            return value.get();
        } else
            return null;
    }

    @Override
    public TagihanModel updateTagihan(String kode, TagihanModel tagihanModel) {
        var tagihanModelLama = getTagihan(kode);
        tagihanModelLama.setPaid(tagihanModel.isPaid());
        tagihanModelLama.setTanggalTerbuat(tagihanModel.getTanggalTerbuat());
        tagihanModelLama.setTanggalBayar(tagihanModel.getTanggalBayar());
        tagihanModelLama.setJumlahTagihan(tagihanModelLama.getJumlahTagihan());
        return tagihanDb.save(tagihanModelLama);
    }

}
