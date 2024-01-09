package it.unical.demacs.backend.Service.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AuthenticationRequest {
    private String email;
    private String password;
}
