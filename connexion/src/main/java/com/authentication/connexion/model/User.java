package com.authentication.connexion.model;

import com.authentication.connexion.dto.auth.AuthApiResponse;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    public static final Integer JWT_REFRESH_TOKEN_DURATION_IN_MILLIS = 24 * 60 * 60 * 1000; // 24 hours

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;

    private String provider;

    private String name;

    private String password;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "REFRESH_TOKEN_CREATED_AT")
    private ZonedDateTime refreshTokenCreatedAt;

    public AuthApiResponse toApiResponse() {
        var res = new AuthApiResponse();
        res.setId(getId());
        res.setName(getName());
        res.setProvider(getProvider());
        res.setEmail(getEmail());
        return res;
    }

    // Maka le valeur le token miovaova sy ny date ny mise a jour.
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
        this.refreshTokenCreatedAt = ZonedDateTime.now();
    }

    public boolean isRefreshTokenExpired() {
        return refreshTokenCreatedAt.toInstant().plusMillis(JWT_REFRESH_TOKEN_DURATION_IN_MILLIS).isBefore(ZonedDateTime.now().toInstant());
    }

}
