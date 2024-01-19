package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.Booking;
import it.unical.demacs.backend.Persistenza.Model.Purchase;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface PurchaseDao {
    CompletableFuture<ArrayList<Purchase>> findAll();
    CompletableFuture<Purchase> findByPrimaryKey(Long id);
    CompletableFuture<Boolean> insert(Long id_user, LocalDate purchase_date);
    CompletableFuture<Long> findByUserId(Long id_user);
    CompletableFuture<Purchase> getPurchaseByIdProductPurchase(Long idProductPurchases);
}
