package com.authentication.connexion.config;

import com.authentication.connexion.services.auth.AuthService;
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
    private AuthService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        getToken(request).ifPresent(token -> SecurityContextHolder.getContext().setAuthentication(token));
        System.out.println("=== REQUEST URI: " + request.getRequestURI());
        filterChain.doFilter(request, response);
    }

    private Optional<OAuth2AuthenticationToken> getToken(HttpServletRequest req) {
        try {
            var token = req.getHeader("Authorization");
//            System.out.println("=== " + r);
            System.out.println("=== TOKEN: " + token);
            if (StringUtils.isBlank(token)) {
                System.out.println("=== RETOURNER VIDE ===");
                return Optional.empty();
            }
            var claims = tokenService.getClaims(token.replace("Bearer", "").trim());
            var user = tokenService.getUser(claims);
            System.out.println("=== USER EMAIL: " + user.getName());
            var provider = tokenService.getProvider(claims);
            System.out.println("=== PROVIDER: " + provider);
            return Optional.of(new OAuth2AuthenticationToken(user, user.getAuthorities(), provider));
        } catch (Exception e) {
            log.error("Echec pour la convertie du token", e);
            System.out.println("============== Echec pour la convertie du token ==================");
            return Optional.empty();
        }
    }

}
