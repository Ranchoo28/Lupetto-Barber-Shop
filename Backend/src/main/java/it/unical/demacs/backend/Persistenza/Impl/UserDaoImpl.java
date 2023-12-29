package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.UserDao;
import it.unical.demacs.backend.Persistenza.Model.User;
import java.sql.*;
import java.util.ArrayList;
import org.springframework.security.crypto.bcrypt.BCrypt;


public class UserDaoImpl implements UserDao {
    Connection con;

    public UserDaoImpl(Connection con) { this.con = con; }

    @Override
    public ArrayList<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
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
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findByPrimaryKey() {
        User user = new User();
        String query = "SELECT * FROM users WHERE idutente = ?";
        try{
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, "username");
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                user.setIdUtente(rs.getLong("idutente"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role").charAt(0));
                user.setName(rs.getString("nome"));
                user.setSurname(rs.getString("cognome"));
            }
            rs.close();
            st.close();
            con.close();
        }catch (SQLException ignored){}
        return user;
    }

    @Override
    public User findByUsername() {
        User user = new User();
        String query = "SELECT * FROM users WHERE username = ?";
        try{
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, "username");
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                user.setIdUtente(rs.getLong("idutente"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role").charAt(0));
                user.setName(rs.getString("nome"));
                user.setSurname(rs.getString("cognome"));
            }
        }catch (SQLException ignored){}
        return user;
    }



    @Override
    public void saveOrUpdate(User user) {
        System.out.println(findByUsername().getUsername());
        if (findByUsername().getUsername() == null && findEmail() == null) {
            String query = "INSERT INTO users (username, password, name, surname, email, ruolo) VALUES (?, ?, ?, ?, ?,?)";
            try {
                PreparedStatement st = con.prepareStatement(query);
                st.setString(1, user.getUsername());
                st.setString(2, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(12)));
                st.setString(3, user.getName());
                st.setString(4, user.getSurname());
                st.setString(5, user.getEmail());
                st.setString(6, String.valueOf(user.getRole()));
                st.executeUpdate();
                st.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String findEmail() {
        String email = null;
        String query = "SELECT email FROM users WHERE email = ?";
        try{
            PreparedStatement st = con.prepareStatement(query);
            st.setString(1, "email");
            ResultSet rs = st.executeQuery();
            if(rs.next()) email = rs.getString("email");

        }catch (SQLException ignored){}
        return email;
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM users WHERE idutente = ?";
        try{
            PreparedStatement st = con.prepareStatement(query);
            st.setLong(1, id);
            st.executeUpdate();
            st.close();
            con.close();
        }catch (SQLException ignored){}

    }

}
