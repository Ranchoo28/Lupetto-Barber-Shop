package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.User;

import java.util.ArrayList;

public interface UserDao {
    public ArrayList<User> findAll();
    public User findByPrimaryKey(Long id);
    public User findByUsername(String username);
    public User findByEmail(String email);
    public void saveOrUpdate(User user);
    public void delete(Long id);
}
