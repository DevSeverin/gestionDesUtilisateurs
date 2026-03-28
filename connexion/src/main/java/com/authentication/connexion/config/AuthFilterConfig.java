package com.authentication.connexion.config;

import com.quizz.app.services.TokenService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class AuthFilterConfig extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        getToken(request).ifPresent(token -> SecurityContextHolder.getContext().setAuthentication(token));
    }

    private Optional<OAuth2AuthenticationToken> getToken(HttpServletRequest req) {
        try {
            var token = req.getHeader("Authorization");
            if (StringUtils.isBlank(token)) {
                return Optional.empty();
            }
            var claims = tokenService.getClaims(token.replace("Bearer", ""));
            var user = tokenService.getUser(claims);
            var provider = tokenService.getProvider(claims);
            return Optional.of(new OAuth2AuthenticationToken(user, user.getAuthorities(), provider));
        } catch (Exception e) {
            log.error("Echec pour la convertie du token", e);
            return Optional.empty();
        }
    }

}
