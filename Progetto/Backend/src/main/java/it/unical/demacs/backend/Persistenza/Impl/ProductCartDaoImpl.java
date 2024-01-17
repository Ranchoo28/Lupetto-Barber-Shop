package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.ProductCartDao;
import it.unical.demacs.backend.Persistenza.Model.Cart;
import it.unical.demacs.backend.Persistenza.Model.Product;
import it.unical.demacs.backend.Persistenza.Model.ProductCart;
import it.unical.demacs.backend.Persistenza.Model.User;
import lombok.NonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ProductCartDaoImpl implements ProductCartDao{
    Connection con;

    public ProductCartDaoImpl(Connection connection) {
        this.con = connection;
    }

    @Override
    public CompletableFuture<ArrayList<ProductCart>> findAll() {
        String query = "SELECT * FROM products_cart";
        ArrayList<ProductCart> productsCart = new ArrayList<>();
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    productsCart.add(new ProductCart(rs.getLong(1)));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(productsCart);
    }


    @Override
    public CompletableFuture<ProductCart> findByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public CompletableFuture<Cart> findCartByIdProductCart(Long id) {
        String query = "SELECT id_cart FROM products_cart WHERE id_product_cart = ?";
        Cart cart = null;
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    cart = new Cart(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(cart);
    }

    @Override
    public CompletableFuture<ArrayList<Product>> findProductByIdCart(Long id) {
        String query = "SELECT id_product FROM products_cart WHERE id_cart = ?";
        ArrayList<Product> products = new ArrayList<>();
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    products.add(new Product(rs.getLong(1)));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(products);
    }
}
