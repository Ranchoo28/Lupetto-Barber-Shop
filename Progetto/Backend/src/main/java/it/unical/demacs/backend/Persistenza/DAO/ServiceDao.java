package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface ServiceDao {
    public CompletableFuture<ArrayList<Service>> findAll();
    public CompletableFuture<Service> findByPrimaryKey(Long id) throws SQLException;
    public CompletableFuture<Service> findByName(String name);
    public CompletableFuture<Boolean> insert(Service services);
    public CompletableFuture<Boolean> update(Service services);
    public CompletableFuture<Boolean> delete(Long id);
}
