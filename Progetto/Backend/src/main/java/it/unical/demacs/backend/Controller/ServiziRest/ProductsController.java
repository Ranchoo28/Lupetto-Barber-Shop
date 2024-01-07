package it.unical.demacs.backend.Controller.ServiziRest;

import it.unical.demacs.backend.Service.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor

public class ProductsController {

    private final ProductsService productsService;

    @GetMapping("/api/products")
    public ResponseEntity<?> getProducts() { return productsService.getProducts(); }
}
