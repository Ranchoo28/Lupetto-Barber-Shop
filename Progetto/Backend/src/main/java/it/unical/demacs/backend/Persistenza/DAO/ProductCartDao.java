package it.unical.demacs.backend.Persistenza.DAO;

import it.unical.demacs.backend.Persistenza.Model.Cart;
import it.unical.demacs.backend.Persistenza.Model.Product;
import it.unical.demacs.backend.Persistenza.Model.ProductCart;
import it.unical.demacs.backend.Persistenza.Model.ProductProxy;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public interface ProductCartDao {
    public CompletableFuture<ArrayList<ProductCart>> findAll();
    public CompletableFuture<ProductCart> findByPrimaryKey(Long id);
    public CompletableFuture<Cart> findCartByIdProductCart(Long idProductCart);
    public CompletableFuture<ArrayList<ProductProxy>> findProductByIdCart(Long id);
}
