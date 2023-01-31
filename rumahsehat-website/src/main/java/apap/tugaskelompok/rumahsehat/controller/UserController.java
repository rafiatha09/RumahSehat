package apap.tugaskelompok.rumahsehat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/user")
    public String userMenu() {
        return "user-menu";
    }
}
