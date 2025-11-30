package com.business.springservice.controller;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeRequest {
    
    @Schema(description = "Current password", example = "hoang123")
    private String oldPassword;
    
    @Schema(description = "New password", example = "newpassword123")
    private String newPassword;
}
