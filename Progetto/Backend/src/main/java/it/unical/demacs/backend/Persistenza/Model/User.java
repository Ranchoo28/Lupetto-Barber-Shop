package it.unical.demacs.backend.Persistenza.Model;

import lombok.*;

@Getter
@Setter
@ToString

public class User {
    private Long idUtente;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private char role;
    public User() {}

    public User(String username, String password, String name, String surname, String email, char role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.role = role;
    }
}
