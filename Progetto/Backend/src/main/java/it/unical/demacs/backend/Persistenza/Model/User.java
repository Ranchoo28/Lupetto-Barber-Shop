package it.unical.demacs.backend.Persistenza.Model;

import lombok.*;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode

public class User {
    private Long idUser;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private char role;
    private ArrayList<Booking> prenotazioni;
}
