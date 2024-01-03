package it.unical.demacs.backend.Persistenza.Model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

public class Product {
    private Long idProduct;
    private String name;
    private String description;
    private String category;
    private Double price;
}
