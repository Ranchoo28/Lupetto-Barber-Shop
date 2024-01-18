package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Hairdresser;
import it.unical.demacs.backend.Persistenza.Model.Product;
import it.unical.demacs.backend.Persistenza.Model.ProductProxy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
            DatabaseHandler.getInstance().openConnection();
            Hairdresser hairdresser = DatabaseHandler.getInstance().getHairdresserDao().findByEmail(email).join();
            if (hairdresser.getId_hairdresser() != null) {
                product.setHairdresser(hairdresser);
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

    public ResponseEntity<?> getProductById(String name) {
        try {
            DatabaseHandler.getInstance().openConnection();
            Product product = DatabaseHandler.getInstance().getProductDao().findByName(name).join();
            if (product == null) {
                return ResponseEntity.badRequest().body("{\"message\": \"No product found\"}");
            } else {
                return ResponseEntity.ok(product);
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> getAllProductProxies() {
        try {
            DatabaseHandler.getInstance().openConnection();
            List<Product> products = DatabaseHandler.getInstance().getProductDao().findAll().join();

            if (products.isEmpty()) {
                return ResponseEntity.badRequest().body("{\"message\": \"No products found\"}");
            } else {
                List<ProductProxy> productProxies = products.stream()
                        .map(product -> new ProductProxy(product.getIdProduct()))
                        .collect(Collectors.toList());

                return ResponseEntity.ok(productProxies);
            }
        } finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

}
