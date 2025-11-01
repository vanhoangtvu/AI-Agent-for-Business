package com.aiagent.service;

import com.aiagent.model.entity.User;

import java.util.List;

/**
 * User Service Interface
 */
public interface UserService {
    
    User getUserById(Long id);
    
    User getUserByEmail(String email);
    
    List<User> getAllUsers();
    
    User updateUser(Long id, User user);
    
    void deleteUser(Long id);
    
    User getCurrentUser();
    
    List<User> getUsersByRole(String role);
    
}

