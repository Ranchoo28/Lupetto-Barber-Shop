package it.unical.demacs.backend.Persistenza.Impl;


import it.unical.demacs.backend.Persistenza.DAO.ProductDao;
import it.unical.demacs.backend.Persistenza.Model.Product;

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
                Product product=new Product();
                product.setIdProduct(rs.getLong(1));
                product.setName(rs.getString(2));
                product.setDescription(rs.getString(3));
                product.setCategory(rs.getString(4));
                product.setPrice(rs.getDouble(5));
                productsList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return CompletableFuture.completedFuture(productsList);
    }

    @Override
    public CompletableFuture<Product> findByPrimaryKey(Long id) throws SQLException {
        Product product=new Product();
        String query = "SELECT * FROM products WHERE id_product = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    product.setIdProduct(rs.getLong(1));
                    product.setName(rs.getString(2));
                    product.setDescription(rs.getString(3));
                    product.setCategory(rs.getString(4));
                    product.setPrice(rs.getDouble(5));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(product);
    }

    @Override
    public CompletableFuture<Product> findByName(String name) {
        Product product=new Product();
        String query = "SELECT * FROM products WHERE name = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setString(1, name);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    product.setIdProduct(rs.getLong(1));
                    product.setName(rs.getString(2));
                    product.setDescription(rs.getString(3));
                    product.setCategory(rs.getString(4));
                    product.setPrice(rs.getDouble(5));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(product);
    }

    @Override
    public CompletableFuture<Void> saveOrUpdate(Product product) {
        String query = "INSERT INTO products (name, description, category, price) VALUES ( ?, ?, ?, ?)";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setString(1, product.getName());
            st.setString(2, product.getDescription());
            st.setString(3, product.getCategory());
            st.setDouble(4, product.getPrice());
            st.executeUpdate();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Void> delete(Long id) {
        String query = "DELETE FROM products WHERE id_product = ?";
        try {
            PreparedStatement st = this.con.prepareStatement(query);
            st.setLong(1, id);
            st.executeUpdate();
            st.close();
        } catch (SQLException ignored) {}

        return CompletableFuture.completedFuture(null);
    }
}
