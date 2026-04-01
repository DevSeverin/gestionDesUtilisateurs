package com.authentication.connexion.controller.auth;

import com.authentication.connexion.dto.auth.AuthApiRequest;
import com.authentication.connexion.dto.auth.RefreshTokenApiResponse;
import com.authentication.connexion.exception.RefreshTokenException;
import com.authentication.connexion.repository.IUserRepository;
import com.authentication.connexion.services.UserService;
import com.authentication.connexion.services.auth.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import static com.authentication.connexion.config.SuccessLoginHandler.REFRESH_TOKEN;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService tokenService;

    @Autowired
    private IUserRepository repository;

    @Autowired
    private UserService userService;

    @PostMapping("auth/authenticate/local")
    public ResponseEntity<RefreshTokenApiResponse> authenticate(@RequestBody AuthApiRequest request) {

        log.info("=================== " + request.getEmail());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        var accessToken = tokenService.createAccessToken(user);
        var refreshToken = tokenService.createRefreshToken();

        user.setRefreshToken(refreshToken);
        repository.save(user);
        log.info("------------------ Je suis appelle AuthController ----------------------");
        return ResponseEntity.ok(new RefreshTokenApiResponse(accessToken, refreshToken));
    }

    @GetMapping("/token/refresh")
    public RefreshTokenApiResponse getRefreshToken(@RequestParam(REFRESH_TOKEN) String refreshToken) {
        var user = repository.findByRefreshToken(refreshToken).orElseThrow(() -> new RefreshTokenException("Refresh token non trouvé"));
        if (user.isRefreshTokenExpired()) {
            throw new RefreshTokenException("Le jeton d'actualisation a expiré.");
        }
        var accessToken = tokenService.createAccessToken(user);
        var newRefreshToken = tokenService.createRefreshToken();
        user.setRefreshToken(newRefreshToken);
        repository.save(user);
        log.info("------------------ Je suis appelle AuthController token refresh ----------------------");
        return new RefreshTokenApiResponse(accessToken, newRefreshToken);
    }

}
