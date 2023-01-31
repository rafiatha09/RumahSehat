package apap.tugaskelompok.rumahsehat.controller;

import apap.tugaskelompok.rumahsehat.model.*;
import apap.tugaskelompok.rumahsehat.security.xml.ServiceResponse;
import apap.tugaskelompok.rumahsehat.service.*;
import apap.tugaskelompok.rumahsehat.setting.Setting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PageController {
    @Autowired
    private UserService userService;

    @Autowired
    ServerProperties serverProperties;

    @Autowired
    private ObatRestService obatRestService;

    @Autowired
    private ApotekerRestService apotekerRestService;

    @Autowired
    private DokterRestService dokterRestService;

    @Autowired
    private PasienRestService pasienRestService;

    @GetMapping("/")
    public String home(Model model, Authentication auth) {
        var securityContext = SecurityContextHolder.getContext();
        String username = securityContext.getAuthentication().getName();
        UserModel user = userService.getUserByUsername(username);
        String role = user.getRole();
        model.addAttribute("role", role);
        return "home";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    private WebClient webClient = WebClient.builder().build();

    @GetMapping("/validate-ticket")
    public ModelAndView adminLoginSSO(
            @RequestParam(value = "ticket", required = false) String ticket,
            HttpServletRequest request) {
                var serviceResponse = this.webClient.get().uri(
                String.format(
                        Setting.SERVER_VALIDATE_TICKET,
                        ticket,
                        Setting.CLIENT_LOGIN))
                .retrieve().bodyToMono(ServiceResponse.class).block();

        if (serviceResponse != null) {
            var attributes = serviceResponse.getAuthenticationSuccess().getAttributes();
            String username = serviceResponse.getAuthenticationSuccess().getUser();

            UserModel user = userService.getUserByUsername(username);

            if (user == null) {
                user = new UserModel();
                user.setEmail(username + "@ui.ac.id");
                user.setNama(attributes.getNama());
                user.setPassword("rumahsehat");
                user.setUsername(username);
                user.setRole("Admin");
                userService.addUser(user);
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, "rumahsehat");

            var securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authentication);

            var httpSession = request.getSession(true);
            httpSession.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, securityContext);
        }

        return new ModelAndView("redirect:/");
    }

    @GetMapping(value = "/login-sso")
    public ModelAndView loginSSO() {
        return new ModelAndView("redirect:" + Setting.SERVER_LOGIN + Setting.CLIENT_LOGIN);
    }

    @GetMapping(value = "/logout-sso")
    public ModelAndView logoutSSO(Principal principal) {
        return new ModelAndView("redirect:" + Setting.SERVER_LOGOUT + Setting.CLIENT_LOGOUT);
    }

    @GetMapping(value = "/create_dummy")
    public String createDummy(RedirectAttributes redirectAttrs) {
        if (apotekerRestService.getListApoteker().isEmpty()) {
            var pasienModel1 = new PasienModel();
            pasienModel1.setNama("Rafi (P1)");
            pasienModel1.setUsername("rafiatha11");
            pasienModel1.setPassword("pasien1");
            pasienModel1.setEmail("rafiatha11@gmail.com");
            pasienModel1.setRole("Pasien");
            pasienModel1.setSaldo(1000);
            pasienModel1.setUmur(20);


            var pasienModel2 = new PasienModel();
            pasienModel2.setNama("Fitri (P2)");
            pasienModel2.setUsername("pasienfitri");
            pasienModel2.setPassword("pasien2");
            pasienModel2.setEmail("Adham@gmail.com");
            pasienModel2.setRole("Pasien");
            pasienModel2.setSaldo(1000000);
            pasienModel2.setUmur(20);


            var apotekerModel2 = new ApotekerModel();
            apotekerModel2.setNama("Apoteker B (A1)");
            apotekerModel2.setUsername("apoteker B");
            apotekerModel2.setPassword("123456789");
            apotekerModel2.setEmail("apotekerb@gmail.com");
            apotekerModel2.setRole("Apoteker");


            var apotekerModel = new ApotekerModel();
            apotekerModel.setNama("Venska (A1)");
            apotekerModel.setUsername("apotekervenska");
            apotekerModel.setPassword("apoteker1");
            apotekerModel.setEmail("Lusi@gmail.com");
            apotekerModel.setRole("Apoteker");

            var dokterModel = new DokterModel();
            dokterModel.setNama("Nirwana (D1)");
            dokterModel.setUsername("dokternirwana");
            dokterModel.setPassword("dokter1");
            dokterModel.setEmail("Muthia@gmail.com");
            dokterModel.setRole("Dokter");
            dokterModel.setTarif(150000);

            var dokterModel2 = new DokterModel();
            dokterModel2.setNama("Dokter B (D1)");
            dokterModel2.setUsername("dokterb");
            dokterModel2.setPassword("123456789");
            dokterModel2.setEmail("dokterb@gmail.com");
            dokterModel2.setRole("Dokter");
            dokterModel2.setTarif(100000);

            var obatModel1 = new ObatModel();
            obatModel1.setId("0101010C0AAAAAA");
            obatModel1.setNama_obat("Alum Hydrox_Cap 475mg");
            obatModel1.setHarga(20000);
            obatModel1.setStok(10);

            var obatModel2 = new ObatModel();
            obatModel2.setId("0101010C0BBAAAA");
            obatModel2.setNama_obat("Alu-Cap_Cap 475mg");
            obatModel2.setHarga(60000);
            obatModel2.setStok(10);

            var obatModel3 = new ObatModel();
            obatModel3.setId("0101010E0AAAEAE");
            obatModel3.setNama_obat("Hydrotalcite_Oral Susp 500mg/5ml S/F");
            obatModel3.setHarga(40000);
            obatModel3.setStok(10);

            obatRestService.addObat(obatModel1);
            obatRestService.addObat(obatModel2);
            obatRestService.addObat(obatModel3);
            pasienRestService.createPasien(pasienModel1);
            pasienRestService.createPasien(pasienModel2);

            dokterRestService.addDokter(dokterModel2);
            dokterRestService.addDokter(dokterModel);

            apotekerRestService.addApoteker(apotekerModel);
            apotekerRestService.addApoteker(apotekerModel2);
            redirectAttrs.addFlashAttribute("acknowledge", "Successfully created dummy data!");
        } else {
            redirectAttrs.addFlashAttribute("acknowledge", "Already created dummy data!");
        }
        return "redirect:/";
    }
}