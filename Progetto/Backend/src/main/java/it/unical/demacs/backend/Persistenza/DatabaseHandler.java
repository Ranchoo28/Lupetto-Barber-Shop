package it.unical.demacs.backend.Persistenza;

import it.unical.demacs.backend.Persistenza.DAO.*;
import it.unical.demacs.backend.Persistenza.Impl.*;
import org.springframework.scheduling.annotation.Async;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class DatabaseHandler {
    private static DatabaseHandler instance = null;

    private DatabaseHandler(){}

    public static DatabaseHandler getInstance(){
        if (instance == null){
            instance = new DatabaseHandler();
        }
        return instance;
    }

    Connection con = null;

    public Connection getConnection(){
        if (con == null){
            try {
                con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Salone", "postgres", "postgres");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return con;
    }

    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }

    public UserDao getUtenteDao(){
        return new UserDaoImpl(getConnection());
    }
    public BookingDao getBookingDao() {return new BookingDaoImpl(getConnection());}
    public ServiceDao getServiceDao() { return new ServiceDaoImpl(getConnection());}
    public ProductDao getProductDao() { return new ProductDaoImpl(getConnection());}
    public HairdresserDao getHairdresserDao() { return new HairdresserDaoImpl(getConnection());}
    public BookingDateDao getBookingDateDao() { return new BookingDateImpl(getConnection());}
}
