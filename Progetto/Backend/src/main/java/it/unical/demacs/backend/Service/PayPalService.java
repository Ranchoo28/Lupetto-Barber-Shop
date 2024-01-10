package it.unical.demacs.backend.Service;

import com.braintreepayments.http.HttpResponse;
import com.paypal.core.PayPalEnvironment;
import com.paypal.core.PayPalHttpClient;
import com.paypal.orders.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PayPalService {

    private final PayPalHttpClient payPalClient;

    @Value("${paypal.return.url}")
    private String returnUrl;

    @Value("${paypal.cancel.url}")
    private String cancelUrl;

    public PayPalService(@Value("${paypal.client.id}") String clientId,
                         @Value("${paypal.client.secret}") String clientSecret) {
        PayPalEnvironment environment = new PayPalEnvironment.Live(clientId, clientSecret);
        this.payPalClient = new PayPalHttpClient(environment);
    }

    public String createPayment() throws IOException {
        OrdersCreateRequest request = new OrdersCreateRequest();
        request.requestBody(buildRequestBody());

        HttpResponse<Order> response = payPalClient.execute(request);

        // Ottenere l'URL di reindirizzamento per il pagamento da PayPal
        String redirectUrl = "";
        for (LinkDescription link : response.result().links()) {
            if ("approve".equals(link.rel())) {
                redirectUrl = link.href();
                break;
            }
        }

        return redirectUrl;
    }

    private OrderRequest buildRequestBody() {
        OrderRequest orderRequest = new OrderRequest();

        // Imposta l'intento di pagamento direttamente
        orderRequest.intent("CAPTURE");
        ApplicationContext applicationContext = new ApplicationContext().returnUrl(returnUrl).cancelUrl(cancelUrl);
        orderRequest.applicationContext(applicationContext);

        // Aggiungi dettagli del pagamento, come l'importo e la valuta
        orderRequest.purchaseUnits(buildPurchaseUnits());

        return orderRequest;
    }


    private List<PurchaseUnitRequest> buildPurchaseUnits() {
        PurchaseUnitRequest purchaseUnitRequest = new PurchaseUnitRequest();
        Item item = new Item().name("Prodotto di esempio").unitAmount(new Money().currencyCode("USD").value("100.00"));
        purchaseUnitRequest.items(List.of(item));

        return List.of(purchaseUnitRequest);
    }

    public String completePayment(String paymentId, String payerId) throws IOException {
        OrdersCaptureRequest request = new OrdersCaptureRequest(paymentId);
        request.requestBody(new Order().payer(new Customer().payerId(payerId)));

        HttpResponse<Order> response = payPalClient.execute(request);

        // Verifica lo stato della transazione
        if (response.statusCode() == 201 || response.statusCode() == 200) {
            // Transazione completata con successo
            return "Pagamento completato con successo. ID transazione: " + response.result().id();
        } else {
            // Gestisci gli errori in modo appropriato
            return "Errore durante il completamento del pagamento. Codice errore: " + response.statusCode();
        }
    }

}
