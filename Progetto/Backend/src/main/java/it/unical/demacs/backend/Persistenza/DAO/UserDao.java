package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.User;

import java.util.ArrayList;

public interface UserDao {
    public ArrayList<User> findAll();
    public User findByPrimaryKey();
    public User findByUsername();
    public String findEmail();
    public void saveOrUpdate(User user);
    public void delete(Long id);
}
