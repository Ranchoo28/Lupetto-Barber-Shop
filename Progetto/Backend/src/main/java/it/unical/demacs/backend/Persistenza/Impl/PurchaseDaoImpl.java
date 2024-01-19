package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.PurchaseDao;
import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Purchase;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public class PurchaseDaoImpl implements PurchaseDao {
    Connection con;

    @Override
    @Async
    public CompletableFuture<ArrayList<Purchase>> findAll() {
        ArrayList<Purchase> purchases = new ArrayList<>();
        String query = "SELECT * FROM purchases";
        try (
                Statement st = this.con.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Purchase purchase = new Purchase(rs.getLong(1));
                purchases.add(purchase);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(purchases);
    }

    @Override
    @Async
    public CompletableFuture<Purchase> findByPrimaryKey(Long id_purchase) {
       String query = "SELECT * FROM purchases WHERE id_purchase = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id_purchase);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                Purchase purchase = new Purchase();
                purchase.setId_purchase(rs.getLong(1));
                purchase.setUser(DatabaseHandler.getInstance().getUserDao().findByPrimaryKey(
                        DatabaseHandler.getInstance().getUserDao().getUserIdByIdPurchase(id_purchase).join()).join());
                purchase.setPurchase_date(rs.getDate(3).toLocalDate());
                return CompletableFuture.completedFuture(purchase);
            }

        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public CompletableFuture<Boolean> insert(Long id_user, LocalDate purchase_date) {
        String query = "INSERT INTO purchases (id_user,purchase_date) VALUES (?,?)";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id_user);
            st.setDate(2, Date.valueOf(purchase_date));
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
    public CompletableFuture<Long> findByUserId(Long id_user) {
        String query = "SELECT id_purchase FROM purchases WHERE id_user = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id_user);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return CompletableFuture.completedFuture(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletableFuture<Purchase> getPurchaseByIdProductPurchase(Long idProductPurchases) {
        return null;
    }
}
