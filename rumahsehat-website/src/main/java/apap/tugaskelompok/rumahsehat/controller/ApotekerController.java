package apap.tugaskelompok.rumahsehat.controller;

import apap.tugaskelompok.rumahsehat.dto.ApotekerDto;
import apap.tugaskelompok.rumahsehat.model.ApotekerModel;
import apap.tugaskelompok.rumahsehat.service.ApotekerRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/apoteker")
public class ApotekerController {
    @Autowired
    private ApotekerRestService apotekerService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/add")
    public String addApotekerFormPage(Model model) {
        var apoteker = new ApotekerModel();
        model.addAttribute("apoteker", apoteker);
        return "form-add-apoteker";
    }

    @PostMapping(value = "/add")
    public String addApotekerSubmit(@ModelAttribute ApotekerDto apotekerDto, Model model) {
        ApotekerModel apoteker = modelMapper.map(apotekerDto, ApotekerModel.class);

        apotekerService.addApoteker(apoteker);
        return "redirect:/apoteker/viewall";
    }

    @GetMapping(value = "/viewall")
    public String getListApoteker(Model model) {
        List<ApotekerModel> listApoteker = apotekerService.getListApoteker();
        int jumlahApoteker = listApoteker.size();
        model.addAttribute("jumlahApoteker", jumlahApoteker);
        return "viewall-apoteker-datatables";
    }
}