package it.unical.demacs.backend.Persistenza.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProduct {
    private Long id_product;
    private String name;
    private Double price;
    private String image;
    private Integer quantity;
}
