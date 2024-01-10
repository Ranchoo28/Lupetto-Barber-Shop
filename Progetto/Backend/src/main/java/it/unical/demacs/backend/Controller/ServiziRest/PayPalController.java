package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Service.PayPalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PayPalController {

    private final PayPalService payPalService;

    @PostMapping("/create-payment")
    public ResponseEntity<String> createPayment() throws IOException {
        // Logica per creare un pagamento e ottenere l'URL di reindirizzamento da PayPal
        String redirectUrl = payPalService.createPayment();

        return ResponseEntity.ok(redirectUrl);
    }

    @GetMapping("/complete-payment")
    public ResponseEntity<String> completePayment(@RequestParam("paymentId") String paymentId,
                                                  @RequestParam("payerId") String payerId) throws IOException {
        // Logica per completare il pagamento dopo il reindirizzamento da PayPal
        String confirmationMessage = payPalService.completePayment(paymentId, payerId);

        return ResponseEntity.ok(confirmationMessage);
    }
}

