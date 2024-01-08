package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.HairdresserDao;
import it.unical.demacs.backend.Persistenza.Model.Booking;
import it.unical.demacs.backend.Persistenza.Model.Hairdresser;
import it.unical.demacs.backend.Persistenza.Model.User;
import org.springframework.scheduling.annotation.Async;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class HairdresserDaoImpl implements HairdresserDao {
    Connection con;

    public HairdresserDaoImpl(Connection con) { this.con = con; }

    @Override
    public CompletableFuture<ArrayList<Hairdresser>> findAll() {
        ArrayList<Hairdresser> hairdressers = new ArrayList<>();
        String query = "SELECT * FROM hairdressers";
        try (
                Statement st = this.con.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Hairdresser hairdresser = new Hairdresser();
                hairdresser.setId_hairdresser(rs.getLong(1));
                hairdresser.setUsername(rs.getString(2));
                hairdresser.setPassword(rs.getString(3));
                hairdressers.add(hairdresser);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(hairdressers);
    }

    @Override
    public CompletableFuture<Hairdresser> findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    @Async
    public CompletableFuture<Hairdresser> findByUsername(String username) {
        Hairdresser hairdresser = new Hairdresser();
        String query = "SELECT * FROM hairdressers WHERE username = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
              hairdresser.setId_hairdresser(rs.getLong(1));
              hairdresser.setUsername(rs.getString(2));
              hairdresser.setPassword(rs.getString(3));

            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(hairdresser);
    }

    @Override
    public CompletableFuture<Boolean> addService(Long idService) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> removeService(Long idHairdresser, Long idService) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> insertUser(Long idHairdresser) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> modifyBooking(Long idHairdresser, Long idBooking) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> removeBooking(Long idHairdresser, Long idBooking) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> insertProducts(Long idHairdresser, Long idProduct) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> removeProducts(Long idHairdresser, Long idProduct) {
        return null;
    }
}
