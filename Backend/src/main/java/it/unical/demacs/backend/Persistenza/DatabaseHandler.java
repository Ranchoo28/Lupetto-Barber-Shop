package it.unical.demacs.backend.Persistenza;

import it.unical.demacs.backend.Persistenza.DAO.UserDao;
import it.unical.demacs.backend.Persistenza.Impl.UserDaoImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public UserDao getUtenteDao(){
        return new UserDaoImpl(getConnection());
    }
}
