package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.ProductPurchasesDao;
import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.ProductPurchases;
import it.unical.demacs.backend.Persistenza.Model.Purchase;
import it.unical.demacs.backend.Service.Response.CartResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class ProductPurchasesDaoImpl implements ProductPurchasesDao {
    Connection con;

    @Override
    public CompletableFuture<ArrayList<ProductPurchases>> findAll() {
        ArrayList<ProductPurchases> purchases = new ArrayList<>();
        String query = "SELECT * FROM product_purchases";
        try (
                Statement st = this.con.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                ProductPurchases purchase = new ProductPurchases(rs.getLong(1));
                purchases.add(purchase);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(purchases);
    }

    @Override
    public CompletableFuture<ArrayList<ProductPurchases>> findByPurchaseId(Long purchase_id) {
        return null;
    }

    @Override
    public CompletableFuture<ProductPurchases> findByPrimaryKey(Long id_product_purchases) {
        String query = "SELECT * FROM product_purchases WHERE id_product_purchases = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id_product_purchases);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                ProductPurchases productPurchases = new ProductPurchases();
                productPurchases.setId_product_purchase(rs.getLong(1));
                productPurchases.setPurchase(DatabaseHandler.getInstance().getPurchaseDao().findByPrimaryKey(rs.getLong(2)).join());
                productPurchases.setProduct(DatabaseHandler.getInstance().getProductDao().findByPrimaryKey(rs.getLong(3)).join());
                productPurchases.setQuantity(rs.getInt(4));
                productPurchases.setIntent(rs.getString(5));
                return CompletableFuture.completedFuture(productPurchases);
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<ArrayList<ProductPurchases>> findByUserId(Long user_id) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> insert(ProductPurchases productPurchases) {
        return null;
    }
    /*
    @Override
    public CompletableFuture<Boolean> insert(ArrayList<CartResponse> cart, String intent){
        return null;
    }

     */
}
