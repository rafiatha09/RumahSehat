package apap.tugaskelompok.rumahsehat.controller;

import apap.tugaskelompok.rumahsehat.model.PasienModel;
import apap.tugaskelompok.rumahsehat.service.PasienRestService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/pasien")
public class PasienController {
    @Autowired
    private PasienRestService pasienService;

    @GetMapping(value = "/viewall")
    public String getListPasien(Model model) {
        List<PasienModel> listPasien = pasienService.getAllPasien();
        int jumlahPasien = listPasien.size();

        var role = true;
        model.addAttribute("role", role);
        model.addAttribute("jumlahPasien", jumlahPasien);

        return "viewall-pasien-datatables";
    }
}
