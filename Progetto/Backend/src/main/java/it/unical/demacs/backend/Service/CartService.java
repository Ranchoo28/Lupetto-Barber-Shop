package it.unical.demacs.backend.Service;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import it.unical.demacs.backend.Persistenza.Model.Cart;
import it.unical.demacs.backend.Persistenza.Model.CartProduct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {
    public ResponseEntity<?> getCart(String email) {
        try {
            DatabaseHandler.getInstance().openConnection();
            Long id_user = DatabaseHandler.getInstance().getUserDao().findByEmail(email).join().getIdUser();
            if(DatabaseHandler.getInstance().getCartDao().findCartByUserId(id_user).join().getId_cart() == null){
                return ResponseEntity.badRequest().body("{\"message\": \"No cart found\"}");
            }else{
                Cart cart = DatabaseHandler.getInstance().getCartDao().findCartByUserId(id_user).join();
                ArrayList<CartProduct> products = DatabaseHandler.getInstance().getProductDao().findAllByCartId(cart.getId_cart()).join();
                return ResponseEntity.ok().body(products);
            }
        }
        finally {
            DatabaseHandler.getInstance().closeConnection();
        }
    }

    public ResponseEntity<?> addCart(String email, ArrayList<CartProduct> products){
        try {
            DatabaseHandler.getInstance().openConnection();
            Long id_user = DatabaseHandler.getInstance().getUserDao().findByEmail(email).join().getIdUser();
            if (DatabaseHandler.getInstance().getCartDao().findCartByUserId(id_user).join().getId_cart() == null) {
                return ResponseEntity.badRequest().body("{\"message\": \"No user found\"}");
            } else {
                if (DatabaseHandler.getInstance().getCartDao().addCart(id_user).join()) {
                    Cart cart = DatabaseHandler.getInstance().getCartDao().findCartByUserId(id_user).join();
                    DatabaseHandler.getInstance().getCartDao().addProductToCart(cart.getId_cart(), products);
                    return ResponseEntity.ok().body("{\"message\": \"Cart created and products added\"}");
                }
                else{
                    return ResponseEntity.badRequest().body("{\"message\": \"Cart creation failed\"}");
                }
            }
        }
        finally{
                DatabaseHandler.getInstance().closeConnection();
        }
    }

}
