package apap.tugaskelompok.rumahsehat.controller;

import apap.tugaskelompok.rumahsehat.model.TagihanModel;
import apap.tugaskelompok.rumahsehat.service.TagihanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("tagihan")
public class TagihanController {
    @Autowired
    TagihanService tagihanService;

    @GetMapping("/viewall")
    public String listTagihan(Model model) {
        List<TagihanModel> listTagihan = tagihanService.getAllTagihan();
        model.addAttribute("listTagihan", listTagihan);
        return "viewall-tagihan";
    }

}
