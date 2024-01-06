package it.unical.demacs.backend.Controller.ServiziRest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {
    @GetMapping("/contatti")
    public String getContatti(Model model) {
        model.addAttribute("title", "Contatti Parrucchiere");
        model.addAttribute("telefono", "1234567890");
        model.addAttribute("email", "info@parrucchiere.it");
        model.addAttribute("indirizzo", "Via Roma, 1, 12345 Città, Provincia");
        return "contatti";
    }
}
