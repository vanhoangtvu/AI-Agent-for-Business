package com.aiagent.business.dto.request;

import com.aiagent.business.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
    
    @NotBlank(message = "Email là bắt buộc")
    @Email(message = "Email không hợp lệ")
    private String email;
    
    @NotBlank(message = "Mật khẩu là bắt buộc")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;
    
    @NotBlank(message = "Họ tên là bắt buộc")
    private String fullName;
    
    private String phone;
    
    @NotNull(message = "Vai trò là bắt buộc")
    private User.UserRole role;
    
    // For BUSINESS role
    private String businessName;
}

