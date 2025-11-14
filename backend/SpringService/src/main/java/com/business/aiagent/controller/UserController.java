package com.business.aiagent.controller;

import com.business.aiagent.dto.ChangePasswordRequest;
import com.business.aiagent.dto.UpdateProfileRequest;
import com.business.aiagent.dto.UserResponse;
import com.business.aiagent.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "👤 User Management", description = "API quản lý thông tin người dùng")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(Authentication authentication) {
        UserResponse profile = userService.getUserProfile(authentication.getName());
        return ResponseEntity.ok(profile);
    }
    
    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @RequestBody UpdateProfileRequest request,
            Authentication authentication) {
        
        UserResponse updated = userService.updateProfile(
                authentication.getName(),
                request.getEmail(),
                request.getFullName(),
                request.getPhoneNumber()
        );
        return ResponseEntity.ok(updated);
    }
    
    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordRequest request,
            Authentication authentication) {
        
        userService.changePassword(
                authentication.getName(),
                request.getCurrentPassword(),
                request.getNewPassword()
        );
        return ResponseEntity.ok().build();
    }
}
