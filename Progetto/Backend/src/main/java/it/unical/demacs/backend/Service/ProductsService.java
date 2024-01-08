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
        DatabaseHandler.getInstance().closeConnection();
        if(products.isEmpty()){
            return ResponseEntity.badRequest().body("{\"message\": \"No products found\"}");
        }
        else{
            return ResponseEntity.ok(products);
        }
    }
}
