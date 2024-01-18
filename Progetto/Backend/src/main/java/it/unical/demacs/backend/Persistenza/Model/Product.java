package it.unical.demacs.backend.Persistenza.Model;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

public class Product implements ProductInterface {
    private Long idProduct;
    private String name;
    private String description;
    private String category;
    private Double price;
    private String image;
    private Hairdresser hairdresser;

    public Product(String name, String description, String category, Double price, String image, Hairdresser hairdresser) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.image = image;
        this.hairdresser=hairdresser;
    }

    public Product(String name, String description, String category, Double price, String image) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.image = image;
        this.hairdresser=null;
    }

}
