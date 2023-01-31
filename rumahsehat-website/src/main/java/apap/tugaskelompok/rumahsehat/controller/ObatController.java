package apap.tugaskelompok.rumahsehat.controller;

import apap.tugaskelompok.rumahsehat.dto.ObatDto;
import apap.tugaskelompok.rumahsehat.model.ObatModel;
import apap.tugaskelompok.rumahsehat.service.ObatRestService;
import apap.tugaskelompok.rumahsehat.service.UserService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/obat")
public class ObatController {
    @Autowired
    private ObatRestService obatService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/add")
    public String addObatFormPage(Model model) {
        var obat = new ObatModel();

        model.addAttribute("obat", obat);
        return "form-add-obat";
    }

    @PostMapping(value = "/add")
    public String addObatSubmit(@ModelAttribute ObatDto obatDto, Model model) {
        ObatModel obat = modelMapper.map(obatDto, ObatModel.class);

        // If user click "SUBMIT" without filling stock
        if (obat.getStok() == null) {
            obat.setStok(100);
        }
        obatService.addObat(obat);

        return "redirect:/obat/viewall";
    }

    @GetMapping(value = "/viewall")
    public String getListObat(Model model) {
        List<ObatModel> listObat = obatService.getListObat();
        int jumlahObat = listObat.size();

        var securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        var userModel = userService.getUserByUsername(username);

        var testD = false;
        if (userModel.getRole().equals("Apoteker")) {
            testD = true;
        }

        model.addAttribute("testD", testD);
        model.addAttribute("jumlahObat", jumlahObat);

        return "viewall-obat-datatables";
    }

    @GetMapping(value = "/ubah-stock/{idObat}")
    public String updateStockObatFormPage(@PathVariable String idObat, Model model) {
        ObatModel obat = obatService.getObatById(idObat);

        model.addAttribute("obat", obat);
        return "form-update-stock-obat";
    }

    @PostMapping(value = "/ubah-stock")
    public String updateStockObatSubmit(@ModelAttribute ObatDto obatDto, Model model) {
        ObatModel obat = modelMapper.map(obatDto, ObatModel.class);

        // If user click "SIMPAN" without changing the stock
        if (obat.getStok() == null) {
            ObatModel nonEditedObat = obatService.getObatById(obat.getId());
            obatService.addObat(nonEditedObat);
        } else {
            obatService.addObat(obat);
        }

        return "redirect:/obat/viewall";
    }
}
