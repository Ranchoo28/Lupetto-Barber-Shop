package it.unical.demacs.backend.Persistenza.Model;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import lombok.*;

import java.awt.dnd.DropTarget;

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
    private String image;
    private Hairdresser hairdresser;

    public Product(Long idProduct){
        Product p = DatabaseHandler.getInstance().getProductDao().findByPrimaryKey(idProduct).join();
        this.idProduct = p.getIdProduct();
        this.name = p.getName();
        this.description = p.getDescription();
        this.category = p.getCategory();
        this.price = p.getPrice();
        this.image = p.getImage();
        this.hairdresser= p.getHairdresser();
    }

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
