package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.DAO.ServiceDao;
import it.unical.demacs.backend.Persistenza.Model.Service;
import org.springframework.scheduling.annotation.Async;

import java.sql.*;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class ServiceDaoImpl implements ServiceDao {
    Connection con;
    public ServiceDaoImpl(Connection con) { this.con = con; }

    @Override
    @Async
    public CompletableFuture<ArrayList<Service>> findAll() {
        ArrayList<Service> services = new ArrayList<>();
        String query = "SELECT * FROM services";
        try (
                Statement st = this.con.createStatement();
                ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Service service = new Service();
                service.setIdService(rs.getLong(1));
                service.setName(rs.getString(2));
                service.setDescription(rs.getString(3));
                service.setCategory(rs.getString(4));
                service.setPrice(rs.getDouble(5));
                services.add(service);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return CompletableFuture.completedFuture(services);
    }

    @Override
    @Async
    public CompletableFuture<Service> findByPrimaryKey(Long id) throws SQLException {
        Service service = new Service();
        String query = "SELECT * FROM services WHERE id_service = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    service.setIdService(rs.getLong(1));
                    service.setName(rs.getString(2));
                    service.setDescription(rs.getString(3));
                    service.setCategory(rs.getString(4));
                    service.setPrice(rs.getDouble(5));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(service);
    }

    @Override
    @Async
    public CompletableFuture<Service> findByName(String name) {
        Service service = new Service();
        String query = "SELECT * FROM services WHERE name = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setString(1, name);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    service.setIdService(rs.getLong(1));
                    service.setName(rs.getString(2));
                    service.setDescription(rs.getString(3));
                    service.setCategory(rs.getString(4));
                    service.setPrice(rs.getDouble(5));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(service);
    }

    @Override
    @Async
    public CompletableFuture<Void> saveOrUpdate(Service service) {
        String query = "INSERT INTO services (name, description, category, price) VALUES (?, ?, ?, ?)";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setString(1, service.getName());
            st.setString(2, service.getDescription());
            st.setString(3, service.getCategory());
            st.setDouble(4, service.getPrice());
            st.executeUpdate();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }

    @Override
    @Async
    public CompletableFuture<Void> delete(Long id) {
        String query = "DELETE FROM services WHERE id_service = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(null);
    }
}
