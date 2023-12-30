package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface UserDao {
    public CompletableFuture<ArrayList<User>> findAll();
    public CompletableFuture<User> findByPrimaryKey(Long id) throws SQLException;
    public CompletableFuture<User> findByUsername(String username);
    public CompletableFuture<User> findByEmail(String email);
    public CompletableFuture<Void> saveOrUpdate(User user);
    public CompletableFuture<Void> delete(Long id);
}
