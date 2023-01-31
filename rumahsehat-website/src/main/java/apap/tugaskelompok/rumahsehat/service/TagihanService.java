package apap.tugaskelompok.rumahsehat.service;
import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.model.TagihanModel;

import java.util.List;


public interface TagihanService {
    TagihanModel createTagihan(AppointmentModel appointment, int hargaObat);
    List<TagihanModel> getAllTagihan();

    TagihanModel getTagihan(String kode);
    TagihanModel updateTagihan(String kode, TagihanModel tagihanModel);

}
