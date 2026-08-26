package com.fitnessapp.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")  //vrednost iz application.properties
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Generiše tajni ključ iz stringa
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
        // prevodi tvoj tajni string u ključ koji 
        // razume HMAC mašina
    }

    // Generiše JWT token za korisnika
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //HEADER.PAYLOAD.SIGNATURE - token

    // Izvlači email iz tokena
    public String extractEmail(String token) {
        return Jwts.parserBuilder()  //parsira
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Proverava da li je token validan
    public boolean isTokenValid(String token) {
        try {
            extractEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    
}

