package com.example.internship.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.example.internship.model.User;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "mySuperSecretKeyThatIsAtLeastThirtyTwoCharactersLong";

    private final long JWT_EXPIRATION = 1000 * 60 * 60 * 24;

    public String generateToken(
            String username,
            String role,
            Long userId,
            Long companyId,
            Long studentId) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION));

        if (studentId != null) {
            builder.claim("studentId", studentId);
        }

        if (companyId != null) {
            builder.claim("companyId", companyId);
        }

        return builder
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateToken(String username, String role, Long userId) {
        return generateToken(username, role, userId, null, null);
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean validateToken(String token, User user) {

        final String username = extractUsername(token);

        if (!username.equals(user.getUsername()) || isTokenExpired(token)) {
            return false;
        }

        Date issuedAt = extractIssuedAt(token);

        if (user.getChangedPasswordAt() != null) {
            if (issuedAt.before(java.sql.Timestamp.valueOf(user.getChangedPasswordAt()))) {
                return false;
            }
        }

        return true;
    }

    public Date extractIssuedAt(String token) {
        return extractAllClaims(token).getIssuedAt();
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
        return Keys.hmacShaKeyFor(keyBytes);
    }
}