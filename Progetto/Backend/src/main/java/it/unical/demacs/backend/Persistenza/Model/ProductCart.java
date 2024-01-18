package it.unical.demacs.backend.Persistenza.Model;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@Getter
@Setter
@ToString

public class ProductCart {
    Long id_product_cart;
    Cart cart;
    ArrayList<Product> products;

    public ProductCart(Long id_product_cart){
        this.id_product_cart = id_product_cart;
        this.cart = DatabaseHandler.getInstance().getProductCartDao().findCartByIdProductCart(id_product_cart).join();
        this.products = DatabaseHandler.getInstance().getProductCartDao().findProductByIdCart(cart.getId_cart()).join();
    }
}
