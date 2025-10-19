package com.aiagent.business.service;

import com.aiagent.business.model.Business;
import com.aiagent.business.model.Customer;
import com.aiagent.business.model.Product;
import com.aiagent.business.model.Order;
import com.aiagent.business.model.Conversation;
import com.aiagent.business.repository.BusinessRepository;
import com.aiagent.business.repository.CustomerRepository;
import com.aiagent.business.repository.ProductRepository;
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
public class BusinessService {

    private final BusinessRepository businessRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ConversationRepository conversationRepository;

    // Business Profile
    public Optional<Business> getBusinessById(Long businessId) {
        return businessRepository.findById(businessId);
    }

    public Business updateBusiness(Business business) {
        business.setUpdatedAt(LocalDateTime.now());
        return businessRepository.save(business);
    }

    // Business Stats
    public Map<String, Object> getBusinessStats(Long businessId) {
        Map<String, Object> stats = new HashMap<>();
        
        long totalCustomers = customerRepository.countByBusinessId(businessId);
        long totalProducts = productRepository.countByBusinessId(businessId);
        long totalOrders = orderRepository.countByBusinessId(businessId);
        long totalConversations = conversationRepository.countByBusinessId(businessId);
        
        stats.put("totalCustomers", totalCustomers);
        stats.put("totalProducts", totalProducts);
        stats.put("totalOrders", totalOrders);
        stats.put("totalConversations", totalConversations);
        
        return stats;
    }

    // Customer Management
    public Page<Customer> getCustomers(Long businessId, int page, int size, String search, String segment) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        if (search != null && !search.isEmpty()) {
            return customerRepository.findByBusinessIdAndFullNameContainingOrEmailContainingOrPhoneContaining(
                businessId, search, search, search, pageable);
        }
        
        if (segment != null && !segment.isEmpty()) {
            return customerRepository.findByBusinessIdAndSegment(businessId, segment, pageable);
        }
        
        return customerRepository.findByBusinessId(businessId, pageable);
    }

    public Optional<Customer> getCustomerById(Long businessId, Long customerId) {
        return customerRepository.findByIdAndBusinessId(customerId, businessId);
    }

    public Customer createCustomer(Customer customer, Long businessId) {
        customer.setBusinessId(businessId);
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(Customer customer) {
        customer.setUpdatedAt(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    // Product Management
    public Page<Product> getProducts(Long businessId, int page, int size, String search, String category) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        if (search != null && !search.isEmpty()) {
            return productRepository.findByBusinessIdAndProductNameContainingOrSkuContaining(
                businessId, search, search, pageable);
        }
        
        if (category != null && !category.isEmpty()) {
            try {
                Long categoryId = Long.parseLong(category);
                return productRepository.findByBusinessIdAndCategoryId(businessId, categoryId, pageable);
            } catch (NumberFormatException e) {
                // If category is not a number, return all products
            }
        }
        
        return productRepository.findByBusinessId(businessId, pageable);
    }

    public Optional<Product> getProductById(Long businessId, Long productId) {
        return productRepository.findByIdAndBusinessId(productId, businessId);
    }

    public Product createProduct(Product product, Long businessId) {
        product.setBusinessId(businessId);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    // Order Management
    public Page<Order> getOrders(Long businessId, int page, int size, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        
        if (status != null && !status.isEmpty()) {
            return orderRepository.findByBusinessIdAndStatus(businessId, status, pageable);
        }
        
        return orderRepository.findByBusinessId(businessId, pageable);
    }

    public Optional<Order> getOrderById(Long businessId, Long orderId) {
        return orderRepository.findByIdAndBusinessId(orderId, businessId);
    }

    public Order createOrder(Order order, Long businessId) {
        order.setBusinessId(businessId);
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Order updateOrder(Order order) {
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    // Conversation Management
    public Page<Conversation> getConversations(Long businessId, int page, int size, String status) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        
        if (status != null && !status.isEmpty()) {
            Conversation.Status statusEnum = Conversation.Status.valueOf(status.toUpperCase());
            return conversationRepository.findByBusinessIdAndStatus(businessId, statusEnum, pageable);
        }
        
        return conversationRepository.findByBusinessId(businessId, pageable);
    }

    public Optional<Conversation> getConversationById(Long businessId, Long conversationId) {
        return conversationRepository.findByIdAndBusinessId(conversationId, businessId);
    }
}

