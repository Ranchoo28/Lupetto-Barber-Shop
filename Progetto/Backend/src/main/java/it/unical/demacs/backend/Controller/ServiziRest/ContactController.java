package it.unical.demacs.backend.Controller.ServiziRest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@Controller


public class ContactController {
    @Value("${contacts.number}")
    private String number;

    @Value("${contacts.email}")
    private String email;

    @Value("${contacts.address}")
    private String address;

    @GetMapping("/thymeleaf")
    public String getContatti(Model model) {
        model.addAttribute("title", "Contatti");
        model.addAttribute("number", number);
        model.addAttribute("email", email);
        model.addAttribute("address", address);
        return "thymeleafcontatti";
    }
}
