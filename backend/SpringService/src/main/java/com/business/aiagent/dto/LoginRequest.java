package com.business.aiagent.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    
    @NotBlank(message = "Username là bắt buộc")
    private String username;
    
    @NotBlank(message = "Password là bắt buộc")
    private String password;
}
