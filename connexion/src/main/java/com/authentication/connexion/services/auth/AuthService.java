package com.authentication.connexion.services.auth;

import com.authentication.connexion.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.authentication.connexion.config.SecurityConfig.ADMIN_ROLE;

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

    public String createAccessToken(User user) {
        return Jwts.builder()
                .issuer("quizzapp")
                .subject(user.getEmail())
                .expiration(Date.from(ZonedDateTime.now().toInstant().plusMillis(5 * 60 * 1000))) // 5 minutes
                .claim(JWT_PROVIDER_CLAIM_NAME, user.getProvider())
                .claim(JWT_NAME_CLAIM_NAME, user.getName())
                .claim(JWT_ADMIN_CLAIM_NAME, adminEmail.equals(user.getEmail()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public String createRefreshToken() {
        return UUID.randomUUID().toString();
    }

    // Validation et lecture du JWT
    public Jws<Claims> getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build()
                .parseSignedClaims(token);
    }

    public List<GrantedAuthority> getRoles(Jws<Claims> claims) {
        return isAdmin(claims) ? List.of(new SimpleGrantedAuthority(ADMIN_ROLE)) : new ArrayList<>();
    }

    public OAuth2User getUser(Jws<Claims> claims) {
        return new DefaultOAuth2User(getRoles(claims), claims.getPayload(), JWT_NAME_CLAIM_NAME);
    }

    public String getProvider(Jws<Claims> claims) {return (String) claims.getPayload().get(JWT_PROVIDER_CLAIM_NAME);}

    public boolean isAdmin(Jws<Claims> claims) {return (boolean) claims.getPayload().get(JWT_ADMIN_CLAIM_NAME);}

}
