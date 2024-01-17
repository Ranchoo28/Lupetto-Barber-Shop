package it.unical.demacs.backend.Persistenza.Model;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class Cart {
    Long id_cart;
    User user;

    public Cart(Long id_cart){
        this.id_cart = id_cart;
        this.user = DatabaseHandler.getInstance().getCartDao().findUserByCartId(id_cart).join();
    }
}
