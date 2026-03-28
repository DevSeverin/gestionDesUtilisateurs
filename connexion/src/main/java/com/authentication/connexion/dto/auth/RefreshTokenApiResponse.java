package com.authentication.connexion.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RefreshTokenApiResponse {

    private String accessToken;
    private String refreshToken;
}