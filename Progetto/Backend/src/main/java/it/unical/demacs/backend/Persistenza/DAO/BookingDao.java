package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.Booking;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface BookingDao {
    CompletableFuture<ArrayList<Booking>> findAll();
    CompletableFuture<Booking> findByPrimaryKey(Long id);
    CompletableFuture<Void> saveOrUpdate(Booking booking);
    CompletableFuture<Boolean> delete(Long id);

    CompletableFuture<Boolean> isValid(Long bookingId);
}
