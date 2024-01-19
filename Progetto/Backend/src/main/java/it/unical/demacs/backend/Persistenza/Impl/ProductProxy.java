package it.unical.demacs.backend.Persistenza.Impl;

import it.unical.demacs.backend.Persistenza.Model.Hairdresser;
import it.unical.demacs.backend.Persistenza.Model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductProxy extends Product {
    Connection con;

    public ProductProxy(Connection con){
        this.con = con;
    }

    @Override
    public String getDescription(){
        if(super.getDescription() == null){
            String query = "SELECT description FROM products WHERE id_product = ?";
            try (
                    PreparedStatement st = this.con.prepareStatement(query)) {
                st.setLong(1, getIdProduct());
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                       super.setDescription(rs.getString(1));
                       return rs.getString(1);
                    }
                }
            } catch (SQLException e) {
                e.fillInStackTrace();
            }
        }
        return super.getDescription();
    }
}
