package it.unical.demacs.backend.Persistenza.Model;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor

public class ProductPurchases {
    Long id_product_purchase;
    Purchase purchase;
    Product product;
    Integer quantity;
    String intent;

    public ProductPurchases(Long id_product_purchase) {
        ProductPurchases p = DatabaseHandler.getInstance().getProductPurchases().findByPrimaryKey(id_product_purchase).join();
        this.id_product_purchase = p.getId_product_purchase();
        this.purchase = p.getPurchase();
        this.product = p.getProduct();
        this.quantity = p.getQuantity();
        this.intent = p.getIntent();
    }
}
