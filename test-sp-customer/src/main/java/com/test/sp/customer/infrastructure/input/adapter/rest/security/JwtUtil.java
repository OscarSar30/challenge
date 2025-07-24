package com.test.sp.customer.infrastructure.input.adapter.rest.security;

import com.test.sp.customer.infrastructure.input.adapter.rest.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@AllArgsConstructor
public class JwtUtil {

    private final JwtProperties jwtProperties;

    public Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getDecoder().decode(jwtProperties.getSecret()))
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
