package apap.tugaskelompok.rumahsehat.service;
import apap.tugaskelompok.rumahsehat.model.ResepModel;
import java.util.List;


public interface ResepService {
    List<ResepModel> getListResep();
    void addResep(ResepModel resep);

    ResepModel getResepById(Long kode);


}
