package com.authentication.connexion.controller.auth;

import com.authentication.connexion.dto.auth.RefreshTokenApiResponse;
import com.authentication.connexion.exception.RefreshTokenException;
import com.authentication.connexion.repository.IUserRepository;
import com.authentication.connexion.services.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.authentication.connexion.config.SuccessLoginHandler.REFRESH_TOKEN;

@RestController
public class AuthController {

    @Autowired
    private AuthService tokenService;

    @Autowired
    private IUserRepository repository;

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
        return new RefreshTokenApiResponse(accessToken, newRefreshToken);
    }

}
