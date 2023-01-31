package apap.tugaskelompok.rumahsehat.controller;

import apap.tugaskelompok.rumahsehat.model.DokterModel;
import apap.tugaskelompok.rumahsehat.dto.DTODokterIncome;
import apap.tugaskelompok.rumahsehat.dto.DokterDto;
import apap.tugaskelompok.rumahsehat.service.DokterRestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/dokter")
public class DokterController {
    @Autowired
    private DokterRestService dokterService;

    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping(value = "/add")
    public String addDokterFormPage(Model model) {
        var dokter = new DokterModel();

        model.addAttribute("dokter", dokter);
        return "form-add-dokter";
    }

    @PostMapping(value = "/add")
    public String addDokterSubmit(@ModelAttribute DokterDto dokterDto, Model model) {
        DokterModel dokter = modelMapper.map(dokterDto, DokterModel.class);

        dokterService.addDokter(dokter);

        return "redirect:/dokter/viewall";
    }

    @GetMapping(value = "/viewall")
    public String getListDokter(Model model) {
        List<DokterModel> listDokter = dokterService.getListDokter();
        int jumlahDokter = listDokter.size();
        model.addAttribute("jumlahDokter", jumlahDokter);
        return "viewall-dokter-datatables";
    }

    @GetMapping(value = "/chart")
    public String getOverallChart(Model model) {
        List<DokterModel> listDokter = dokterService.getListDokter();
        String[] months = {"January", "February", 
        "March", "April", "May", "June", "July", "August", "September", 
        "October", "November", "December"};
        List<DTODokterIncome> listDokterIncome = new ArrayList<>();
        for(var i = 0; i < listDokter.size(); i++){
            var dataChart = new DTODokterIncome(listDokter.get(i));
            dataChart.getResultYear();
            listDokterIncome.add(dataChart);
        }

        model.addAttribute("months", months);
        model.addAttribute("values", (listDokterIncome.get(0)).getPemasukan().values());
        return "highchart-dokter";
    }

}
