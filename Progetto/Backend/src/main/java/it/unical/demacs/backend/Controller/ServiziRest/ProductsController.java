package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Persistenza.Model.Product;
import it.unical.demacs.backend.Service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor

public class ProductsController {

    private final ProductsService productsService;

    @GetMapping("/api/products")
    public ResponseEntity<?> getProducts() { return productsService.getProducts(); }

    @PostMapping("/api/hairdresser/addProduct")
    public ResponseEntity<?> insertProduct(@RequestParam String email, @RequestBody Product product) {
        return productsService.insertProduct(email, product);
    }

    @PostMapping("/api/hairdresser/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam String email, @RequestParam Long id_product) {
        return productsService.deleteProduct(email, id_product);
    }
}
