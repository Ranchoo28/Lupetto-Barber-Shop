package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.BookingDao;
import it.unical.demacs.backend.Persistenza.Model.Booking;
import it.unical.demacs.backend.Persistenza.Model.BookingDate;
import it.unical.demacs.backend.Persistenza.Model.User;
import org.springframework.scheduling.annotation.Async;

import java.sql.*;
import java.time.LocalDate;
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
                booking.setUser(new User(rs.getLong(2)));
                booking.setBookingDate(new BookingDate(rs.getLong(3)));
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
                    booking.setUser(new User(rs.getLong(2)));
                    booking.setBookingDate(new BookingDate(rs.getLong(3)));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(booking);
    }

    @Override
    @Async
    public CompletableFuture<Boolean> insert(Booking booking) {
        String query = "INSERT INTO bookings ( id_user, id_bookingdate) VALUES (?, ?)";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, booking.getUser().getIdUser());
            st.setLong(2, booking.getBookingDate().getIdBookingDate());
            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    @Async
    public CompletableFuture<Boolean> update(Booking booking) {
        String query = "UPDATE bookings SET id_booking=?, id_user=?, id_bookingdate=? WHERE id_booking=?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, booking.getIdBooking());
            st.setLong(1, booking.getUser().getIdUser());
            st.setLong(2, booking.getBookingDate().getIdBookingDate());
            int rowsAffected = st.executeUpdate();
            st.close();
            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    @Async
    public CompletableFuture<Boolean> delete(Long idBooking) {
        String query = "DELETE FROM bookings WHERE id_booking = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, idBooking);
            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException e) {
            e.fillInStackTrace();
            return CompletableFuture.completedFuture(false);
        }
    }

    @Override
    @Async
    public CompletableFuture<Boolean> isValid(Long id, Long idUser) {
        String query = "SELECT COUNT(*) FROM bookings WHERE id_booking = ? and id_user = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            st.setLong(2, idUser);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return CompletableFuture.completedFuture(rs.getInt(1) > 0);
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    @Async
    public CompletableFuture<ArrayList<Booking>> findByDate(LocalDate date) {
        ArrayList<Booking> bookingList = new ArrayList<>();
        String query = "SELECT * FROM bookings INNER JOIN bookingsDate ON bookings.id_bookingdate = bookingsDate.id_bookingdate WHERE bookingsDate.data = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
                st.setDate(1, Date.valueOf(date));
                st.setDate(1, Date.valueOf(date));
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        Booking booking=new Booking();
                        booking.setIdBooking(rs.getLong(1));
                        booking.setUser(new User(rs.getLong(2)));
                        booking.setBookingDate(new BookingDate(rs.getLong(3)));
                        bookingList.add(booking);
                    }
                }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(bookingList);
    }


}
