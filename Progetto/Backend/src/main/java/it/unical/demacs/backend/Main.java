package it.unical.demacs.backend;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.util.concurrent.ExecutionException;

import it.unical.demacs.backend.Service.PayPalService;
import okio.*;
import okhttp3.*;


public class Main {

    // USARE SOLO PER TESTING
    public static void main2(String[] args) throws SQLException, ExecutionException, InterruptedException {
        DatabaseHandler db = DatabaseHandler.getInstance();
        //db.getUtenteDao().saveOrUpdate(new User("Sav", "test", "Saverio", "Crea", "test", 'c'));
        System.out.println(db.getUtenteDao().findByPrimaryKey(6L).join().getUsername());
    }

    public static void main1(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n    \"recipient_type\": \"individual\",\r\n \"to\": \"numero\",\r\n    \"type\": \"text\",\r\n    \"text\": {\r\n        \"body\": \"Culo\"\r\n    }\r\n}");
        Request request = new Request.Builder()
                .url("https://whatsapp-business.p.rapidapi.com/messages")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "<REQUIRED>")
                .addHeader("X-RapidAPI-Key", "981a9b900emshaac8acbfe406080p14a554jsnc7d1f517aa45")
                .addHeader("X-RapidAPI-Host", "whatsapp-business.p.rapidapi.com")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response);
    }
}


