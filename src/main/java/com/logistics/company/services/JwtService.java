package com.logistics.company.services;

import com.logistics.company.dtos.TokenDataDTO;
import com.logistics.company.models.enums.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class.getName());

    private final String secret = System.getenv("JWT_SECRET");
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

    public String generateToken(int id, String firstName, String lastName, String email, UserRole role) {
        logger.info("Generating JWT token for email: {} and role: {}", email, role);

        Date now = new Date();
        long tokenValidityMs = 1000 * 60 * 60;
        Date validity = new Date(now.getTime() + tokenValidityMs);

        Map<String, String> claims = new HashMap<>();

        claims.put("id", String.valueOf(id));
        claims.put("firstName", firstName);
        claims.put("lastName", lastName);
        claims.put("role", role.toString());

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(email)
            .setIssuedAt(now)
            .setExpiration(validity)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isUserAuthorized(String token, List<UserRole> requiredRoles) {
        if(!isTokenValid(token)) {
            return false;
        }
        TokenDataDTO tokenData = getDataFromToken(token);

        for(UserRole requiredRole : requiredRoles) {
            if(requiredRole.equals(tokenData.getRole())) {
                return true;
            }
        }

        return false;
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error(e.getMessage());
            return false;
        }
    }

    public TokenDataDTO getDataFromToken(String token) {
        Jws<Claims> claimsJws = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token);

        Claims claims = claimsJws.getBody();

        Long id = Long.parseLong(claims.get("id", String.class));

        return new TokenDataDTO(
            id,
            claims.get("firstName", String.class),
            claims.get("lastName", String.class),
            claims.getSubject(),
            claims.get("role", UserRole.class)
        );
    }
}
