package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Service.CartService;
import it.unical.demacs.backend.Persistenza.Model.CartProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/api/user/cart/get")
    public ResponseEntity<?> getCart(@RequestParam String email){
        return cartService.getCart(email);
    }

    @PostMapping("/api/user/cart/add")
    public ResponseEntity<?> addCart(@RequestParam String email, @RequestBody ArrayList<CartProduct> products){
        return cartService.addCart(email, products);
    }
}
