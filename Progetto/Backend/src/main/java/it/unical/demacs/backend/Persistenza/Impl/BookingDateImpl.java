package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.BookingDateDao;
import it.unical.demacs.backend.Persistenza.Model.BookingDate;
import it.unical.demacs.backend.Persistenza.Model.Service;
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
        String query = "SELECT * FROM bookingsdate WHERE isvalid= true";
        ArrayList<BookingDate> bookingList = new ArrayList<>();
        try (
                Statement st = this.con.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                BookingDate bookingDate = new BookingDate();
                bookingDate.setIdBookingDate(rs.getLong(1));
                bookingDate.setService(new Service(rs.getLong(2)));
                bookingDate.setDate(rs.getDate(3).toLocalDate());
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
        BookingDate bookingDate = new BookingDate();
        String query = "SELECT * FROM bookingsdate WHERE id_bookingdate = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    bookingDate.setIdBookingDate(rs.getLong(1));
                    bookingDate.setService(new Service(rs.getLong(2)));
                    bookingDate.setDate(rs.getDate(3).toLocalDate());
                    bookingDate.setTime(rs.getTime(4));
                    bookingDate.setIsValid(rs.getBoolean(5));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(bookingDate);
    }

    @Override
    @Async
    public CompletableFuture<ArrayList<BookingDate>> findByService(Long id) {
        ArrayList<BookingDate> bookingList = new ArrayList<>();
        String query = "SELECT * FROM bookingsdate WHERE id_service = ?  and isvalid = true";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    BookingDate bookingDate = new BookingDate();
                    bookingDate.setIdBookingDate(rs.getLong(1));
                    bookingDate.setService(new Service(rs.getLong(2)));
                    bookingDate.setDate(rs.getDate(3).toLocalDate());
                    bookingDate.setTime(rs.getTime(4));
                    bookingDate.setIsValid(rs.getBoolean(5));
                    bookingList.add(bookingDate);
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(bookingList);
    }

    @Override
    @Async
    public CompletableFuture<ArrayList<BookingDate>> findByDate(Date date, Long idService)   {
        ArrayList<BookingDate> bookingList = new ArrayList<>();
        String query = "SELECT * FROM bookingsdate WHERE data = ? and id_service=?  and isvalid = true";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setDate(1, date);
            st.setLong(2, idService);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    BookingDate bookingDate = new BookingDate();
                    bookingDate.setIdBookingDate(rs.getLong(1));
                    bookingDate.setService(new Service(rs.getLong(2)));
                    bookingDate.setDate(rs.getDate(3).toLocalDate());
                    bookingDate.setTime(rs.getTime(4));
                    bookingDate.setIsValid(rs.getBoolean(5));
                    bookingList.add(bookingDate);
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(bookingList);
    }

    @Override
    @Async
    public CompletableFuture<Boolean> insert(BookingDate bookingdate) {
        String query = "INSERT INTO bookingsdate (id_service, data, ora, isvalid) VALUES (?, ?, ?,?)";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, bookingdate.getService().getIdService());
            st.setDate(2, Date.valueOf(bookingdate.getDate()));
            st.setTime(3, bookingdate.getTime());
            st.setBoolean(4,true);
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
    public CompletableFuture<Boolean> delete(Long id) {
        String query = "DELETE FROM bookingsdate WHERE id_bookingDate = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
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
    public CompletableFuture<Boolean> update(BookingDate booking) {
        String query = "UPDATE bookingsdate SET id_service=?, data=?, ora=?, isvalid=? WHERE id_bookingdate=?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, booking.getService().getIdService());
            st.setDate(2, Date.valueOf(booking.getDate()));
            st.setTime(3, booking.getTime());
            st.setBoolean(4, booking.getIsValid());
            st.setLong(5, booking.getIdBookingDate());

            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    public CompletableFuture<Boolean> updateIsValid(Long idBookingDate, boolean b) {
        String query = "UPDATE bookingsdate SET isvalid=? WHERE id_bookingdate=?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setBoolean(1, b);
            st.setLong(2, idBookingDate);

            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    public CompletableFuture<Boolean> deleteSovrapposition(BookingDate booking) {
        String query = "DELETE FROM bookingsdate WHERE data = ? and ora >=? and ora<? and isvalid = true";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setDate(1, Date.valueOf(booking.getDate()));
            st.setTime(2, booking.getTime());
            st.setTime(3, new Time(booking.getTime().getTime() + (booking.getService().getDuration() * 60000L)));
            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(false);
    }

}
