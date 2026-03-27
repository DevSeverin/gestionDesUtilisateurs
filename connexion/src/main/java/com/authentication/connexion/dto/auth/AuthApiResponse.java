package com.authentication.connexion.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthApiResponse {

    private UUID id;
    private String name;
    private String email;
    private String provider;
}
