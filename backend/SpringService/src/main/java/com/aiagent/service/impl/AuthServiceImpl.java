package com.aiagent.service.impl;

import com.aiagent.model.dto.AuthRequest;
import com.aiagent.model.dto.AuthResponse;
import com.aiagent.model.dto.RegisterRequest;
import com.aiagent.model.entity.User;
import com.aiagent.repository.UserRepository;
import com.aiagent.security.JwtService;
import com.aiagent.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Authentication Service Implementation
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        log.info("Registering new user: {}", request.getEmail());

        // Check if user already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Determine user role
        Set<String> roles = new HashSet<>();
        String requestedRole = request.getRole();
        
        if (requestedRole != null && !requestedRole.isEmpty()) {
            // Validate role
            if (requestedRole.equals("ADMIN") || requestedRole.equals("BUSINESS") || requestedRole.equals("CUSTOMER")) {
                roles.add(requestedRole);
            } else {
                roles.add("CUSTOMER"); // Default role
            }
        } else {
            roles.add("CUSTOMER"); // Default role if not specified
        }

        // Create new user
        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .roles(roles)
                .active(true)
                .emailVerified(false)
                .build();

        user = userRepository.save(user);

        // Generate tokens
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        log.info("User registered successfully: {}", user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    @Override
    @Transactional
    public AuthResponse login(AuthRequest request) {
        log.info("User login attempt: {}", request.getEmail());

        // Authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update last login
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);

        // Generate tokens
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        log.info("User logged in successfully: {}", user.getEmail());

        return AuthResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        log.info("Refreshing token");

        String userEmail = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

        if (jwtService.isTokenValid(refreshToken, userDetails)) {
            String newToken = jwtService.generateToken(userDetails);
            
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            return AuthResponse.builder()
                    .token(newToken)
                    .refreshToken(refreshToken)
                    .userId(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .roles(user.getRoles())
                    .build();
        }

        throw new RuntimeException("Invalid refresh token");
    }

    @Override
    public void logout(String token) {
        log.info("User logout");
        // In a production system, you might want to blacklist the token
        // For now, we'll just log the logout
    }

}

