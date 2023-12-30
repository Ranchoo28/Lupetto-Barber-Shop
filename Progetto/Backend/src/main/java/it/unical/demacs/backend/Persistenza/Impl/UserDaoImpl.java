package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.UserDao;
import it.unical.demacs.backend.Persistenza.Model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;



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
                user.setIdUtente(rs.getLong(1));
                user.setUsername(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setName(rs.getString(4));
                user.setSurname(rs.getString(5));
                user.setEmail(rs.getString(6));
                user.setRole(rs.getString(7).charAt(0));
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
        String query = "SELECT * FROM users WHERE idutente = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    user.setIdUtente(rs.getLong(1));
                    user.setUsername(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setName(rs.getString(4));
                    user.setSurname(rs.getString(5));
                    user.setEmail(rs.getString(6));
                    user.setRole(rs.getString(7).charAt(0));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(user);
    }

    @Override
    @Async
    public CompletableFuture<User> findByUsername(String username) {
        User user = new User();
        String query = "SELECT username,password FROM users WHERE username = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user.setUsername(rs.getString(1));
                user.setPassword(rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(user);
    }

    @Override
    @Async
    public CompletableFuture<User> findByEmail(String email) {
        User user = new User();
        String query = "SELECT email FROM users WHERE email = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                user.setEmail(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(user);
    }

    @Override
    @Async
    public CompletableFuture<Void> saveOrUpdate(User user) {
        String query = "INSERT INTO users (username, password, name, surname, email, ruolo) VALUES (?, ?, ?, ?, ?,?)";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setString(1, user.getUsername());
            st.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
            st.setString(3, user.getName());
            st.setString(4, user.getSurname());
            st.setString(5, user.getEmail());
            st.setString(6, String.valueOf(user.getRole()));
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public CompletableFuture<Void> delete(Long id) {
        String query = "DELETE FROM users WHERE idutente = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, id);
            st.executeUpdate();
            st.close();
        } catch (SQLException ignored) {}

        return CompletableFuture.completedFuture(null);
    }
}
