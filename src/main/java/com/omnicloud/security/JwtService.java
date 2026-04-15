package com.omnicloud.security;

import com.omnicloud.Utils.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtUtil jwtUtil;

    public JwtService(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String generateToken(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        return jwtUtil.generateToken(userId.toString());
    }

    public boolean validateToken(String token) {
        if (token == null || token.isBlank()) {
            return false;
        }

        try {
            return jwtUtil.validateToken(token);
        } catch (Exception ex) {
            return false;
        }
    }

    public Long extractUserId(String token) {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token cannot be null or empty");
        }

        try {
            String userId = jwtUtil.extractUsername(token);
            return Long.parseLong(userId);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Invalid user ID in token", ex);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Invalid token", ex);
        }
    }
}