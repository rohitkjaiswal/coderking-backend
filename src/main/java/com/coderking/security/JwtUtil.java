package com.coderking.security;

import lombok.Getter;
import org.springframework.stereotype.Component;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;

@Component @Getter
public class JwtUtil {
    private final String secret = "01234567890123456789012345678901";
    private final long validityMs = 24 * 60 * 60 * 1000;

    public String generateToken(String username ,Long userId) {
        SecretKeySpec key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        return Jwts.builder()
                .setSubject(username)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + validityMs))
                .signWith(key, SignatureAlgorithm.HS256)   // use key, not secret.getBytes()
                .compact();
    }
}
