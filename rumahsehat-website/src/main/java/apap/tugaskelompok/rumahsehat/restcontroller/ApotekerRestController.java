package apap.tugaskelompok.rumahsehat.restcontroller;

import apap.tugaskelompok.rumahsehat.model.ApotekerModel;
import apap.tugaskelompok.rumahsehat.service.ApotekerRestService;

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
public class ApotekerRestController {
    private static Logger loggerLogger = LoggerFactory.getLogger(ApotekerRestController.class);
    @Autowired
    private ApotekerRestService apotekerRestService;

    @GetMapping(value = "/list-apoteker")
    public List<ApotekerModel> retrieveListApoteker() {
        loggerLogger.info("Successfully retrieved list Apoteker");
        return apotekerRestService.getListApoteker();
    }
}
