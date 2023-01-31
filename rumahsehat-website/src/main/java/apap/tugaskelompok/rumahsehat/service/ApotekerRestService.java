package apap.tugaskelompok.rumahsehat.service;

import apap.tugaskelompok.rumahsehat.model.ApotekerModel;
import java.util.List;

public interface ApotekerRestService {
    void addApoteker(ApotekerModel obat);
    List<ApotekerModel> getListApoteker();
    ApotekerModel getApotekerById(String idApoteker);
    String encrypt(String password);
    void updateApoterker(ApotekerModel apotekerModel);
}
