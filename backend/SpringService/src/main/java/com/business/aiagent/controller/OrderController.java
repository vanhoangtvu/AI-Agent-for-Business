package com.business.aiagent.controller;

import com.business.aiagent.dto.OrderRequest;
import com.business.aiagent.dto.OrderResponse;
import com.business.aiagent.entity.Order;
import com.business.aiagent.entity.Role;
import com.business.aiagent.security.RequirePermission;
import com.business.aiagent.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Management", description = "Order management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {
    
    private final OrderService orderService;
    
    @PostMapping
    @Operation(summary = "Create order", description = "CUSTOMER - Create order from cart")
    @RequirePermission(Role.Permission.ORDER_CREATE)
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody OrderRequest request,
            Authentication authentication) {
        OrderResponse order = orderService.createOrder(request, authentication.getName());
        return ResponseEntity.ok(order);
    }
    
    @GetMapping
    @Operation(summary = "Get my orders", description = "CUSTOMER - View own orders")
    @RequirePermission(Role.Permission.ORDER_READ)
    public ResponseEntity<Page<OrderResponse>> getOrders(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Authentication authentication
    ) {
        Page<OrderResponse> orders = orderService.getOrders(authentication.getName(), page, size);
        return ResponseEntity.ok(orders);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get order details")
    @RequirePermission(Role.Permission.ORDER_READ)
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id,
            Authentication authentication) {
        OrderResponse order = orderService.getOrder(id, authentication.getName());
        return ResponseEntity.ok(order);
    }
    
    @GetMapping("/number/{orderNumber}")
    @Operation(summary = "Find order by number")
    public ResponseEntity<OrderResponse> getOrderByNumber(
            @PathVariable String orderNumber,
            Authentication authentication) {
        OrderResponse order = orderService.getOrderByNumber(orderNumber, authentication.getName());
        return ResponseEntity.ok(order);
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update order status", description = "BUSINESS/ADMIN")
    @RequirePermission(Role.Permission.ORDER_UPDATE)
    public ResponseEntity<OrderResponse> updateOrderStatus(
        @PathVariable Long id,
        @RequestParam Order.OrderStatus status,
        Authentication authentication
    ) {
        OrderResponse order = orderService.updateOrderStatus(id, status, authentication.getName());
        return ResponseEntity.ok(order);
    }
    
    @PostMapping("/{id}/cancel")
    @Operation(summary = "Cancel order", description = "CUSTOMER - Cancel own order")
    @RequirePermission(Role.Permission.ORDER_UPDATE)
    public ResponseEntity<Void> cancelOrder(
        @PathVariable Long id,
        @RequestParam(required = false) String reason,
        Authentication authentication
    ) {
        orderService.cancelOrder(id, reason, authentication.getName());
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/tracking")
    @Operation(summary = "Update tracking number", description = "BUSINESS/ADMIN")
    @RequirePermission(Role.Permission.ORDER_UPDATE)
    public ResponseEntity<OrderResponse> updateTrackingNumber(
        @PathVariable Long id,
        @RequestParam String trackingNumber,
        Authentication authentication
    ) {
        OrderResponse order = orderService.updateTrackingNumber(id, trackingNumber, authentication.getName());
        return ResponseEntity.ok(order);
    }
    
    @PatchMapping("/{id}/payment-status")
    @Operation(summary = "Update payment status", description = "BUSINESS/ADMIN")
    @RequirePermission(Role.Permission.ORDER_UPDATE)
    public ResponseEntity<OrderResponse> updatePaymentStatus(
        @PathVariable Long id,
        @RequestParam Order.PaymentStatus status,
        Authentication authentication
    ) {
        OrderResponse order = orderService.updatePaymentStatus(id, status, authentication.getName());
        return ResponseEntity.ok(order);
    }
}
