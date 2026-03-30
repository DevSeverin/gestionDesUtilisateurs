package com.authentication.connexion.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthApiRequest {

    private String email;
    private String password;

}
