package apap.tugaskelompok.rumahsehat.controller;

import apap.tugaskelompok.rumahsehat.dto.ResepDto;
import apap.tugaskelompok.rumahsehat.model.*;
import apap.tugaskelompok.rumahsehat.service.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/resep")
public class ResepController {

    @Autowired
    private ResepService resepService;

    @Autowired
    private ObatRestService obatService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private JumlahService jumlahService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagihanService tagihanService;

    @Autowired
    private ApotekerRestService apotekerRestService;

    @Autowired
    private ModelMapper modelMapper;

    String resepStr = "resep";
    String listObatExistingStr = "listObatExisting";
    String formAddResepStr = "form-add-resep";

    @GetMapping("/create/{codeAppointment}")
    public String addResepFormPage(Model model, @PathVariable String codeAppointment) {
        var resep = new ResepModel();
        List<ObatModel> listObat = obatService.getListObat();

        // resep ke appointment
        AppointmentModel appointment = appointmentService.getAppointment(codeAppointment);
        resep.setAppointment(appointment);

        var jumlahResepObat = new JumlahResepObatModel();
        resep.setListJumlahResepObat(new ArrayList<>());
        resep.getListJumlahResepObat().add(jumlahResepObat);

        model.addAttribute(resepStr, resep);
        model.addAttribute(listObatExistingStr, listObat);
        return formAddResepStr;
    }

    @PostMapping(value = "/create", params = { "deleteRow" })
    public String deleteResepMultiple(@ModelAttribute ResepDto resepDto, @RequestParam("deleteRow") Integer row,
            Model model) {
        ResepModel resep = modelMapper.map(resepDto, ResepModel.class);

        resep.getListJumlahResepObat().remove(row.intValue());

        List<ObatModel> listObat = obatService.getListObat();

        model.addAttribute(resepStr, resep);
        model.addAttribute(listObatExistingStr, listObat);

        return formAddResepStr;
    }

    @PostMapping(value = "/create", params = { "addRow" })
    public String addRowResepMultiple(@ModelAttribute ResepDto resepDto, Model model) {
        ResepModel resep = modelMapper.map(resepDto, ResepModel.class);

        if (resep.getListJumlahResepObat() == null || resep.getListJumlahResepObat().isEmpty()) {
            resep.setListJumlahResepObat(new ArrayList<>());
        }
        resep.getListJumlahResepObat().add(new JumlahResepObatModel());
        List<ObatModel> listObat = obatService.getListObat();
        model.addAttribute(resepStr, resep);
        model.addAttribute(listObatExistingStr, listObat);
        return formAddResepStr;
    }

    @PostMapping(value = "/create")
    public String addSubmitResep(@ModelAttribute ResepDto resepDto, Model model) {
        ResepModel resep = modelMapper.map(resepDto, ResepModel.class);

        var appointmentModel = resep.getAppointment();
        List<JumlahResepObatModel> jumlahResepObat = resep.getListJumlahResepObat(); // disimpen
        Long idResep = (long) resepService.getListResep().size() + 1;
        resep.setId(idResep);
        resep.setDone(false);
        resep.setCreatedAt(LocalDate.now());
        resep.setListJumlahResepObat(new ArrayList<>());
        // appointment ke resep

        resepService.addResep(resep);
        for (JumlahResepObatModel jumlah : jumlahResepObat) {
            ObatModel obat = obatService.getObatById(jumlah.getObat().getId());
            jumlah.setId((long) jumlahService.allJumlahResepObat().size() + 1);
            jumlah.setResep(resep);
            jumlah.setObat(obat);

            if (obat.getListJumlahResepObat() == null || obat.getListJumlahResepObat().isEmpty()) {
                obat.setListJumlahResepObat(new ArrayList<>());
            }
            // resep ke jumlah
            resep.getListJumlahResepObat().add(jumlah);
            // obat ke jumlah
            obat.getListJumlahResepObat().add(jumlah);
            obatService.addObat(obat);
        }
        appointmentModel.setResep(resep);
        appointmentService.updateAppointment(appointmentModel.getKode(), appointmentModel);
        return "redirect:/";
    }

