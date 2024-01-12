package it.unical.demacs.backend;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.BookingDate;
import it.unical.demacs.backend.Persistenza.Model.Service;
import it.unical.demacs.backend.Persistenza.Model.User;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
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
    /*
    public static void main(String[] args) throws SQLException, ExecutionException, InterruptedException {
        DatabaseHandler db = DatabaseHandler.getInstance();
        //db.getUtenteDao().saveOrUpdate(new User("Sav", "test", "Saverio", "Crea", "test", 'c'));
        System.out.println(db.getUtenteDao().findByPrimaryKey(6L).join().getUsername());
    }*/

    public static void main(String[] args) {
        LocalDate currentDate = LocalDate.now();
        ArrayList<LocalDate> dateList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            dateList.add(currentDate.plusDays(i));
        }
        DatabaseHandler.getInstance().openConnection();
        ArrayList<Service> services = DatabaseHandler.getInstance().getServiceDao().findAll().join();
        for(int k=0; k<dateList.size(); k++)
        {
            Time time = new Time(9, 0, 0);
            int duration=0;
            for(int k1=0; k1<services.size(); k1++)
            {
                duration = services.get(k1).getDuration();
                if(time.getHours() == 12 && time.getMinutes()+duration >= 59)
                    time = new Time(14, 30, 0);
                if(time.getHours() == 18 && time.getMinutes()+duration >= 59)
                    break;
                BookingDate bookingDate = new BookingDate(services.get(k1), Date.valueOf(dateList.get(k)), time, true);
                DatabaseHandler.getInstance().getBookingDateDao().insert(bookingDate);
                time=Time.valueOf(time.toLocalTime().plusMinutes(duration));
            }
        }
        DatabaseHandler.getInstance().closeConnection();
    }
}


