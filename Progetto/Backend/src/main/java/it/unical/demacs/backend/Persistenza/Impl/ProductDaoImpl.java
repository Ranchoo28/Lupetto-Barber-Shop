package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.ProductDao;
import it.unical.demacs.backend.Persistenza.Model.Hairdresser;
import it.unical.demacs.backend.Persistenza.Model.Product;
import it.unical.demacs.backend.Persistenza.Model.CartProduct;
import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ProductDaoImpl implements ProductDao {
    Connection con;

    public ProductDaoImpl(Connection con) { this.con = con; }

    @Override
    public CompletableFuture<ArrayList<Product>> findAll() {
        ArrayList<Product> productsList = new ArrayList<>();
        String query = "SELECT * FROM products";
        try (
                Statement st = this.con.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Product product = new ProductProxy(con);
                product.setIdProduct(rs.getLong(1));
                product.setName(rs.getString(2));
                product.setCategory(rs.getString(4));
                product.setPrice(rs.getDouble(5));
                product.setImage(rs.getString(6));
                product.setHairdresser(new Hairdresser(rs.getLong(7)));
                productsList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return CompletableFuture.completedFuture(productsList);
    }

    @Override
    public CompletableFuture<Product> findByPrimaryKey(Long id) {
        Product product=new ProductProxy(con);
        String query = "SELECT * FROM products WHERE id_product = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    product.setIdProduct(rs.getLong(1));
                    product.setName(rs.getString(2));
                    product.setCategory(rs.getString(4));
                    product.setPrice(rs.getDouble(5));
                    product.setImage(rs.getString(6));
                    product.setHairdresser(new Hairdresser(rs.getLong(7)));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(product);
    }

    @Override
    public CompletableFuture<Product> findByName(String name) {
        Product product=new ProductProxy(con);
        String query = "SELECT * FROM products WHERE name = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setString(1, name);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    product.setIdProduct(rs.getLong(1));
                    product.setName(rs.getString(2));
                    product.setCategory(rs.getString(4));
                    product.setPrice(rs.getDouble(5));
                    product.setImage(rs.getString(6));
                    product.setHairdresser(new Hairdresser(rs.getLong(7)));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(product);
    }

    @Override
    public CompletableFuture<Boolean> insert(Product product) {
        String query = "INSERT INTO products (name, description, category, price, image, id_hairdresser) VALUES ( ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setString(1, product.getName());
            st.setString(2, product.getDescription());
            st.setString(3, product.getCategory());
            st.setDouble(4, product.getPrice());
            st.setString(5, product.getImage());
            st.setLong(6, product.getHairdresser().getId_hairdresser());
            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    public CompletableFuture<Boolean> update(Product product) {
        String query = "UPDATE products SET name=?, description=?, category=?, price=?, image=?, id_hairdresser=? WHERE id_product=?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setString(1, product.getName());
            st.setString(2, product.getDescription());
            st.setString(3, product.getCategory());
            st.setDouble(4, product.getPrice());
            st.setString(5, product.getImage());
            st.setLong(6, product.getHairdresser().getId_hairdresser());
            st.setLong(7, product.getIdProduct());
            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(false);
    }

    @Override
    public CompletableFuture<Boolean> delete(Long id) {
        String query = "DELETE FROM products WHERE id_product = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, id);
            st.executeUpdate();
            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException ignored) {}

        return CompletableFuture.completedFuture(false);
    }

    @Override
    public CompletableFuture<ArrayList<CartProduct>> findAllByCartId(Long id) {
        String query = "SELECT id_product, quantity FROM products_cart WHERE id_cart = ?";
        ArrayList<CartProduct> products = new ArrayList<>();
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Product product = new Product(rs.getLong(1));
                    CartProduct response = new CartProduct();
                    response.setIdProduct(product.getIdProduct());
                    response.setName(product.getName());
                    response.setPrice(product.getPrice());
                    response.setImage(product.getImage());
                    response.setQuantity(rs.getInt(2));
                    products.add(response);
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(products);
    }
}
