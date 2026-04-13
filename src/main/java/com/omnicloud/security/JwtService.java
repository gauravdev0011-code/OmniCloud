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
        return jwtUtil.generateToken(userId.toString());
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public Long extractUserId(String token) {
        return Long.valueOf(jwtUtil.extractUsername(token));
    }
}