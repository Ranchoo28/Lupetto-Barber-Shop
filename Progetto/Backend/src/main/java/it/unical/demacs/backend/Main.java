package it.unical.demacs.backend;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.User;


public class Main {

    // USARE SOLO PER TESTING
    public static void main(String[] args) {
        DatabaseHandler db = DatabaseHandler.getInstance();
        //db.getUtenteDao().saveOrUpdate(new User("test", "test", "test", "test", "test", 'c'));
        System.out.println(db.getUtenteDao().findAll());
    }

}
