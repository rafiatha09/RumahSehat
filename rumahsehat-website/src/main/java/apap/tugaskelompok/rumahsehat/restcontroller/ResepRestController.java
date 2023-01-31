package apap.tugaskelompok.rumahsehat.restcontroller;

import apap.tugaskelompok.rumahsehat.model.ResepModel;
import apap.tugaskelompok.rumahsehat.service.AppointmentService;
import apap.tugaskelompok.rumahsehat.service.ResepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class ResepRestController {
    private static Logger loggerLogger = LoggerFactory.getLogger(ResepRestController.class);
    @Autowired
    ResepService resepService;

    @Autowired
    AppointmentService appointmentService;

    @GetMapping(value = "/list-resep")
    public List<ResepModel> getAllResep() {

        loggerLogger.info("Successfully retrieved list Resep");
        return resepService.getListResep();
    }

    @GetMapping(value = "/resep/appointment/{kode}")
    public ResepModel getResepByAppointment(@PathVariable("kode") String kode) {
        final var message = String.format("Successfully retrieved resep by Appointment with code %s", kode);
        loggerLogger.info(message);

        var appointmentModel = appointmentService.getAppointment(kode);
        if (appointmentModel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tidak ada appointment dengan kode " + kode);
        }
        return appointmentModel.getResep();
    }

    @GetMapping(value = "/resep/{kode}")
    public ResepModel getResepById(@PathVariable("kode") String kode) {
        final var message = String.format("Successfully retrieved Resep by code %s", kode);
        loggerLogger.info(message);

        var resepModel = resepService.getResepById(Long.parseLong(kode));
        if (resepModel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tidak ada resep dengan kode " + kode);
        }
        return resepModel;
    }
}
