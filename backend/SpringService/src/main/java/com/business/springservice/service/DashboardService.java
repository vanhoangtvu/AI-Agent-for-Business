package com.business.springservice.service;

import com.business.springservice.dto.BusinessDashboardDTO;
import com.business.springservice.dto.DashboardStatsDTO;
import com.business.springservice.dto.RevenueReportDTO;
import com.business.springservice.entity.Order;
import com.business.springservice.entity.OrderItem;
import com.business.springservice.entity.User;
import com.business.springservice.enums.OrderStatus;
import com.business.springservice.enums.Role;
import com.business.springservice.enums.Status;
import com.business.springservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final BusinessDocumentRepository documentRepository;
    
    @Transactional(readOnly = true)
    public DashboardStatsDTO getAdminDashboardStats() {
        DashboardStatsDTO stats = new DashboardStatsDTO();
        
        // User statistics
        stats.setTotalUsers(userRepository.count());
        stats.setTotalAdmins(countUsersByRole(Role.ADMIN));
        stats.setTotalBusinesses(countUsersByRole(Role.BUSINESS));
        stats.setTotalCustomers(countUsersByRole(Role.CUSTOMER));
        
        // Category statistics
        stats.setTotalCategories(categoryRepository.count());
        stats.setActiveCategories(countCategoriesByStatus(Status.ACTIVE));
        stats.setInactiveCategories(countCategoriesByStatus(Status.INACTIVE));
        
        // Product statistics
        stats.setTotalProducts(productRepository.count());
        stats.setActiveProducts(countProductsByStatus(Status.ACTIVE));
        stats.setInactiveProducts(countProductsByStatus(Status.INACTIVE));
        
        // Order statistics
        List<Order> allOrders = orderRepository.findAll();
        stats.setTotalOrders((long) allOrders.size());
        stats.setPendingOrders(countOrdersByStatus(OrderStatus.PENDING));
        stats.setConfirmedOrders(countOrdersByStatus(OrderStatus.CONFIRMED));
        stats.setProcessingOrders(countOrdersByStatus(OrderStatus.PROCESSING));
        stats.setShippingOrders(countOrdersByStatus(OrderStatus.SHIPPING));
        stats.setDeliveredOrders(countOrdersByStatus(OrderStatus.DELIVERED));
        stats.setCancelledOrders(countOrdersByStatus(OrderStatus.CANCELLED));
        stats.setReturnedOrders(countOrdersByStatus(OrderStatus.RETURNED));
        
        // Revenue statistics
        BigDecimal totalRevenue = allOrders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setTotalRevenue(totalRevenue);
        
        BigDecimal deliveredRevenue = orderRepository.findByStatus(OrderStatus.DELIVERED).stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setDeliveredRevenue(deliveredRevenue);
        
        // Document statistics
        stats.setTotalDocuments(documentRepository.count());
        
        return stats;
    }
    
    @Transactional(readOnly = true)
    public BusinessDashboardDTO getBusinessDashboardStats(Long businessId) {
        User business = userRepository.findById(businessId)
                .orElseThrow(() -> new RuntimeException("Business user not found"));
        
        BusinessDashboardDTO stats = new BusinessDashboardDTO();
        stats.setBusinessId(businessId);
        stats.setBusinessUsername(business.getUsername());
        
        // Product statistics
        List<com.business.springservice.entity.Product> products = productRepository.findBySellerId(businessId);
        stats.setTotalProducts((long) products.size());
        stats.setActiveProducts(products.stream()
                .filter(p -> p.getStatus() == Status.ACTIVE)
                .count());
        stats.setInactiveProducts(products.stream()
                .filter(p -> p.getStatus() == Status.INACTIVE)
                .count());
        
        // Inventory statistics
        BigDecimal inventoryValue = products.stream()
                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setTotalInventoryValue(inventoryValue);
        
        Long totalStock = products.stream()
                .mapToLong(com.business.springservice.entity.Product::getQuantity)
                .sum();
        stats.setTotalStockQuantity(totalStock);
        
        Long outOfStock = products.stream()
                .filter(p -> p.getQuantity() == 0)
                .count();
        stats.setOutOfStockProducts(outOfStock);
        
        Long lowStock = products.stream()
                .filter(p -> p.getQuantity() > 0 && p.getQuantity() < 10)
                .count();
        stats.setLowStockProducts(lowStock);
        
        // Order statistics - orders containing products from this business
        List<Order> allOrders = orderRepository.findAll();
        List<Order> businessOrders = allOrders.stream()
                .filter(order -> order.getOrderItems().stream()
                        .anyMatch(item -> item.getProduct().getSeller().getId().equals(businessId)))
                .toList();
        
        stats.setTotalOrders((long) businessOrders.size());
        stats.setPendingOrders(businessOrders.stream()
                .filter(o -> o.getStatus() == OrderStatus.PENDING)
                .count());
        stats.setDeliveredOrders(businessOrders.stream()
                .filter(o -> o.getStatus() == OrderStatus.DELIVERED)
                .count());
        
        // Revenue statistics - only from this business's products
        BigDecimal totalRevenue = businessOrders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .filter(item -> item.getProduct().getSeller().getId().equals(businessId))
                .map(item -> item.getSubtotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setTotalRevenue(totalRevenue);
        
        BigDecimal deliveredRevenue = businessOrders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getOrderItems().stream())
                .filter(item -> item.getProduct().getSeller().getId().equals(businessId))
                .map(item -> item.getSubtotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        stats.setDeliveredRevenue(deliveredRevenue);
        
        // Document statistics
        stats.setTotalDocuments((long) documentRepository.findByBusinessId(businessId).size());
        
        return stats;
    }
    
    private Long countUsersByRole(Role role) {
        return userRepository.findAll().stream()
                .filter(user -> user.getRole() == role)
                .count();
    }
    
    private Long countCategoriesByStatus(Status status) {
        return categoryRepository.findAll().stream()
                .filter(category -> category.getStatus() == status)
                .count();
    }
    
    private Long countProductsByStatus(Status status) {
        return productRepository.findAll().stream()
                .filter(product -> product.getStatus() == status)
                .count();
    }
    
    private Long countOrdersByStatus(OrderStatus status) {
        return (long) orderRepository.findByStatus(status).size();
    }
    
    @Transactional(readOnly = true)
    public List<RevenueReportDTO> getRevenueByDay(Long businessId, int days) {
        List<RevenueReportDTO> reports = new ArrayList<>();
        LocalDate today = LocalDate.now();
        
        for (int i = days - 1; i >= 0; i--) {
            LocalDate date = today.minusDays(i);
            LocalDateTime startOfDay = date.atStartOfDay();
            LocalDateTime endOfDay = date.atTime(23, 59, 59);
            
            RevenueReportDTO report = calculateRevenueForPeriod(businessId, startOfDay, endOfDay);
            report.setPeriod(date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            reports.add(report);
        }
        
        return reports;
    }
    
    @Transactional(readOnly = true)
    public List<RevenueReportDTO> getRevenueByWeek(Long businessId, int weeks) {
        List<RevenueReportDTO> reports = new ArrayList<>();
        LocalDate today = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        
        for (int i = weeks - 1; i >= 0; i--) {
            LocalDate weekStart = today.minusWeeks(i)
                    .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate weekEnd = weekStart.plusDays(6);
            
            LocalDateTime startOfWeek = weekStart.atStartOfDay();
            LocalDateTime endOfWeek = weekEnd.atTime(23, 59, 59);
            
            RevenueReportDTO report = calculateRevenueForPeriod(businessId, startOfWeek, endOfWeek);
            int weekNumber = weekStart.get(weekFields.weekOfYear());
            report.setPeriod("Week " + weekNumber + " (" + weekStart + " to " + weekEnd + ")");
            reports.add(report);
        }
        
        return reports;
    }
    
    @Transactional(readOnly = true)
    public List<RevenueReportDTO> getRevenueByMonth(Long businessId, int months) {
        List<RevenueReportDTO> reports = new ArrayList<>();
        LocalDate today = LocalDate.now();
        
        for (int i = months - 1; i >= 0; i--) {
            LocalDate monthDate = today.minusMonths(i);
            LocalDate monthStart = monthDate.with(TemporalAdjusters.firstDayOfMonth());
            LocalDate monthEnd = monthDate.with(TemporalAdjusters.lastDayOfMonth());
            
            LocalDateTime startOfMonth = monthStart.atStartOfDay();
            LocalDateTime endOfMonth = monthEnd.atTime(23, 59, 59);
            
            RevenueReportDTO report = calculateRevenueForPeriod(businessId, startOfMonth, endOfMonth);
            report.setPeriod(monthDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
            reports.add(report);
        }
        
        return reports;
    }
    
    private RevenueReportDTO calculateRevenueForPeriod(Long businessId, LocalDateTime start, LocalDateTime end) {
        List<Order> ordersInPeriod = orderRepository.findAll().stream()
                .filter(order -> {
                    LocalDateTime createdAt = order.getCreatedAt();
                    return createdAt != null && 
                           !createdAt.isBefore(start) && 
                           !createdAt.isAfter(end);
                })
                .collect(Collectors.toList());
        
        // Filter orders that contain products from this business
        List<Order> businessOrders;
        if (businessId != null) {
            businessOrders = ordersInPeriod.stream()
                    .filter(order -> order.getOrderItems().stream()
                            .anyMatch(item -> item.getProduct().getSeller().getId().equals(businessId)))
                    .collect(Collectors.toList());
        } else {
            businessOrders = ordersInPeriod;
        }
        
        long totalOrders = businessOrders.size();
        long deliveredOrders = businessOrders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .count();
        
        // Calculate revenue and products sold
        List<OrderItem> deliveredItems = businessOrders.stream()
                .filter(order -> order.getStatus() == OrderStatus.DELIVERED)
                .flatMap(order -> order.getOrderItems().stream())
                .collect(Collectors.toList());
        
        if (businessId != null) {
            deliveredItems = deliveredItems.stream()
                    .filter(item -> item.getProduct().getSeller().getId().equals(businessId))
                    .collect(Collectors.toList());
        }
        
        BigDecimal revenue = deliveredItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        long productsSold = deliveredItems.stream()
                .mapToLong(OrderItem::getQuantity)
                .sum();
        
        RevenueReportDTO report = new RevenueReportDTO();
        report.setTotalOrders(totalOrders);
        report.setDeliveredOrders(deliveredOrders);
        report.setRevenue(revenue);
        report.setProductsSold(productsSold);
        
        return report;
    }
}