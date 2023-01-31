package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.TagihanModel;

import java.time.LocalDate;
import java.util.List;

public interface TagihanRestService {
    List<TagihanModel> getAllTagihan();
    TagihanModel getTagihanByCode(String kode);
    TagihanModel updateTagihan(String kode, TagihanModel tagihanModel, int saldo, LocalDate tanggalBayar);
}
