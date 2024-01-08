package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.Hairdresser;
import it.unical.demacs.backend.Persistenza.Model.User;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface HairdresserDao {
    public CompletableFuture<ArrayList<Hairdresser>> findAll();
    public CompletableFuture<Hairdresser> findByPrimaryKey(Long id);
    public CompletableFuture<Hairdresser> findByUsername(String username);
    public CompletableFuture<Boolean> addService(Long idService);
    public CompletableFuture<Boolean> removeService(Long idHairdresser, Long idService);
    public CompletableFuture<Boolean> insertUser(Long idHairdresser);
    public CompletableFuture<Boolean> modifyBooking(Long idHairdresser, Long idBooking);
    public CompletableFuture<Boolean> removeBooking(Long idHairdresser, Long idBooking);
    public CompletableFuture<Boolean> insertProducts(Long idHairdresser, Long idProduct);
    public CompletableFuture<Boolean> removeProducts(Long idHairdresser, Long idProduct);
}
