package com.authentication.connexion.repository;

import com.authentication.connexion.exception.UserNotFoundException;
import com.authentication.connexion.model.User;
import com.authentication.connexion.services.auth.AuthService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

import java.util.Optional;
import java.util.UUID;

import static com.authentication.connexion.services.auth.AuthService.JWT_EMAIL_CLAIM_NAME;
import static com.authentication.connexion.services.auth.AuthService.JWT_PROVIDER_CLAIM_NAME;

public interface IUserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmailAndProvider(String email, String provider);

    default User findByAccessToken(OAuth2AuthenticationToken token) throws UserNotFoundException {
        var email = (String) token.getPrincipal().getAttribute(JWT_EMAIL_CLAIM_NAME);
        var provider = (String) token.getPrincipal().getAttribute(JWT_PROVIDER_CLAIM_NAME);
        return findByEmailAndProvider(email, provider).orElseThrow(() -> new UserNotFoundException(email, provider));
    }

}
