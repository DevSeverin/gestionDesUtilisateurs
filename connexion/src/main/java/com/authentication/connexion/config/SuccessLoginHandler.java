package com.authentication.connexion.config;

import com.quizz.app.model.User;
import com.quizz.app.repository.IUserRepository;
import com.quizz.app.services.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Optional;

public class SuccessLoginHandler extends SimpleUrlAuthenticationSuccessHandler {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String GITHUB_PROVIDER = "github";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private IUserRepository userRepository;

    @Value("${app.oauth2.redirectUri}")
    private String redirectUri;

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication instanceof OAuth2AuthenticationToken token) {
            var user = toUserEntity(token);
            userRepository.save(user);
            getRedirectStrategy().sendRedirect(request, response, getTargetUrl(user));
        }
    }

    private String getTargetUrl(User user) {
        return UriComponentsBuilder.fromUriString(redirectUri)
                .queryParam(ACCESS_TOKEN, tokenService.createAccessToken(user))
                .queryParam(REFRESH_TOKEN, user.getRefreshToken())
                .build().toString();
    }

    private User toUserEntity(OAuth2AuthenticationToken token) {
        var email = (String) Optional.ofNullable(token.getPrincipal().getAttribute("email")).orElse("NON TROUVEE");
        var provider = token.getAuthorizedClientRegistrationId();
        var res = userRepository.findByEmailAndProvider(email, provider).orElse(new User());
        res.setEmail(email);
        res.setProvider(provider);
        res.setName((String) Optional.ofNullable(token.getPrincipal().getAttribute("name")).orElse("NON TROUVE"));
        res.setRefreshToken(tokenService.createRefreshToken());
        return res;
    }

}
