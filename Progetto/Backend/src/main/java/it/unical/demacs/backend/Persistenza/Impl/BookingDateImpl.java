package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.BookingDateDao;
import it.unical.demacs.backend.Persistenza.Model.Booking;
import it.unical.demacs.backend.Persistenza.Model.BookingDate;
import org.springframework.scheduling.annotation.Async;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class BookingDateImpl implements BookingDateDao {
    Connection con;

    public BookingDateImpl(Connection connection){
        this.con = connection;
    }

    @Override
    @Async
    public CompletableFuture<ArrayList<BookingDate>> findAll() {
        String query = "SELECT * FROM bookingsdate WHERE id_bookingdate = ?";
        ArrayList<BookingDate> bookingList = new ArrayList<>();
        try (
                Statement st = this.con.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                BookingDate bookingDate = new BookingDate();
                bookingDate.setIdBookingDate(rs.getLong(1));
                bookingDate.setIdService(rs.getLong(2));
                bookingDate.setDate(rs.getDate(3));
                bookingDate.setTime(rs.getTime(4));
                bookingDate.setIsValid(rs.getBoolean(5));
                bookingList.add(bookingDate);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(bookingList);
    }

    @Override
    @Async
    public CompletableFuture<BookingDate> findByPrimaryKey(Long id) {
        String query = "SELECT * FROM bookingsdate WHERE id_bookingdate = ?";
        return CompletableFuture.completedFuture(executeFindBy(query));
    }

    @Override
    @Async
    public CompletableFuture<BookingDate> findByDate(Date date) {
        String query = "SELECT * FROM bookingsdate WHERE data = ?";
        return CompletableFuture.completedFuture(executeFindBy(query));
    }

    @Override
    @Async
    public CompletableFuture<Boolean> insert(BookingDate bookingdate) {
        String query = "INSERT INTO bookingsdate (id_service, data, ora, isvalid) VALUES (?, ?, ?,?)";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, bookingdate.getIdService());
            st.setDate(2, bookingdate.getDate());
            st.setTime(3, bookingdate.getTime());
            st.setBoolean(4, bookingdate.getIsValid());
            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    public CompletableFuture<Boolean> delete(BookingDate booking) {
        return null;
    }

    private BookingDate executeFindBy(String query){
        BookingDate bookingDate = new BookingDate();
        try (
                Statement st = this.con.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            if (rs.next()) {
                bookingDate.setIdBookingDate(rs.getLong(1));
                bookingDate.setIdService(rs.getLong(2));
                bookingDate.setDate(rs.getDate(3));
                bookingDate.setTime(rs.getTime(4));
                bookingDate.setIsValid(rs.getBoolean(5));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookingDate;
    }
}
