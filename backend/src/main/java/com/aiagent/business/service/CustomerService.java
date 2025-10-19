package com.aiagent.business.service;

import com.aiagent.business.model.Customer;
import com.aiagent.business.model.Order;
import com.aiagent.business.model.Conversation;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ConversationRepository conversationRepository;

    // Customer Profile
    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.findById(customerId);
    }

    public Customer updateCustomer(Customer customer) {
        customer.setUpdatedAt(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    // Orders
    public Page<Order> getCustomerOrders(Long customerId, int page, int size, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        if (status != null && !status.isEmpty()) {
            return orderRepository.findByCustomerIdAndStatus(customerId, status, pageable);
        }
        
        return orderRepository.findByCustomerId(customerId, pageable);
    }

    public Optional<Order> getOrderById(Long customerId, Long orderId) {
        return orderRepository.findByIdAndCustomerId(orderId, customerId);
    }

    // Conversations
    public Page<Conversation> getCustomerConversations(Long customerId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        return conversationRepository.findByCustomerId(customerId, pageable);
    }

    public Optional<Conversation> getConversationById(Long customerId, Long conversationId) {
        return conversationRepository.findByIdAndCustomerId(conversationId, customerId);
    }
}

