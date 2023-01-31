package apap.tugaskelompok.rumahsehat.restcontroller;

import apap.tugaskelompok.rumahsehat.model.ObatModel;
import apap.tugaskelompok.rumahsehat.service.ObatRestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ObatRestController {
    private static Logger loggerLogger = LoggerFactory.getLogger(ObatRestController.class);
    @Autowired
    private ObatRestService obatRestService;

    @GetMapping(value = "/list-obat")
    public List<ObatModel> retrieveListObat(){
        loggerLogger.info("Successfully retrieved list Obat");
        return obatRestService.getListObat();
    }
}
