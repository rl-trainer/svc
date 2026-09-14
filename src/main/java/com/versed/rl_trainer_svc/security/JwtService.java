package com.versed.rl_trainer_svc.security;

import java.util.Base64;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final Logger log = LoggerFactory.getLogger(JwtService.class);

    private static final String ACCESS_TOKEN_NAME = "access_token";

    private final SecretKey key;
    private final Long expirationMs;

    public JwtService(
            @Value("${security.jwt.secret}") String secret,
            @Value("${security.jwt.expiration-ms}") Long expirationMs) {
        this.key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secret));
        this.expirationMs = expirationMs;
    }

    public String generateToken(Long userId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        // Build token
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .issuedAt(now)
                .expiration(expiration)
                .signWith(this.key)
                .compact();

    }

    public Optional<Long> extractUserId(String token) {
        try {
            var parser = Jwts.parser().verifyWith(this.key).build();
            Jws<Claims> claims = parser.parseSignedClaims(token);
            String userId = claims.getPayload().getSubject();
            return Optional.of(Long.valueOf(userId));
        } catch (JwtException | IllegalArgumentException e) {
            log.warn("Rejected invalid JWT: {}", e.getMessage());
            return Optional.empty();
        }
    }

    public Long getExpirationMs(){
        return this.expirationMs;
    }

    public String getAccessTokenName(){
        return ACCESS_TOKEN_NAME;
    }

}
