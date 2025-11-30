package com.business.springservice.service;

import com.business.springservice.dto.LoginRequest;
import com.business.springservice.dto.LoginResponse;
import com.business.springservice.dto.RegisterRequest;
import com.business.springservice.entity.User;
import com.business.springservice.enums.Role;
import com.business.springservice.repository.UserRepository;
import com.business.springservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    public LoginResponse register(RegisterRequest request) {
        // Check if username already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // Create new user with CUSTOMER role by default
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);

        user = userRepository.save(user);

        // Generate token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole().name());

        return new LoginResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }

    public LoginResponse login(LoginRequest request) {
        // Find user by username or email
        User user = userRepository.findByUsername(request.getUsername())
                .or(() -> userRepository.findByEmail(request.getUsername()))
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        // Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        // Generate token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole().name());

        return new LoginResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
