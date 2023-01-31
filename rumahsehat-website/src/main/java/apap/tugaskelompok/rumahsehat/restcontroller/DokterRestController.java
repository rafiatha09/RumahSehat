package apap.tugaskelompok.rumahsehat.restcontroller;

import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.service.DokterRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class DokterRestController {
    private static Logger loggerLogger = LoggerFactory.getLogger(DokterRestController.class);
    @Autowired
    private DokterRestService dokterService;


    // add new dokter
    @PostMapping(value = "/dokter/add")
    private DokterModel registerDokter(@Valid @RequestBody DokterModel dokterModel, BindingResult bindingResult){
        if(bindingResult.hasFieldErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed");
        }
        else{
            LOGGER.info("Successfully added a new Dokter");
            return dokterService.addDokter(dokterModel);
        }
    }

    // get all dokter
    @GetMapping(value = "/list-dokter")
    public List<DokterModel> retrieveListDokter() {
        loggerLogger.info("Successfully retrieved list Dokter");
        return dokterService.getListDokter();
    }
}
