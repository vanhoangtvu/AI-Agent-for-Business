package com.business.aiagent.service;

import com.business.aiagent.dto.DashboardStatistics;
import com.business.aiagent.entity.Order;
import com.business.aiagent.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public DashboardStatistics getStatistics() {
        // Count total products
        Long totalProducts = productRepository.count();
        
        // Count total orders
        Long totalOrders = orderRepository.count();
        
        // Count total documents
        Long totalDocuments = documentRepository.count();
        
        // Count total users
        Long totalUsers = userRepository.count();
        
        // Count total reviews
        Long totalReviews = reviewRepository.count();
        
        // Count pending orders
        Long pendingOrders = orderRepository.countByStatus(Order.OrderStatus.PENDING);
        
        // Count delivered orders (completed orders)
        Long completedOrders = orderRepository.countByStatus(Order.OrderStatus.DELIVERED);
        
        // Calculate total revenue from DELIVERED orders (đơn đã giao thành công)
        BigDecimal totalRevenue = orderRepository.findByStatus(Order.OrderStatus.DELIVERED)
                .stream()
                .map(Order::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return DashboardStatistics.builder()
                .totalProducts(totalProducts)
                .totalOrders(totalOrders)
                .totalDocuments(totalDocuments)
                .totalRevenue(totalRevenue)
                .totalUsers(totalUsers)
                .totalReviews(totalReviews)
                .pendingOrders(pendingOrders)
                .completedOrders(completedOrders)
                .build();
    }
}
