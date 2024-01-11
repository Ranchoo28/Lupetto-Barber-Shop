package it.unical.demacs.backend.Persistenza.Model;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import lombok.*;

import java.sql.SQLException;

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
    private String sex;
    private int duration;

    public Service(String name, String description, String category, double price, String sex, int duration) {
    	this.name = name;
    	this.description = description;
    	this.category = category;
    	this.price = price;
        this.sex=sex;
        this.duration=duration;
    }

    public Service(Long idService) throws SQLException {
        Service s=DatabaseHandler.getInstance().getServiceDao().findByPrimaryKey(idService).join();
        this.idService=s.getIdService();
        this.name=s.getName();
        this.description=s.getDescription();
        this.category=s.getCategory();
        this.price=s.getPrice();
        this.sex=s.getSex();
        this.duration=s.getDuration();
    }

}
