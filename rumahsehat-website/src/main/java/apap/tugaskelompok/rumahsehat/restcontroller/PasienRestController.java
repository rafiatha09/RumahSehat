package apap.tugaskelompok.rumahsehat.restcontroller;

import apap.tugaskelompok.rumahsehat.dto.PasienDto;
import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.service.PasienRestService;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class PasienRestController {
    private static Logger loggerLogger = LoggerFactory.getLogger(PasienRestController.class);

    @Autowired
    private PasienRestService pasienRestService;

    @Autowired
    private ModelMapper modelMapper;

    String notFoundStr = " not found";

    @PostMapping(value = "/pasien/add")
    public PasienModel registerPasien(@Valid @RequestBody PasienDto pasienDto, BindingResult bindingResult) {
        var pasienModel = modelMapper.map(pasienDto, PasienModel.class);

        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed");
        } else {
            loggerLogger.info("Successfully created Pasien");
            return pasienRestService.createPasien(pasienModel);
        }
    }

    // get all pasien
    @GetMapping(value = "/list-pasien")
    public List<PasienModel> retrieveListPasien() {
        loggerLogger.info("Successfully retrieved list Pasien");
        return pasienRestService.getAllPasien();
    }

    @GetMapping(value = "/pasien/{kode}")
    public PasienModel getPasien(@PathVariable("kode") String kode) {
        try {
            final var message = String.format("Successfully retrieved Pasien by id %s", kode);
            loggerLogger.info(message);

            return pasienRestService.getPasienById(kode);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pasien " + kode + notFoundStr);
        }
    }

    @PutMapping(value = "/topup-saldo/{uuidPasien}")
    public PasienModel topUpSaldo(@PathVariable("uuidPasien") String uuidPasien,
            @RequestBody Map<String, String> saldo) {
        try {
            var newSaldo = Integer.parseInt(saldo.get("saldo"));
            loggerLogger.info("Successfully topping up saldo Pasien");
            pasienRestService.topUpSaldo(uuidPasien, newSaldo);
            return pasienRestService.getPasienById(uuidPasien);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "UUID Pasien  " + uuidPasien + notFoundStr);
        }
    }

    @PutMapping(value = "/bayar-tagihan")
    public PasienModel bayarTagihan(@RequestBody PasienDto pasienDto) {
        var pasienModel = modelMapper.map(pasienDto, PasienModel.class);

        try {
            loggerLogger.info("Successfully pay Tagihan");
            return pasienRestService.bayarTagihan(pasienModel.getUuid(), pasienModel);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "UUID Pasien  " + pasienModel.getUuid() + notFoundStr);
        }
    }
}
