package it.unical.demacs.backend.Service.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponse {
    Long id_user;
    Long id_product;
    Integer quantity;

}
