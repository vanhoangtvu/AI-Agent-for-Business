package com.aiagent.business.dto.response;

import com.aiagent.business.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String email;
    private String fullName;
    private User.UserRole role;
    private Long businessId;
    
    public AuthResponse(String token, Long id, String email, String fullName, User.UserRole role, Long businessId) {
        this.token = token;
        this.id = id;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.businessId = businessId;
    }
}

