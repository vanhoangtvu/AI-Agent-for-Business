package com.aiagent.service.impl;

import com.aiagent.exception.ResourceNotFoundException;
import com.aiagent.model.entity.User;
import com.aiagent.repository.UserRepository;
import com.aiagent.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User Service Implementation
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User userUpdate) {
        User user = getUserById(id);
        
        if (userUpdate.getFullName() != null) {
            user.setFullName(userUpdate.getFullName());
        }
        if (userUpdate.getPhoneNumber() != null) {
            user.setPhoneNumber(userUpdate.getPhoneNumber());
        }
        if (userUpdate.getActive() != null) {
            user.setActive(userUpdate.getActive());
        }
        
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
        log.info("Deleted user: {}", user.getEmail());
    }

    @Override
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            
            if (principal instanceof UserDetails) {
                String email = ((UserDetails) principal).getUsername();
                return getUserByEmail(email);
            }
        }
        
        throw new IllegalStateException("No authenticated user found");
    }

    @Override
    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

}

