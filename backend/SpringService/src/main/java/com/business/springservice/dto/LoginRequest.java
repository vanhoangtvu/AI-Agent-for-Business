package com.business.springservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    
    @Schema(description = "Username or Email", example = "admin")
    private String username;
    
    @Schema(description = "Password", example = "hoang123")
    private String password;
}
