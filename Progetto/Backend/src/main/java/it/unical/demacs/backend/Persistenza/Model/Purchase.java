package it.unical.demacs.backend.Persistenza.Model;

import it.unical.demacs.backend.Persistenza.DatabaseHandler;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class Purchase {
    Long id_purchase;
    User user;
    LocalDate purchase_date;

    public Purchase(Long id_purchase) {
        Purchase p = DatabaseHandler.getInstance().getPurchaseDao().findByPrimaryKey(id_purchase).join();
        this.id_purchase = p.getId_purchase();
        this.user = p.getUser();
        this.purchase_date = p.getPurchase_date();
    }
}
