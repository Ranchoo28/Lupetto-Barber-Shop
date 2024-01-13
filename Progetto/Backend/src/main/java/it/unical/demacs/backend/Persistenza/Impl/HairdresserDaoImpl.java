package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.HairdresserDao;
import it.unical.demacs.backend.Persistenza.Model.Hairdresser;
import it.unical.demacs.backend.Service.Response.HairdresserBookingResponse;
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
                hairdresser.setEmail(rs.getString(2));
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
        Hairdresser hairdresser = new Hairdresser();
        String query = "SELECT * FROM hairdressers WHERE id_hairdresser = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                hairdresser.setId_hairdresser(rs.getLong(1));
                hairdresser.setEmail(rs.getString(2));
                hairdresser.setPassword(rs.getString(3));
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(hairdresser);
    }

    @Override
    @Async
    public CompletableFuture<Hairdresser> findByEmail(String email) {
        Hairdresser hairdresser = new Hairdresser();
        String query = "SELECT * FROM hairdressers WHERE email = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
              hairdresser.setId_hairdresser(rs.getLong(1));
              hairdresser.setEmail(rs.getString(2));
              hairdresser.setPassword(rs.getString(3));

            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(hairdresser);
    }

    @Override
    public CompletableFuture<ArrayList<HairdresserBookingResponse>> findAllBookings() {
        String query =
                "SELECT b.id_booking, u.name, u.surname, s.name, bd.data, bd.ora " +
                        "FROM users as u, services as s, bookings as b, bookingsdate as bd " +
                        "WHERE u.id_user = b.id_user and b.id_bookingdate = bd.id_bookingdate " +
                        "and bd.id_service = s.id_service and bd.isvalid = false";
        ArrayList<HairdresserBookingResponse> bookings = new ArrayList<>();
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                HairdresserBookingResponse booking = new HairdresserBookingResponse();
                booking.setId_booking(rs.getLong(1));
                booking.setUser_name(rs.getString(2));
                booking.setUser_surname(rs.getString(3));
                booking.setService_name(rs.getString(4));
                booking.setDate(rs.getDate(5).toLocalDate());
                booking.setTime(rs.getTime(6));
                bookings.add(booking);
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(bookings);
    }

    @Override
    public CompletableFuture<Boolean> insert(Hairdresser hairdresser) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> delete(Long id) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> update(Hairdresser hairdresser) {
        return null;
    }


}
