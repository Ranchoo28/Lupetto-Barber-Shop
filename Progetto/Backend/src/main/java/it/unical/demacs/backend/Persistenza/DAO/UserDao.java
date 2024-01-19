package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.Booking;
import it.unical.demacs.backend.Persistenza.Model.BookingDate;
import it.unical.demacs.backend.Persistenza.Model.User;
import it.unical.demacs.backend.Service.Response.UserBookingResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface UserDao {
    public CompletableFuture<ArrayList<User>> findAll();
    public CompletableFuture<User> findByPrimaryKey(Long id);
    public CompletableFuture<User> findByEmail(String email);
    public CompletableFuture<ArrayList<UserBookingResponse>> findBookings(Long id);
    public CompletableFuture<Boolean> insert(User user);
    public CompletableFuture<Boolean> update(User user);
    public CompletableFuture<Boolean> delete(Long id);
    public CompletableFuture<Long>getUserIdByIdPurchase(Long id_purchase);
}
