package it.unical.demacs.backend;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

import okio.*;
import okhttp3.*;


import static spark.Spark.post;
import static spark.Spark.port;
import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.*;
import com.stripe.net.Webhook;

public class Main {

    // USARE SOLO PER TESTING
    public static void main(String[] args) throws SQLException, ExecutionException, InterruptedException {
        DatabaseHandler db = DatabaseHandler.getInstance();
        //db.getUtenteDao().saveOrUpdate(new User("Sav", "test", "Saverio", "Crea", "test", 'c'));
        System.out.println(db.getUtenteDao().findByPrimaryKey(6L).join().getUsername());
    }

    public static void main1(String[] args) {
        // The library needs to be configured with your account's secret key.
        // Ensure the key is kept out of any version control system you might be using.
        Stripe.apiKey = "sk_test_...";

        // This is your Stripe CLI webhook secret for testing your endpoint locally.
        String endpointSecret = "whsec_290583701dec7ea074015cfce0efd0a24fea73f5b15a62ea81bc1e0e6e36c13a";

        port(4242);

        post("/webhook", (request, response) -> {
            String payload = request.body();
            String sigHeader = request.headers("Stripe-Signature");
            Event event = null;

            try {
                event = Webhook.constructEvent(
                        payload, sigHeader, endpointSecret
                );
            } catch (JsonSyntaxException e) {
                // Invalid payload
                response.status(400);
                return "";
            } catch (SignatureVerificationException e) {
                // Invalid signature
                response.status(400);
                return "";
            }

            // Deserialize the nested object inside the event
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            StripeObject stripeObject;
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                // Deserialization failed, probably due to an API version mismatch.
                // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
                // instructions on how to handle this case, or return an error here.
            }
            // Handle the event
            switch (event.getType()) {
                case "payment_intent.succeeded": {
                    // Then define and call a function to handle the event payment_intent.succeeded
                    break;
                }
                // ... handle other event types
                default:
                    System.out.println("Unhandled event type: " + event.getType());
            }

            response.status(200);
            return "";
        });
    }

}


