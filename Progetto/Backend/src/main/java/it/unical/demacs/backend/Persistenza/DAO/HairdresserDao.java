package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.Hairdresser;
import it.unical.demacs.backend.Persistenza.Model.User;
import it.unical.demacs.backend.Service.Response.HaidresserBookingResponse;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface HairdresserDao {
    public CompletableFuture<ArrayList<Hairdresser>> findAll();
    public CompletableFuture<Hairdresser> findByPrimaryKey(Long id);
    public CompletableFuture<Hairdresser> findByEmail(String email);
    public CompletableFuture<ArrayList<HaidresserBookingResponse>> findAllBookings();
    public CompletableFuture<Boolean> insert(Hairdresser hairdresser);
    public CompletableFuture<Boolean> delete(Long id);
    public CompletableFuture<Boolean> update(Hairdresser hairdresser);
}
