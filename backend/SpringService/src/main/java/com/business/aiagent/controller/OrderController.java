package com.business.aiagent.controller;

import com.business.aiagent.dto.OrderRequest;
import com.business.aiagent.dto.OrderResponse;
import com.business.aiagent.entity.Order;
import com.business.aiagent.entity.Role;
import com.business.aiagent.security.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Order Management Controller
 * CUSTOMER: View own orders, create new orders, cancel orders
 * BUSINESS: View orders containing own products, update status
 * ADMIN: Manage all orders
 */
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "Order management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {
    
    @PostMapping
    @Operation(summary = "Create order", description = "CUSTOMER - Create order from cart")
    @RequirePermission(Role.Permission.ORDER_CREATE)
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        // TODO: Implement service call
        // Auto set user from SecurityContext
        return ResponseEntity.ok(OrderResponse.builder().build());
    }
    
    @GetMapping
    @Operation(summary = "Get order list", description = "Filtered by role")
    @RequirePermission(Role.Permission.ORDER_READ)
    public ResponseEntity<Page<OrderResponse>> getOrders(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) Order.OrderStatus status
    ) {
        // TODO: Implement service call
        return ResponseEntity.ok(Page.empty());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get order details", description = "Check ownership permissions")
    @RequirePermission(Role.Permission.ORDER_READ)
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        // TODO: Implement service call
        // Check ownership: CUSTOMER (own order), BUSINESS (has products), ADMIN (all)
        return ResponseEntity.ok(OrderResponse.builder().build());
    }
    
    @GetMapping("/number/{orderNumber}")
    @Operation(summary = "Find order by number", description = "Public tracking endpoint")
    public ResponseEntity<OrderResponse> getOrderByNumber(@PathVariable String orderNumber) {
        // TODO: Implement service call
        // Basic info for tracking
        return ResponseEntity.ok(OrderResponse.builder().build());
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update order status", description = "BUSINESS/ADMIN - Workflow: PENDING → CONFIRMED → PROCESSING → SHIPPING → DELIVERED")
    @RequirePermission(Role.Permission.ORDER_UPDATE)
    public ResponseEntity<OrderResponse> updateOrderStatus(
        @PathVariable Long id,
        @RequestParam Order.OrderStatus status
    ) {
        // TODO: Implement service call
        // Validate workflow: PENDING → CONFIRMED → PROCESSING → SHIPPING → DELIVERED
        return ResponseEntity.ok(OrderResponse.builder().build());
    }
    
    @PatchMapping("/{id}/cancel")
    @Operation(summary = "Cancel order", description = "CUSTOMER (PENDING only), BUSINESS/ADMIN (any status)")
    @RequirePermission(Role.Permission.ORDER_UPDATE)
    public ResponseEntity<OrderResponse> cancelOrder(
        @PathVariable Long id,
        @RequestParam(required = false) String reason
    ) {
        // TODO: Implement service call
        return ResponseEntity.ok(OrderResponse.builder().build());
    }
    
    @PatchMapping("/{id}/tracking")
    @Operation(summary = "Update tracking number", description = "BUSINESS/ADMIN - Add tracking number")
    @RequirePermission(Role.Permission.ORDER_UPDATE)
    public ResponseEntity<OrderResponse> updateTrackingNumber(
        @PathVariable Long id,
        @RequestParam String trackingNumber
    ) {
        // TODO: Implement service call
        return ResponseEntity.ok(OrderResponse.builder().build());
    }
    
    @PatchMapping("/{id}/payment-status")
    @Operation(summary = "Update payment status", description = "BUSINESS/ADMIN - Payment status")
    @RequirePermission(Role.Permission.ORDER_UPDATE)
    public ResponseEntity<OrderResponse> updatePaymentStatus(
        @PathVariable Long id,
        @RequestParam Order.PaymentStatus paymentStatus
    ) {
        // TODO: Implement service call
        return ResponseEntity.ok(OrderResponse.builder().build());
    }
    
    @GetMapping("/statistics")
    @Operation(summary = "Get order statistics", description = "BUSINESS/ADMIN - Order statistics")
    @RequirePermission(Role.Permission.ORDER_READ)
    public ResponseEntity<?> getOrderStatistics(
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate
    ) {
        // TODO: Implement service call
        // Return: total orders, total revenue, orders by status, avg order value
        return ResponseEntity.ok().build();
    }
}
