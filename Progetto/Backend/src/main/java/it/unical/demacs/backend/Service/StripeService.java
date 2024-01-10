package it.unical.demacs.backend.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StripeService {
    public ResponseEntity<?> createPaymentIntent(Integer amount, String description) {
        Stripe.apiKey = "sk_test_51OX4YdJkGG7c6naXMQlwyXAKL8VbqTIHdkDoyppqb1TroqyXI018oi4x1tvu8yRVhqU55SnATpl47JRMDvcC0GEB00wVCfTEqq";

        try {
            Map<String, Object> paymentIntentParams = new HashMap<>();
            paymentIntentParams.put("amount", amount);
            paymentIntentParams.put("currency", "eur");
            paymentIntentParams.put("description", description);

            List<String> paymentMethodTypes = new ArrayList<>();
            paymentMethodTypes.add("card");
            paymentIntentParams.put("payment_method_types", paymentMethodTypes);

            PaymentIntent paymentIntent = PaymentIntent.create(paymentIntentParams);

            return ResponseEntity.ok(paymentIntent.getClientSecret());

        } catch (StripeException e) {
            e.fillInStackTrace();
        }
        return ResponseEntity.badRequest().body("{\"message\": \"Invalid request\"}");
    }
}
