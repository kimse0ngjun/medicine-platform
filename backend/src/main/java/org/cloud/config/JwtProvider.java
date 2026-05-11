package org.cloud.config;


import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtConfig jwtConfig;

    public String createToken(String email) {

        Date now = new Date();

        Date expireDate =
                new Date(now.getTime() + jwtConfig.getExpiration());

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expireDate)
                .signWith(
                        Keys.hmacShaKeyFor(
                                jwtConfig.getSecretKey().getBytes()
                        ),
                        SignatureAlgorithm.HS256
                )
                .compact();
    }
    
    public boolean validateToken(String token) {

        try {

            Jwts.parserBuilder()
                    .setSigningKey(
                            Keys.hmacShaKeyFor(
                                    jwtConfig.getSecretKey().getBytes()
                            )
                    )
                    .build()
                    .parseClaimsJws(token);

            return true;

        } catch (Exception e) {
            return false;
        }
    }
    
    public String getEmail(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(
                        Keys.hmacShaKeyFor(
                                jwtConfig.getSecretKey().getBytes()
                        )
                )
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}