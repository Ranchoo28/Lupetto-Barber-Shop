package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.Product;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface ProductDao {
    public CompletableFuture<ArrayList<Product>> findAll();
    public CompletableFuture<Product> findByPrimaryKey(Long id) throws SQLException;
    public CompletableFuture<Product> findByName(String name);
    public CompletableFuture<Void> saveOrUpdate(Product product);
    public CompletableFuture<Void> delete(Long id);
}
