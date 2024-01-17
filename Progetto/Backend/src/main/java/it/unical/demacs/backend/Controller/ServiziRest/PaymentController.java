package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Service.StripeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class PaymentController {
    private final StripeService stripeService;

    @GetMapping("/api/payment/create")
    public ResponseEntity<?> createPaymentIntent(@RequestParam Integer amount, @RequestParam String description) {
        return stripeService.createPaymentIntent(amount, description);
    }


}
