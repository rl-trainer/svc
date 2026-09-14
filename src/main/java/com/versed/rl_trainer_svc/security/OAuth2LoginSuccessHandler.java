package com.versed.rl_trainer_svc.security;

import java.io.IOException;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.versed.rl_trainer_svc.model.User;
import com.versed.rl_trainer_svc.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler{
    
    private final JwtService jwt;
    private final boolean secureCookie;
    private final String frontEndURL;
    private final UserService userService;

    public OAuth2LoginSuccessHandler(JwtService jwt,@Value("${security.cookie.secure}") boolean secureCookie,
     @Value("${app.frontend-url}") String frontEndURL, UserService userService){
        this.jwt = jwt;
        this.secureCookie = secureCookie;
        this.frontEndURL = frontEndURL;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
         Authentication authentication) throws IOException, ServletException{
            OidcUser oidcUser  = (OidcUser) authentication.getPrincipal();
            User appUser = userService.findOrCreateFromGoogle(oidcUser);
            String jwtToken = jwt.generateToken(appUser.getId());
            ResponseCookie cookie = ResponseCookie.from(jwt.getAccessTokenName(), jwtToken)
                                    .httpOnly(true)
                                    .sameSite("Lax")
                                    .path("/")
                                    .maxAge(Duration.ofMillis(jwt.getExpirationMs()))
                                    .secure(secureCookie)
                                    .build();
            
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            response.sendRedirect(this.frontEndURL);
    }

}
