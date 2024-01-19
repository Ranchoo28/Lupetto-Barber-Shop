package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Persistenza.Model.Product;
import it.unical.demacs.backend.Service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@RequiredArgsConstructor

public class ProductsController {

    private final ProductsService productsService;

    @GetMapping("/api/productsByName")
    public ResponseEntity<?> getProducts(@RequestParam String name) { return productsService.getProductById(name); }

    @GetMapping("/api/products")
    public ResponseEntity<?> getProductsMinimal() { return productsService.getProducts(); }


    @PostMapping("/api/hairdresser/addProduct")
    public ResponseEntity<?> insertProduct(@RequestParam String email, @RequestBody Product product) {
        return productsService.insertProduct(email, product);
    }

    @PostMapping("/api/hairdresser/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam String email, @RequestParam Long id_product) {
        return productsService.deleteProduct(email, id_product);
    }

    @GetMapping("/api/hairdresser/productProxy")
    public ResponseEntity<?> getProductProxy(@RequestParam Long id_product) {
       return productsService.getProductProxy(id_product);
    }
}
