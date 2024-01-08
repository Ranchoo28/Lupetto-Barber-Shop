package it.unical.demacs.backend.Persistenza.Model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

public class Service {
    private Long idService;
    private String name;
    private String description;
    private String category;
    private double price;

    public Service(String name, String description, String category, double price) {
    	this.name = name;
    	this.description = description;
    	this.category = category;
    	this.price = price;
    }
}
