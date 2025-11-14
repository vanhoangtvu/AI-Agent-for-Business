package com.business.aiagent.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    
    @NotBlank(message = "Username là bắt buộc")
    @Size(min = 3, max = 50, message = "Username phải từ 3-50 ký tự")
    private String username;
    
    @NotBlank(message = "Email là bắt buộc")
    @Email(message = "Email không hợp lệ")
    private String email;
    
    @NotBlank(message = "Password là bắt buộc")
    @Size(min = 6, message = "Password phải ít nhất 6 ký tự")
    private String password;
    
    private String fullName;
    private String phone;
}
