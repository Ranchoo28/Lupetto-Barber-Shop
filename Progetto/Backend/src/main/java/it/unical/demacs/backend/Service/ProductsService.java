package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductsService {
    public ResponseEntity<?> getProducts() {
        try {
            DatabaseHandler.getInstance().openConnection();
            ArrayList<Product> products = DatabaseHandler.getInstance().getProductDao().findAll().join();
            if (products.isEmpty()) {
                return ResponseEntity.badRequest().body("{\"message\": \"No products found\"}");
            } else {
                return ResponseEntity.ok(products);
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }
}
