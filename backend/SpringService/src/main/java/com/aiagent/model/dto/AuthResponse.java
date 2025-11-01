package com.aiagent.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Authentication Response DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String token;
    
    private String refreshToken;
    
    private String type = "Bearer";
    
    private Long userId;
    
    private String username;
    
    private String email;
    
    private Set<String> roles;

}

