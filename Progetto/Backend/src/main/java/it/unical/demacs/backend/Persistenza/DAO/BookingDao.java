package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.Booking;
import it.unical.demacs.backend.Service.Response.HairdresserBookingResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface BookingDao {
    CompletableFuture<ArrayList<Booking>> findAll();
    CompletableFuture<Booking> findByPrimaryKey(Long id);
    CompletableFuture<Boolean> insert(Booking booking);
    CompletableFuture<Boolean> update(Booking booking);
    CompletableFuture<Boolean> delete(Long id);

    CompletableFuture<Boolean> isValid(Long id, Long idUser);

    CompletableFuture<ArrayList<HairdresserBookingResponse>> findByDate(LocalDate date);

}
