package com.aiagent.business.service;

import com.aiagent.business.dto.request.LoginRequest;
import com.aiagent.business.dto.request.RegisterRequest;
import com.aiagent.business.dto.response.AuthResponse;
import com.aiagent.business.model.Business;
import com.aiagent.business.model.User;
import com.aiagent.business.repository.BusinessRepository;
import com.aiagent.business.repository.UserRepository;
import com.aiagent.business.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Check if email exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email đã tồn tại");
        }

        // Create user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());
        user.setIsActive(true);
        user.setIsVerified(false);

        // If BUSINESS role, create business
        Long businessId = null;
        if (request.getRole() == User.UserRole.BUSINESS && request.getBusinessName() != null) {
            Business business = new Business();
            business.setBusinessName(request.getBusinessName());
            business.setSlug(generateSlug(request.getBusinessName()));
            business.setStatus(Business.BusinessStatus.ACTIVE);
            business.setType(Business.BusinessType.OTHER);
            business = businessRepository.save(business);
            
            businessId = business.getId();
            user.setBusinessId(businessId);
            
            // Update business owner
            business.setOwnerId(user.getId());
            businessRepository.save(business);
        }

        user = userRepository.save(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getId(),
                user.getRole().name(),
                user.getBusinessId()
        );

        return new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole(),
                businessId
        );
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email hoặc mật khẩu không đúng"));

        if (!user.getIsActive()) {
            throw new RuntimeException("Tài khoản đã bị khóa");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Email hoặc mật khẩu không đúng");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getId(),
                user.getRole().name(),
                user.getBusinessId()
        );

        return new AuthResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getRole(),
                user.getBusinessId()
        );
    }

    private String generateSlug(String businessName) {
        return businessName.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                + "-" + System.currentTimeMillis();
    }
}

