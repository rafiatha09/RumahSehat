package apap.tugaskelompok.rumahsehat.restcontroller;

import apap.tugaskelompok.rumahsehat.dto.TagihanDto;
import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.model.TagihanModel;
import apap.tugaskelompok.rumahsehat.service.PasienRestService;
import apap.tugaskelompok.rumahsehat.service.TagihanRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class TagihanRestController {

    private static Logger loggerLogger = LoggerFactory.getLogger(TagihanRestController.class);
    @Autowired
    TagihanRestService tagihanRestService;

    @Autowired
    private PasienRestService pasienRestService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/list-tagihan")
    public List<TagihanModel> retrieveListTagihan() {
        return tagihanRestService.getAllTagihan();
    }

    @PutMapping(value = "/tagihan/update")
    public TagihanModel updateTagihan(@RequestBody TagihanDto tagihanDto) {
        var tagihanModel = modelMapper.map(tagihanDto, TagihanModel.class);

        int saldo = tagihanModel.getAppointment().getPasien().getSaldo();
        final var message = "Successfully updated Tagihan " + tagihanModel.getKode();
        loggerLogger.info(message);

        loggerLogger.info("Status tagihan has set to paid");
        try {
            return tagihanRestService.updateTagihan(tagihanModel.getKode(), tagihanModel, saldo,
                    tagihanModel.getTanggalBayar());
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Tagihan degan " + tagihanModel.getKode() + " not found");

        }
    }

    @GetMapping(value = "/list-tagihan/pasien/{kode}")
    public List<TagihanModel> retrieveTagihanFromPasienId(@PathVariable("kode") String kode) {
        var pasienModel = pasienRestService.getPasienById(kode);
        if (pasienModel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tidak ada pasien dengan kode " + kode);
        }
        List<AppointmentModel> appointmentModelList = pasienModel.getListAppointment();

        List<TagihanModel> tagihanModelsList = new ArrayList<>();

        for (AppointmentModel appointmentModel : appointmentModelList) {
            if (appointmentModel.getTagihan() != null) {
                tagihanModelsList.add(appointmentModel.getTagihan());
            }

        }
        loggerLogger.info("Successfully retrieved Tagihan data");
        return tagihanModelsList;
    }

}
