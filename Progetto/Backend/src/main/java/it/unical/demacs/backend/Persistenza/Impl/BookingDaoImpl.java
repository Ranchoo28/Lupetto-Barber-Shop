package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.BookingDao;
import it.unical.demacs.backend.Persistenza.Model.Booking;
import org.springframework.scheduling.annotation.Async;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class BookingDaoImpl implements BookingDao {
    Connection con;

    public BookingDaoImpl(Connection con) { this.con = con; }

    @Override
    @Async
    public CompletableFuture<ArrayList<Booking>> findAll() {
        ArrayList<Booking> bookingList = new ArrayList<>();
        String query = "SELECT * FROM bookings";
        try (
                Statement st = this.con.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setIdBooking(rs.getLong(1));
                booking.setIdUser(rs.getLong(2));
                booking.setIdService(rs.getLong(3));
                booking.setDate(rs.getDate(4).toLocalDate());
                booking.setTime(rs.getTime(5));
                bookingList.add(booking);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(bookingList);
    }

    @Override
    @Async
    public CompletableFuture<Booking> findByPrimaryKey(Long id) {
        Booking booking=new Booking();
        String query = "SELECT * FROM bookings WHERE id_booking = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    booking.setIdBooking(rs.getLong(1));
                    booking.setIdUser(rs.getLong(2));
                    booking.setIdService(rs.getLong(3));
                    booking.setDate(rs.getDate(4).toLocalDate());
                    booking.setTime(rs.getTime(5));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(booking);
    }
    @Override
    public CompletableFuture<Booking> findByUsername(String username) {
        Booking booking=new Booking();
        String query = "SELECT * FROM bookings WHERE username = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setString(1, username);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    booking.setIdBooking(rs.getLong(1));
                    booking.setIdUser(rs.getLong(2));
                    booking.setIdService(rs.getLong(3));
                    booking.setDate(rs.getDate(4).toLocalDate());
                    booking.setTime(rs.getTime(5));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(booking);
    }

    @Override
    public CompletableFuture<Void> saveOrUpdate(Booking booking) {
        String query = "INSERT INTO bookings (id_booking, id_user, id_service, data, ora) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, booking.getIdBooking());
            st.setLong(2, booking.getIdUser());
            st.setLong(3, booking.getIdService());
            st.setDate(4, Date.valueOf(booking.getDate()));
            st.setTime(5, booking.getTime());
            st.executeUpdate();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> delete(Long id) {
        String query = "DELETE FROM bookings WHERE id_booking = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, id);
            st.executeUpdate();
            st.close();
        } catch (SQLException ignored) {}

        return CompletableFuture.completedFuture(null);
    }
}
