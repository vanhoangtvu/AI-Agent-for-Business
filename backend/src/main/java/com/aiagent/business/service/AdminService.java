package com.aiagent.business.service;

import com.aiagent.business.model.Business;
import com.aiagent.business.model.User;
import com.aiagent.business.repository.BusinessRepository;
import com.aiagent.business.repository.UserRepository;
import com.aiagent.business.repository.CustomerRepository;
import com.aiagent.business.repository.OrderRepository;
import com.aiagent.business.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AdminService {

    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ConversationRepository conversationRepository;

    // Business Management
    public Page<Business> getAllBusinesses(int page, int size, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        if (status != null && !status.isEmpty()) {
            Business.BusinessStatus statusEnum = Business.BusinessStatus.valueOf(status.toUpperCase());
            return businessRepository.findByStatus(statusEnum, pageable);
        }
        
        return businessRepository.findAll(pageable);
    }

    public Optional<Business> getBusinessById(Long businessId) {
        return businessRepository.findById(businessId);
    }

    public Business updateBusiness(Business business) {
        business.setUpdatedAt(LocalDateTime.now());
        return businessRepository.save(business);
    }

    public void deleteBusiness(Long businessId) {
        businessRepository.deleteById(businessId);
    }

    public Business suspendBusiness(Long businessId) {
        Business business = businessRepository.findById(businessId)
            .orElseThrow(() -> new RuntimeException("Business not found"));
        business.setStatus(Business.BusinessStatus.SUSPENDED);
        business.setUpdatedAt(LocalDateTime.now());
        return businessRepository.save(business);
    }

    public Business activateBusiness(Long businessId) {
        Business business = businessRepository.findById(businessId)
            .orElseThrow(() -> new RuntimeException("Business not found"));
        business.setStatus(Business.BusinessStatus.ACTIVE);
        business.setUpdatedAt(LocalDateTime.now());
        return businessRepository.save(business);
    }

    // User Management
    public Page<User> getAllUsers(int page, int size, String role) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        if (role != null && !role.isEmpty()) {
            return userRepository.findByRole(role, pageable);
        }
        
        return userRepository.findAll(pageable);
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User updateUser(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    // System Stats
    public Map<String, Object> getSystemStats() {
        Map<String, Object> stats = new HashMap<>();
        
        long totalBusinesses = businessRepository.count();
        long totalUsers = userRepository.count();
        long totalCustomers = customerRepository.count();
        long totalOrders = orderRepository.count();
        long totalConversations = conversationRepository.count();
        
        stats.put("totalBusinesses", totalBusinesses);
        stats.put("totalUsers", totalUsers);
        stats.put("totalCustomers", totalCustomers);
        stats.put("totalOrders", totalOrders);
        stats.put("totalConversations", totalConversations);
        
        return stats;
    }
}

