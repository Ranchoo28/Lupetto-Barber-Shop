package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Hairdresser;
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

    public ResponseEntity<?> insertProduct(String email, Product product) {
        try {
            Hairdresser hairdresser = DatabaseHandler.getInstance().getHairdresserDao().findByEmail(email).join();
            if (hairdresser.getId_hairdresser() != null) {
                DatabaseHandler.getInstance().openConnection();
                boolean res = DatabaseHandler.getInstance().getProductDao().insert(product).join();
                if (res) {
                    return ResponseEntity.ok("{\"message\": \"Product added\"}");
                } else {
                    return ResponseEntity.badRequest().body("{\"message\": \"Insert failed\"}");
                }
            }
            else
            {
                return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> deleteProduct(String email, Long id){
        try {
            DatabaseHandler.getInstance().openConnection();
            Hairdresser hairdresser = DatabaseHandler.getInstance().getHairdresserDao().findByEmail(email).join();
            if (hairdresser.getId_hairdresser() != null) {
                boolean res = DatabaseHandler.getInstance().getProductDao().delete(id).join();
                if (res) {
                    return ResponseEntity.ok().body("{\"message\": \"Successful delete of the product\"}");
                } else {
                    return ResponseEntity.badRequest().body("{\"message\": \"Delete failed\"}");
                }
            }else {
                return ResponseEntity.badRequest().body("{\"message\": \"You are not authorized to perform this action\"}");
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }
}
