package com.authentication.connexion.services.auth;

import com.authentication.connexion.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public static final String JWT_PROVIDER_CLAIM_NAME = "provider";
    public static final String JWT_ADMIN_CLAIM_NAME = "admin";
    public static final String JWT_NAME_CLAIM_NAME = "name";
    public static final String JWT_EMAIL_CLAIM_NAME = "sub";

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.admin.email}")
    private String adminEmail;

    // Manamboatra token
    public String createAccessToken(User user) {
//        return Jwts.bui
    }

}
