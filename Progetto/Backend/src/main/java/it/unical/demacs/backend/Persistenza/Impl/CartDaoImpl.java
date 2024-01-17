package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.CartDao;
import it.unical.demacs.backend.Persistenza.Model.Cart;
import it.unical.demacs.backend.Persistenza.Model.User;
import it.unical.demacs.backend.Service.CartProduct;
import org.springframework.scheduling.annotation.Async;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class CartDaoImpl implements CartDao {

    Connection con;

    public CartDaoImpl(Connection connection) { this.con = connection; }

    @Override
    @Async
    public CompletableFuture<ArrayList<Cart>> findAll() {
       String query = "SELECT * FROM carts";
       ArrayList<Cart> carts = new ArrayList<>();
         try (
                PreparedStatement st = this.con.prepareStatement(query)) {
              try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                     Cart cart = new Cart();
                     cart.setId_cart(rs.getLong(1));
                     cart.setUser(new User(rs.getLong(2)));
                     carts.add(cart);
                }
              }
         } catch (SQLException e) {
              e.fillInStackTrace();
         }
            return CompletableFuture.completedFuture(carts);
    }

    @Override
    @Async
    public CompletableFuture<Cart> findByPrimaryKey(Long id) {
        String query = "SELECT * FROM carts WHERE id_cart = ?";
        Cart cart = new Cart();
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    cart.setId_cart(rs.getLong(1));
                    cart.setUser(new User(rs.getLong(2)));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(cart);
    }

    @Override
    @Async
    public CompletableFuture<User> findUserByCartId(Long id) {
        User user = null;
        String query = "SELECT * FROM carts WHERE id_cart = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                   user = new User(rs.getLong(2));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(user);
    }

    @Override
    @Async
    public CompletableFuture<Cart> findCartByUserId(Long id) {
        String query = "SELECT id_cart FROM carts WHERE id_user = ?";
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
    @Async
    public CompletableFuture<Boolean> addCart(long idUser) {
        String query = "INSERT INTO carts (id_user) VALUES (?)";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, idUser);
            int rowsAffected = st.executeUpdate();
            st.close();
            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    @Async
    public void addProductToCart(Long idCart, Long id_product, Integer quantity) {
        String query = "INSERT INTO products_cart (id_cart,id_product,quantity) VALUES (?,?,?)";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, idCart);
            st.setLong(2, id_product);
            st.setInt(3, quantity);
            st.close();

        } catch (SQLException e) {
            e.fillInStackTrace();
        }
    }
}
