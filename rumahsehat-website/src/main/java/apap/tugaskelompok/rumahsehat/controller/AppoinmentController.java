package apap.tugaskelompok.rumahsehat.controller;

import apap.tugaskelompok.rumahsehat.model.AppointmentModel;
import apap.tugaskelompok.rumahsehat.service.AppointmentRestService;
import apap.tugaskelompok.rumahsehat.service.AppointmentService;
import apap.tugaskelompok.rumahsehat.service.TagihanService;
import apap.tugaskelompok.rumahsehat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("appointment")
public class AppoinmentController {
    @Autowired
    UserService userService;

    @Autowired
    AppointmentRestService appointmentRestService;

    @Autowired
    TagihanService tagihanService;

    @Autowired
    AppointmentService appointmentService;

    @GetMapping(value = "/viewall")
    public String getListApointment(Model model) {
        var userModel = userService.getLoggedUser();

        if (userModel.getRole().equals("Admin")) {
            List<AppointmentModel> listAdminAppointment = appointmentRestService.getAllAppointment();
            List<LocalTime> listEndAdminTime = appointmentService.getAdminEndTime();
            model.addAttribute("listAppointment", listAdminAppointment);
            model.addAttribute("listLocalTime", listEndAdminTime);
            return "viewall-appointment";

        } else { // Dokter

            List<AppointmentModel> listDokterAppointment = appointmentService
                    .getListDokterAppointment(userModel.getUuid());
            List<LocalTime> listEndDokterTime = appointmentService.getDokterEndTime(userModel.getUuid());
            model.addAttribute("listAppointment", listDokterAppointment);
            model.addAttribute("listLocalTime", listEndDokterTime);
            return "viewall-appointment";
        }
    }

    @GetMapping(value = "/detail/{kode}")
    public String getDetailAppointment(@PathVariable("kode") String kode, Model model) {
        var userModel = userService.getLoggedUser();
        var appointmentModel = appointmentRestService.getAppointmentByCode(kode);

        if (userModel.getRole().equals("Admin")) {
            model.addAttribute("role", 1);
        } else {
            model.addAttribute("role", 2);
        }
        LocalTime endTime = appointmentModel.getWaktuDimulai().plusHours(1);
        model.addAttribute("appointment", appointmentModel);
        model.addAttribute("endtime", endTime);
        return "view-appointment";
    }

    @GetMapping(value = "/done/{kode}")
    public String getDoneAppoitnment(@PathVariable("kode") String kode) {
        var appointmentModel = appointmentRestService.getAppointmentById(kode);
        // appointment tanpa resep, yang ada resep, di resep controller
        if (appointmentModel.getResep() == null) {
            var tagihanModel = tagihanService.createTagihan(appointmentModel, 0);
            appointmentModel.setDone(true);
            appointmentModel.setTagihan(tagihanModel);
            appointmentService.updateAppointment(appointmentModel.getKode(), appointmentModel);
        } else {
            appointmentModel.setDone(true);
            appointmentService.updateAppointment(appointmentModel.getKode(), appointmentModel);
        }
        return "redirect:/appointment/viewall/";
    }

}
