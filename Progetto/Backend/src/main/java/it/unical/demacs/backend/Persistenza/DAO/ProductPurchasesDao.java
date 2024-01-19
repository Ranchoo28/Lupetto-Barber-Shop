package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.ProductPurchases;
import it.unical.demacs.backend.Persistenza.Model.Purchase;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface ProductPurchasesDao {
    CompletableFuture<ArrayList<ProductPurchases>> findAll();
    CompletableFuture<ArrayList<ProductPurchases>> findByPurchaseId(Long purchase_id);
    CompletableFuture<ProductPurchases> findByPrimaryKey(Long id);
    CompletableFuture<ArrayList<ProductPurchases>> findByUserId(Long user_id);
    CompletableFuture<Boolean> insert(ProductPurchases productPurchases);
}
