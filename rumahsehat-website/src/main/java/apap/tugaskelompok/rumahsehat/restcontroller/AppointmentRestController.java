package apap.tugaskelompok.rumahsehat.restcontroller;

import apap.tugaskelompok.rumahsehat.dto.AppointmentDto;
import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.service.AppointmentRestService;
import apap.tugaskelompok.rumahsehat.service.DokterRestService;
import apap.tugaskelompok.rumahsehat.service.PasienRestService;
import apap.tugaskelompok.rumahsehat.service.TagihanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin
@RestController
@RequestMapping("/api/v1")
public class AppointmentRestController {
    private static Logger loggerLogger = LoggerFactory.getLogger(AppointmentRestController.class);
    @Autowired
    private DokterRestService dokterRestService;

    @Autowired
    private PasienRestService pasienRestService;

    @Autowired
    private AppointmentRestService appointmentRestService;

    @Autowired
    private TagihanService tagihanService;


    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/appointment/add")
    public AppointmentModel registerAppointment(@Valid @RequestBody AppointmentDto appointmentDto,
            BindingResult bindingResult) {
        var appointmentModel = modelMapper.map(appointmentDto, AppointmentModel.class);

        if (bindingResult.hasFieldErrors()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "failed");
        } else {

            var dokterModel = dokterRestService.getDokterById(appointmentModel.getDokter().getUuid());
            var pasienModel = pasienRestService.getPasienById(appointmentModel.getPasien().getUuid());
            if (dokterModel.getListAppointment() == null || dokterModel.getListAppointment().isEmpty()) {
                dokterModel.setListAppointment(new ArrayList<>());
            }
            if (pasienModel.getListAppointment() == null || pasienModel.getListAppointment().isEmpty()) {
                pasienModel.setListAppointment(new ArrayList<>());
            }

            LocalDate datePart = appointmentModel.getTanggalDimulai();
            LocalTime timePart = appointmentModel.getWaktuDimulai();
            var datetimeNewAppointment = LocalDateTime.of(datePart, timePart);
            LocalDateTime datetimeNewAppointmentOneHour = datetimeNewAppointment.plusHours(1);

            List<AppointmentModel> allAppointment = pasienModel.getListAppointment();

            if (datetimeNewAppointment.isBefore(LocalDateTime.now())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tanggal appointment harus setelah hari ini");
            }
            for (AppointmentModel appointmentOld : allAppointment) {
                LocalDate datePartOld = appointmentOld.getTanggalDimulai();
                LocalTime timePartOld = appointmentOld.getWaktuDimulai();

                var datetimeOldAppointment = LocalDateTime.of(datePartOld, timePartOld);
                LocalDateTime datetimeOldAppointmentOneHour = LocalDateTime.of(datePartOld, timePartOld).plusHours(1);
                if (checkBetween(datetimeOldAppointment, datetimeOldAppointmentOneHour, datetimeNewAppointment)
                        || checkBetween(datetimeOldAppointment, datetimeOldAppointmentOneHour,
                                datetimeNewAppointmentOneHour)) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Sudah ada appointment ditanggal tersebut");
                }
            }

            appointmentModel.setKode("APT-" + Integer.toString(appointmentRestService.getAllAppointment().size() + 1));
            var appointmentModel1 = appointmentRestService.createAppointment(appointmentModel);

            dokterModel.getListAppointment().add(appointmentModel);
            dokterRestService.updateDokter(dokterModel);

            pasienModel.getListAppointment().add(appointmentModel);
            pasienRestService.updatePasien(pasienModel);
            final var message = String.format("Successfully created Appointment with code %s", appointmentModel1.getKode());
            loggerLogger.info(message);
            return appointmentModel1;
        }
    }

    public boolean checkBetween(LocalDateTime start, LocalDateTime end, LocalDateTime cek) {
        if (cek.isAfter(start) && cek.isBefore(end)) { // salah
            return true;
        } else
            return false;
    }
    // x
    @GetMapping(value = "/appointment/{kode}")
    public AppointmentModel retrieveAppointment(@PathVariable("kode") String kode) {
        var appointmentModel = appointmentRestService.getAppointmentById(kode);
        final var message = String.format("Successfully get Appointment with code %s", kode);
        loggerLogger.info(message);

        if (appointmentModel != null) {
            return appointmentModel;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tidak ada appoinment dengan kode " + kode);
    }
    //x
    @GetMapping(value = "/appointment/tagihan/{kode}")
    public AppointmentModel retrieveAppointmentFromTagihan(@PathVariable("kode") String kode) {
        var tagihanModel = tagihanService.getTagihan(kode);
        var appointmentModel = appointmentRestService
                .getAppointmentById(tagihanModel.getAppointment().getKode());
        final var message = String.format("Successfully retrieved Appointment from tagihan %s", kode);
        loggerLogger.info(message);

        if (appointmentModel != null) {
            return appointmentModel;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tidak ada appoinment dari tagihan " + kode);
    }

    // Mengembalikan appointment miliki pasien x
    @GetMapping(value = "/list-appointment/pasien/{kode}")
    public List<AppointmentModel> retrieveListPasienAppointment(@PathVariable("kode") String kode) {
        var pasienModel = pasienRestService.getPasienById(kode);
        if (pasienModel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tidak ada pasien dengan kode " + kode);
        }
        List<AppointmentModel> listAppointmentPasien = pasienModel.getListAppointment();
        final var message = String.format("Successfully retrieved Appointment correlated with Pasien with id %s", kode);
        loggerLogger.info(message);

        return listAppointmentPasien;
    }

    // Mengembalikan appointment miliki dokter x
    @GetMapping(value = "/list-appointment/dokter/{kode}")
    public List<AppointmentModel> retrieveListDokterAppointment(@PathVariable("kode") String kode) {
        var dokterModel = dokterRestService.getDokterById(kode);
        if (dokterModel == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tidak ada dokter dengan kode " + kode);
        }
        List<AppointmentModel> listAppointmentDokter = dokterModel.getListAppointment();
        final var message = String.format("Successfully retrieved Appointment correlated with Dokter with id %s", kode);
        loggerLogger.info(message);

        return listAppointmentDokter;
    }

    // get all appointment x
    @GetMapping(value = "/list-appointment")
    public List<AppointmentModel> retrieveListAppointment() {
        loggerLogger.info("Successfully retrieved Appointment");
        return appointmentRestService.getAllAppointment();
    }

    // Update
    @PutMapping(value = "/appointment/{kode}")
    public AppointmentModel updateAppointment(@PathVariable("kode") String kode,
            @RequestBody AppointmentDto appointmentDto) {
        var appointmentModel = modelMapper.map(appointmentDto, AppointmentModel.class);

        try {
            final var message = String.format("Successfully updated Appointment with code %s", kode);
            loggerLogger.info(message);

            return appointmentRestService.updateAppointment(kode, appointmentModel);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Appointment " + kode + " not found");

        }
    }

    @DeleteMapping(value = "/appointment/delete/{kode}")
    public void deleteAppointment(@PathVariable("kode") String kode) {
        final var message = String.format("Appointment with code %s has been deleted", kode);
        loggerLogger.info(message);

        appointmentRestService.deleteAppointmentByCode(kode);
    }
}
