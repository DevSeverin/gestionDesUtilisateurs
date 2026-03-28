package com.authentication.connexion.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {

    public static final String ADMIN_ROLE = "ADMIN";

    @Autowired
    private SuccessLoginHandler successLoginHandler;

    @Autowired
    private AuthFilterConfig authFilterConfig;

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests
                        .requestMatchers(HttpMethod.GET, "/token/refresh", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated())
                .oauth2Login(oauth2Login -> oauth2Login.successHandler(successLoginHandler))
                .addFilterBefore(authFilterConfig, BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable) // As we use Token Authentication
                .build();
    }

}
