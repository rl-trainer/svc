package com.versed.rl_trainer_svc.security;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
public class AuthCookieService {

    private final JwtService jwt;
    private final boolean secureCookie;

    public AuthCookieService(JwtService jwt, @Value("${security.cookie.secure}") boolean secureCookie) {
        this.jwt = jwt;
        this.secureCookie = secureCookie;
    }

    public ResponseCookie createAccessTokenCookie(String token) {
        ResponseCookie cookie = ResponseCookie.from(jwt.getAccessTokenName(), token)
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(Duration.ofMillis(jwt.getExpirationMs()))
                .secure(secureCookie)
                .build();
        return cookie;
    }

    public ResponseCookie clearAccessTokenCookie(){
        ResponseCookie cookie = ResponseCookie.from(jwt.getAccessTokenName(), "")
                .httpOnly(true)
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .secure(secureCookie)
                .build();
        return cookie;
    }

}
