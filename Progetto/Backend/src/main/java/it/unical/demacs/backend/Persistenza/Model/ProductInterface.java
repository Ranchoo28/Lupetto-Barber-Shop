package it.unical.demacs.backend.Persistenza.Model;

public interface ProductInterface {
    Long getIdProduct();
    String getName();
    String getDescription();
    String getCategory();
    Double getPrice();
    String getImage();
    Hairdresser getHairdresser();
}
