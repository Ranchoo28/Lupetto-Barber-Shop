package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductsService {
    public ResponseEntity<?> getProducts() {
        ArrayList<Product> products = DatabaseHandler.getInstance().getProductDao().findAll().join();
        if(products.isEmpty()){
            DatabaseHandler.getInstance().closeConnection();
            return ResponseEntity.badRequest().body("No products found");
        }
        else{
            DatabaseHandler.getInstance().closeConnection();
            return ResponseEntity.ok(products);
        }
    }
}
