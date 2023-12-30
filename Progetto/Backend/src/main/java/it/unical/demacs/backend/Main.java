package it.unical.demacs.backend;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.User;

import java.sql.SQLException;
import java.util.concurrent.ExecutionException;


public class Main {

    // USARE SOLO PER TESTING
    public static void main(String[] args) throws SQLException, ExecutionException, InterruptedException {
        DatabaseHandler db = DatabaseHandler.getInstance();
        //db.getUtenteDao().saveOrUpdate(new User("Sav", "test", "Saverio", "Crea", "test", 'c'));
        System.out.println(db.getUtenteDao().findByPrimaryKey(6L).join().getUsername());
    }

}