    @GetMapping("")
    public String listResep(Model model) {
        List<ResepModel> listResep = resepService.getListResep();
        model.addAttribute("listResep", listResep);
        return "view-daftar-resep";
    }

    @GetMapping("/viewDetail/{idResep}")
    public String viewDetailResep(Model model, @PathVariable String idResep, Authentication authentication) {
        var resepModel = resepService.getResepById(Long.parseLong(idResep));
        var dokterModel = resepModel.getAppointment().getDokter();
        var pasienModel = resepModel.getAppointment().getPasien();
        var apotekerModel = resepModel.getApoteker();

        var securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName(); // return username
        var userModel = userService.getUserByUsername(username);

        var role = 0;
        if (userModel.getRole().equals("Apoteker")) {
            role = 1;
        }
        model.addAttribute("role", role);
        model.addAttribute("idResep", idResep);
        model.addAttribute(resepStr, resepModel);
        model.addAttribute("dokter", dokterModel);
        model.addAttribute("pasien", pasienModel);
        model.addAttribute("apoteker", apotekerModel);
        return "view-detail-resep";
    }

    @PostMapping("/viewDetail/{idResep}")
    public ModelAndView confirmResep(@PathVariable String idResep, Model model, RedirectAttributes redirectAttributes) {
        var resepModel = resepService.getResepById(Long.parseLong(idResep));
        List<JumlahResepObatModel> jumlahResepObatModel = resepModel.getListJumlahResepObat();

        Integer hargaObat = 0;

        // cek stok
        for (JumlahResepObatModel jumlahResepObatModel1 : jumlahResepObatModel) {
            var obatModel = jumlahResepObatModel1.getObat();
            Integer kuantitas = jumlahResepObatModel1.getKuantitas();
            Integer harga = obatModel.getHarga() * kuantitas;

            if (!obatService.cekStok(obatModel, kuantitas)) {
                resepModel.setDone(false);
                redirectAttributes.addFlashAttribute("isDone", resepModel.isDone());
                redirectAttributes.addFlashAttribute("message", "Maaf, stok obat kurang");
                return new ModelAndView("redirect:/resep/viewDetail/" + idResep);
            }
            hargaObat += harga;
        }
        for (JumlahResepObatModel jumlahResepObatModel1 : jumlahResepObatModel) {
            var obatModel = jumlahResepObatModel1.getObat();
            int stokObatAwal = obatModel.getStok();
            obatModel.setStok(stokObatAwal - jumlahResepObatModel1.getKuantitas());
        }

        // Membuat Tagihan ketika resep ada
        // Yang tidak ada, berada di Appointment Controller
        var appointmentModel = resepModel.getAppointment();
        var tagihanModel = tagihanService.createTagihan(appointmentModel, hargaObat);

        // Mengupdate Appointment
        appointmentModel.setTagihan(tagihanModel);
        appointmentService.updateAppointment(appointmentModel.getKode(), appointmentModel);

        // Get Apoteker
        var userModel = userService.getLoggedUser();
        var apotekerModel = apotekerRestService.getApotekerById(userModel.getUuid());

        // Mengupdate Resep
        resepModel.setApoteker(apotekerModel);
        resepModel.setDone(true);
        resepService.addResep(resepModel);

        // Mengupdate Apoterker
        if (apotekerModel.getListResep() == null || apotekerModel.getListResep().isEmpty()) {
            apotekerModel.setListResep(new ArrayList<>());
        }
        apotekerModel.getListResep().add(resepModel);
        apotekerRestService.updateApoterker(apotekerModel);

        redirectAttributes.addFlashAttribute("isDone", resepModel.isDone());
        return new ModelAndView("redirect:/resep/viewDetail/" + idResep);
    }
}
