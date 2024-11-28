package com.devInnovators.security.services;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;


public interface JWTService {
    
    String extractUserName(String token);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails);
}
