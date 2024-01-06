package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.Model.Booking;
import it.unical.demacs.backend.Persistenza.Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserProxy extends User {
    Connection con;

    public UserProxy(Connection con) {
        this.con = con;
    }

    /*public ArrayList<Booking> getPrenotazioni() {
        if (super.getBookings() == null) {
           String query = "SELECT * FROM bookings as b, users as u WHERE u.id_user = ? and b.id_user = u.id_user";
              try (
                     PreparedStatement st = this.con.prepareStatement(query)) {
                st.setLong(1, super.getIdUser());
                try (ResultSet rs = st.executeQuery()) {
                     ArrayList<Booking> bookings = new ArrayList<>();
                     while (rs.next()) {
                          Booking booking = new Booking();
                          booking.setIdBooking(rs.getLong(1));
                          booking.setIdUser(rs.getLong(2));
                          booking.setIdService(rs.getLong(3));
                          booking.setDate(rs.getDate(4));
                          booking.setTime(rs.getTime(5));
                          bookings.add(booking);
                     }
                     super.setBookings(bookings);
                     return bookings;
                }
              } catch (SQLException e) {
                throw new RuntimeException(e);
              }
        }
        return super.getBookings();
    }*/
}
