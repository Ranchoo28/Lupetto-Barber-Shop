package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.Hairdresser;
import it.unical.demacs.backend.Service.Response.HairdresserBookingResponse;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface HairdresserDao {
    public CompletableFuture<ArrayList<Hairdresser>> findAll();
    public CompletableFuture<Hairdresser> findByPrimaryKey(Long id);
    public CompletableFuture<Hairdresser> findByEmail(String email);
    public CompletableFuture<ArrayList<HairdresserBookingResponse>> findAllBookings();
    public CompletableFuture<Boolean> insert(Hairdresser hairdresser);
    public CompletableFuture<Boolean> delete(Long id);
    public CompletableFuture<Boolean> update(Hairdresser hairdresser);
}
