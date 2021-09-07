package com.assets_france.api.security.infrastructure;


import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.hasText;

@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";
    private final byte[] secret;
    private final long ACCESS_TOKEN_MILLISECONDS = Duration.ofMinutes(10).getSeconds() * 1000;
    private final long REFRESH_TOKEN_MILLISECONDS = Duration.ofHours(1).getSeconds() * 1000;

    public enum TypeToken {
        access,
        refresh
    }

    public TokenProvider(@Value("${security.token.secret}") CharSequence secret) {
        this.secret = secret.toString().getBytes();
    }

    public String createToken(Authentication authentication, TypeToken typeToken) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validity = new Date(now + getTokenValidityInMilliseconds(typeToken));

        log.info("authorities : {}", authorities);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(validity)
                .compact();
    }

    private long getTokenValidityInMilliseconds(TypeToken typeToken) {
        return typeToken.equals(TypeToken.access)
                ? ACCESS_TOKEN_MILLISECONDS
                : REFRESH_TOKEN_MILLISECONDS;
    }

    public boolean validateToken(String authToken) {
        try {
            parseToken(authToken);
            return true;

        } catch (JwtException | IllegalArgumentException e) {
            log.info("Invalid JWT token.");
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseToken(token).getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        User principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private Jws<Claims> parseToken(String authToken) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(authToken);
    }


    public String resolveToken(HttpServletRequest request) {
        var bearerPart = "Bearer ";
        var authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (hasText(authorization) && authorization.startsWith(bearerPart)) {
            return authorization.substring(bearerPart.length());
        }

        return null;
    }
}
