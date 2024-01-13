package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.UserDao;
import it.unical.demacs.backend.Persistenza.Model.BookingDate;
import it.unical.demacs.backend.Persistenza.Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import it.unical.demacs.backend.Service.Response.UserBookingResponse;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.scheduling.annotation.Async;


public class UserDaoImpl implements UserDao {
    Connection con;

    public UserDaoImpl(Connection con) { this.con = con; }

    @Override
    @Async
    public CompletableFuture<ArrayList<User>> findAll() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (
                Statement st = this.con.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                User user = new User();
                user.setIdUser(rs.getLong(1));
                user.setEmail(rs.getString(1));
                user.setPassword(rs.getString(3));
                user.setName(rs.getString(4));
                user.setSurname(rs.getString(5));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(users);
    }

    @Override
    @Async
    public CompletableFuture<User> findByPrimaryKey(Long id) {
        User user = new User();
        String query = "SELECT * FROM users WHERE id_user = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    user.setIdUser(rs.getLong(1));
                    user.setEmail(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setName(rs.getString(4));
                    user.setSurname(rs.getString(5));
                    user.setNumber(rs.getString(6));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(user);
    }
    
    @Override
    @Async
    public CompletableFuture<User> findByEmail(String email) {
        User user = new User();
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
              user.setIdUser(rs.getLong(1));
              user.setEmail(rs.getString(2));
              user.setPassword(rs.getString(3));
              user.setName(rs.getString(4));
              user.setSurname(rs.getString(5));
              user.setNumber(rs.getString(6));
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(user);
    }

    public CompletableFuture<ArrayList<UserBookingResponse>> findBookings(Long id)
    {
        ArrayList<UserBookingResponse> bookings = new ArrayList<>();
        String query =
                "SELECT s.name, bd.data, bd.ora, b.id_booking " +
                "FROM bookings as b, bookingsdate as bd, services as s " +
                "WHERE b.id_user = ? and b.id_bookingdate = bd.id_bookingdate and bd.id_service = s.id_service";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                UserBookingResponse userBookingResponse = new UserBookingResponse();
                userBookingResponse.setService_name(rs.getString(1));
                userBookingResponse.setDate(rs.getDate(2).toLocalDate());
                userBookingResponse.setTime(rs.getTime(3));
                userBookingResponse.setId_booking(rs.getLong(4));
                bookings.add(userBookingResponse);
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(bookings);
    }

    @Override
    @Async
    public CompletableFuture<Boolean> insert(User user) {
        System.out.println(user.getNumber());
        String query = "INSERT INTO users (email, password, name, surname, tel_number) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setString(1, user.getEmail());
            st.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
            st.setString(3, user.getName());
            st.setString(4, user.getSurname());
            st.setString(5, user.getNumber());
            System.out.println(st);
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
    public CompletableFuture<Boolean> update(User user) {
        String query = "UPDATE users SET username=?, password=?, name=?, surname=?, email=?, role=? WHERE id_user=?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setString(1, user.getEmail());
            st.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
            st.setString(3, user.getName());
            st.setString(4, user.getSurname());
            st.setString(5, user.getNumber());
            st.setLong(6, user.getIdUser());
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
        String query = "DELETE FROM users WHERE id_user = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, id);
            st.executeUpdate();
            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException ignored) {}

        return CompletableFuture.completedFuture(false);
    }
}
