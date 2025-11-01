package com.aiagent.service;

import com.aiagent.model.dto.AuthRequest;
import com.aiagent.model.dto.AuthResponse;
import com.aiagent.model.dto.RegisterRequest;

/**
 * Authentication Service Interface
 */
public interface AuthService {
    
    AuthResponse register(RegisterRequest request);
    
    AuthResponse login(AuthRequest request);
    
    AuthResponse refreshToken(String refreshToken);
    
    void logout(String token);
    
}

