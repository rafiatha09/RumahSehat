package apap.tugaskelompok.rumahsehat.restcontroller;


import apap.tugaskelompok.rumahsehat.model.JumlahResepObatModel;
import apap.tugaskelompok.rumahsehat.service.JumlahService;
import apap.tugaskelompok.rumahsehat.service.ResepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class JumlahResepRestController {
    private static Logger loggerLogger = LoggerFactory.getLogger(JumlahResepRestController.class);

    @Autowired
    JumlahService jumlahService;

    @Autowired
    ResepService resepService;

    @GetMapping(value = "/list-jumlah")
    public List<JumlahResepObatModel> getJumlahResepById() {
        loggerLogger.info("Successfully retrieved list Jumlah Resep");
        return jumlahService.allJumlahResepObat();
    }

    @GetMapping(value = "/list-jumlah/resep/{id}")
    public List<JumlahResepObatModel> getJumlahResepByObatId(@PathVariable("id") String id) {
        var resepModel =  resepService.getResepById(Long.parseLong(id));
        if(resepModel == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tidak ada resep dengan id " + id);
        }
        List<JumlahResepObatModel> jumlahResepObatModelList = resepModel.getListJumlahResepObat();
        loggerLogger.info("Successfully retrieved list Jumlah Resep Obat");
        return  jumlahResepObatModelList;
    }
}

