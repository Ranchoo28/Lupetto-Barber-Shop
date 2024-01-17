package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.Product;
import it.unical.demacs.backend.Service.CartProduct;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface ProductDao {
    public CompletableFuture<ArrayList<Product>> findAll();
    public CompletableFuture<Product> findByPrimaryKey(Long id);
    public CompletableFuture<Product> findByName(String name);
    public CompletableFuture<Boolean> insert(Product product);
    public CompletableFuture<Boolean> update(Product product);
    public CompletableFuture<Boolean> delete(Long id);
    public CompletableFuture<ArrayList<CartProduct>> findAllByCartId(Long id);
}
