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
        DatabaseHandler db = DatabaseHandler.getInstance();
        // System.out.println(db.getCartDao().findAll().join());  worka
        // System.out.println(db.getCartDao().findUserByCartId(1L).join());  worka
        // System.out.println(db.getCartDao().findByPrimaryKey(1L).join()); worka

        // System.out.println(db.getProductCartDao().findAll().join()); worka
        // System.out.println(db.getProductCartDao().findCartByIdProductCart(2L).join()); worka
        // System.out.println(db.getProductCartDao().findProductByIdCart(1L).join()); worka
        //System.out.println(db.getProductDao().findAllByCartId(1L).join()); worka

    }
}


