package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.Cart;
import it.unical.demacs.backend.Persistenza.Model.User;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface CartDao {
    public CompletableFuture<ArrayList<Cart>> findAll();
    public CompletableFuture<Cart> findByPrimaryKey(Long idCart);
    public CompletableFuture<User> findUserByCartId(Long idCart);
    public CompletableFuture<Cart> findCartByUserId(Long idUser);

    public CompletableFuture<Boolean> addCart(long idUser);
    public void addProductToCart(Long idCart, Long id_product, Integer quantity);
}
