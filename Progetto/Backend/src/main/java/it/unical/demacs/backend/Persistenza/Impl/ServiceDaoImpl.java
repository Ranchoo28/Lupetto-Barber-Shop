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
                service.setSex(rs.getString(6));
                service.setDuration(rs.getInt(7));
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
        Service services = new Service();
        String query = "SELECT * FROM services WHERE id_service = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    services.setIdService(rs.getLong(1));
                    services.setName(rs.getString(2));
                    services.setDescription(rs.getString(3));
                    services.setCategory(rs.getString(4));
                    services.setPrice(rs.getDouble(5));
                    services.setSex(rs.getString(6));
                    services.setDuration(rs.getInt(7));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(services);
    }

    @Override
    @Async
    public CompletableFuture<Service> findByName(String name) {
        Service services = new Service();
        String query = "SELECT * FROM services WHERE name = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setString(1, name);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    services.setIdService(rs.getLong(1));
                    services.setName(rs.getString(2));
                    services.setDescription(rs.getString(3));
                    services.setCategory(rs.getString(4));
                    services.setPrice(rs.getDouble(5));
                    services.setSex(rs.getString(6));
                    services.setDuration(rs.getInt(7));
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(services);
    }

    @Override
    @Async
    public CompletableFuture<ArrayList<Service>> findBySex(String sex) {
        ArrayList<Service> services = new ArrayList<>();
        String query = "SELECT * FROM services WHERE sex = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setString(1, sex);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Service service = new Service();
                    service.setIdService(rs.getLong(1));
                    service.setName(rs.getString(2));
                    service.setDescription(rs.getString(3));
                    service.setCategory(rs.getString(4));
                    service.setPrice(rs.getDouble(5));
                    service.setSex(rs.getString(6));
                    service.setDuration(rs.getInt(7));
                    services.add(service);
                }
            }
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(services);
    }

    @Override
    @Async
    public CompletableFuture<Boolean> insert(Service services) {
        String query = "INSERT INTO services (name, description, category, price, sex, duration) VALUES (?, ?, ?, ?, ?, ?)";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setString(1, services.getName());
            st.setString(2, services.getDescription());
            st.setString(3, services.getCategory());
            st.setDouble(4, services.getPrice());
            st.setString(5, services.getSex());
            st.setInt(6, services.getDuration());
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
    public CompletableFuture<Boolean> update(Service services) {
        String query = "UPDATE services SET name=?, description=?, category=?, price=?, sex=?, duration=? WHERE id_service=?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setString(1, services.getName());
            st.setString(2, services.getDescription());
            st.setString(3, services.getCategory());
            st.setDouble(4, services.getPrice());
            st.setString(5, services.getSex());
            st.setInt(6, services.getDuration());
            st.setLong(7, services.getIdService());

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
    public CompletableFuture<Boolean> delete(Long id) {
        String query = "DELETE FROM services WHERE id_service = ?";
        try (
                PreparedStatement st = this.con.prepareStatement(query)) {
            st.setLong(1, id);
            int rowsAffected = st.executeUpdate();
            st.close();

            return CompletableFuture.completedFuture(rowsAffected > 0);
        } catch (SQLException e) {
            e.fillInStackTrace();
        }
        return CompletableFuture.completedFuture(false);
    }
}
