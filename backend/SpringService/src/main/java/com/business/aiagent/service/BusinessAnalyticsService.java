package com.business.aiagent.service;

import com.business.aiagent.dto.BusinessAnalyticsResponse;
import com.business.aiagent.entity.Order;
import com.business.aiagent.entity.User;
import com.business.aiagent.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service phân tích dữ liệu cho Business Owner
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BusinessAnalyticsService {
    
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    
    /**
     * Lấy tổng quan analytics cho business owner
     */
    public BusinessAnalyticsResponse getBusinessAnalytics(User businessOwner) {
        return BusinessAnalyticsResponse.builder()
            .overview(getOverviewStats(businessOwner))
            .revenue(getRevenueAnalytics(businessOwner))
            .products(getProductAnalytics(businessOwner))
            .orders(getOrderAnalytics(businessOwner))
            .customers(getCustomerAnalytics(businessOwner))
            .build();
    }
    
    /**
     * Tổng quan chung
     */
    private BusinessAnalyticsResponse.OverviewStats getOverviewStats(User businessOwner) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        
        // Đếm sản phẩm
        long totalProducts = productRepository.count();
        long activeProducts = productRepository.findByIsActiveTrue(null).getTotalElements();
        int lowStockProducts = productRepository.findLowStockProducts(10).size();
        
        // Đếm đơn hàng
        long totalOrders = orderRepository.count();
        long pendingOrders = orderRepository.findByStatusOrderByCreatedAtDesc(Order.OrderStatus.PENDING, null).getTotalElements();
        
        // Đếm khách hàng (users với role CUSTOMER đã từng đặt hàng)
        long totalCustomers = userRepository.count(); // TODO: Filter by role CUSTOMER
        
        // Doanh thu
        BigDecimal totalRevenue = orderRepository.getTotalRevenue(
            LocalDateTime.of(2000, 1, 1, 0, 0),
            now
        );
        
        BigDecimal monthlyRevenue = orderRepository.getTotalRevenue(startOfMonth, now);
        
        return BusinessAnalyticsResponse.OverviewStats.builder()
            .totalProducts((int) totalProducts)
            .activeProducts((int) activeProducts)
            .lowStockProducts(lowStockProducts)
            .totalOrders((int) totalOrders)
            .pendingOrders((int) pendingOrders)
            .totalCustomers((int) totalCustomers)
            .totalRevenue(totalRevenue != null ? totalRevenue : BigDecimal.ZERO)
            .monthlyRevenue(monthlyRevenue != null ? monthlyRevenue : BigDecimal.ZERO)
            .build();
    }
    
    /**
     * Phân tích doanh thu
     */
    private BusinessAnalyticsResponse.RevenueAnalytics getRevenueAnalytics(User businessOwner) {
        LocalDateTime now = LocalDateTime.now();
        
        // Today
        LocalDateTime startOfToday = now.withHour(0).withMinute(0).withSecond(0);
        BigDecimal todayRevenue = orderRepository.getTotalRevenue(startOfToday, now);
        
        // This week
        LocalDateTime startOfWeek = now.minusDays(7).withHour(0).withMinute(0).withSecond(0);
        BigDecimal weekRevenue = orderRepository.getTotalRevenue(startOfWeek, now);
        
        // This month
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        BigDecimal monthRevenue = orderRepository.getTotalRevenue(startOfMonth, now);
        
        // Last month
        LocalDateTime startOfLastMonth = startOfMonth.minusMonths(1);
        LocalDateTime endOfLastMonth = startOfMonth.minusSeconds(1);
        BigDecimal lastMonthRevenue = orderRepository.getTotalRevenue(startOfLastMonth, endOfLastMonth);
        
        // This year
        LocalDateTime startOfYear = now.withDayOfYear(1).withHour(0).withMinute(0).withSecond(0);
        BigDecimal yearRevenue = orderRepository.getTotalRevenue(startOfYear, now);
        
        // Calculate growth rate
        BigDecimal growthRate = BigDecimal.ZERO;
        if (lastMonthRevenue != null && lastMonthRevenue.compareTo(BigDecimal.ZERO) > 0) {
            if (monthRevenue != null) {
                BigDecimal diff = monthRevenue.subtract(lastMonthRevenue);
                growthRate = diff.divide(lastMonthRevenue, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            }
        }
        
        return BusinessAnalyticsResponse.RevenueAnalytics.builder()
            .today(todayRevenue != null ? todayRevenue : BigDecimal.ZERO)
            .thisWeek(weekRevenue != null ? weekRevenue : BigDecimal.ZERO)
            .thisMonth(monthRevenue != null ? monthRevenue : BigDecimal.ZERO)
            .lastMonth(lastMonthRevenue != null ? lastMonthRevenue : BigDecimal.ZERO)
            .thisYear(yearRevenue != null ? yearRevenue : BigDecimal.ZERO)
            .growthRate(growthRate)
            .dailyRevenues(new ArrayList<>()) // TODO: Implement
            .monthlyRevenues(new ArrayList<>()) // TODO: Implement
            .build();
    }
    
    /**
     * Phân tích sản phẩm
     */
    private BusinessAnalyticsResponse.ProductAnalytics getProductAnalytics(User businessOwner) {
        long totalProducts = productRepository.count();
        long activeProducts = productRepository.findByIsActiveTrue(null).getTotalElements();
        
        // Low stock products
        var lowStockList = productRepository.findLowStockProducts(10);
        
        List<BusinessAnalyticsResponse.TopProduct> lowStock = lowStockList.stream()
            .map(p -> BusinessAnalyticsResponse.TopProduct.builder()
                .productId(p.getId())
                .productName(p.getName())
                .imageUrl(p.getImageUrls().isEmpty() ? null : p.getImageUrls().get(0))
                .stockQuantity(p.getStockQuantity())
                .build())
            .collect(Collectors.toList());
        
        // Top selling products
        var topSellingProducts = productRepository.findTop10ByIsActiveTrueOrderBySoldCountDesc();
        List<BusinessAnalyticsResponse.TopProduct> topSelling = topSellingProducts.stream()
            .map(p -> BusinessAnalyticsResponse.TopProduct.builder()
                .productId(p.getId())
                .productName(p.getName())
                .imageUrl(p.getImageUrls().isEmpty() ? null : p.getImageUrls().get(0))
                .quantitySold(p.getSoldCount())
                .stockQuantity(p.getStockQuantity())
                .build())
            .collect(Collectors.toList());
        
        return BusinessAnalyticsResponse.ProductAnalytics.builder()
            .totalProducts((int) totalProducts)
            .activeProducts((int) activeProducts)
            .outOfStock(lowStockList.stream().filter(p -> p.getStockQuantity() == 0).toList().size())
            .topSelling(topSelling)
            .topRevenue(new ArrayList<>()) // TODO: Implement
            .lowStock(lowStock)
            .categoryDistribution(new HashMap<>()) // TODO: Implement
            .build();
    }
    
    /**
     * Phân tích đơn hàng
     */
    private BusinessAnalyticsResponse.OrderAnalytics getOrderAnalytics(User businessOwner) {
        long totalOrders = orderRepository.count();
        
        // Count by status
        Map<String, Integer> statusDistribution = new HashMap<>();
        for (Order.OrderStatus status : Order.OrderStatus.values()) {
            long count = orderRepository.findByStatusOrderByCreatedAtDesc(status, null).getTotalElements();
            statusDistribution.put(status.name(), (int) count);
        }
        
        // Recent orders
        var recentOrdersList = orderRepository.findTop10ByOrderByCreatedAtDesc();
        List<BusinessAnalyticsResponse.RecentOrder> recentOrders = recentOrdersList.stream()
            .map(o -> BusinessAnalyticsResponse.RecentOrder.builder()
                .orderId(o.getId())
                .orderNumber(o.getOrderNumber())
                .customerName(o.getShippingName())
                .total(o.getTotal())
                .status(o.getStatus().name())
                .createdAt(o.getCreatedAt().toString())
                .build())
            .collect(Collectors.toList());
        
        return BusinessAnalyticsResponse.OrderAnalytics.builder()
            .totalOrders((int) totalOrders)
            .pendingOrders(statusDistribution.getOrDefault("PENDING", 0))
            .processingOrders(statusDistribution.getOrDefault("PROCESSING", 0))
            .shippingOrders(statusDistribution.getOrDefault("SHIPPING", 0))
            .completedOrders(statusDistribution.getOrDefault("COMPLETED", 0))
            .cancelledOrders(statusDistribution.getOrDefault("CANCELLED", 0))
            .averageOrderValue(BigDecimal.ZERO) // TODO: Calculate
            .orderStatusDistribution(statusDistribution)
            .recentOrders(recentOrders)
            .build();
    }
    
    /**
     * Phân tích khách hàng
     */
    private BusinessAnalyticsResponse.CustomerAnalytics getCustomerAnalytics(User businessOwner) {
        // TODO: Implement proper customer analytics
        
        return BusinessAnalyticsResponse.CustomerAnalytics.builder()
            .totalCustomers(0)
            .newCustomersThisMonth(0)
            .activeCustomers(0)
            .topCustomers(new ArrayList<>())
            .build();
    }
}
