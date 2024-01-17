package it.unical.demacs.backend.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartProduct {
    private Long idProduct;
    private String name;
    private Double price;
    private String image;
    private Integer quantity;
}
