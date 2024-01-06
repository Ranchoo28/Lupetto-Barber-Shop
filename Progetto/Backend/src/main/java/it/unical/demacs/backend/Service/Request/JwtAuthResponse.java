package it.unical.demacs.backend.Service.Request;

import lombok.Getter;

@Getter
public class JwtAuthResponse {
    private final String accessToken;

    public JwtAuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }

}