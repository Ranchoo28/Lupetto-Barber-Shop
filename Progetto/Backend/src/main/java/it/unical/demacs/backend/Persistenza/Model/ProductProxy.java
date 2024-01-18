package it.unical.demacs.backend.Persistenza.Model;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import lombok.*;

@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class ProductProxy implements ProductInterface {
    private Long idProduct;
    private Product realProduct;

    public ProductProxy(Long idProduct) {
        this.idProduct = idProduct;
    }

    @Override
    public Long getIdProduct() {
        return idProduct;
    }

    public String getName() {
        checkAndLoad();
        return realProduct.getName();
    }

    public String getDescription() {
        checkAndLoad();
        return realProduct.getDescription();
    }

    public String getCategory() {
        checkAndLoad();
        return realProduct.getCategory();
    }

    public Double getPrice() {
        checkAndLoad();
        return realProduct.getPrice();
    }

    public String getImage() {
        checkAndLoad();
        return realProduct.getImage();
    }

    public Hairdresser getHairdresser() {
        checkAndLoad();
        return realProduct.getHairdresser();
    }

    private void checkAndLoad() {
        if (realProduct == null) {
            realProduct = DatabaseHandler.getInstance().getProductDao().findByPrimaryKey(idProduct).join();
        }
    }
}

