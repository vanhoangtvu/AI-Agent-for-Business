package com.business.aiagent.service;

import com.business.aiagent.dto.OrderItemResponse;
import com.business.aiagent.dto.OrderRequest;
import com.business.aiagent.dto.OrderResponse;
import com.business.aiagent.entity.Order;
import com.business.aiagent.entity.OrderItem;
import com.business.aiagent.entity.Product;
import com.business.aiagent.entity.User;
import com.business.aiagent.repository.OrderRepository;
import com.business.aiagent.repository.ProductRepository;
import com.business.aiagent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public OrderResponse createOrder(OrderRequest request, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Create order items and calculate subtotal
        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;
        
        for (OrderRequest.OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found: " + itemRequest.getProductId()));
            
            // Check stock
            if (product.getStockQuantity() < itemRequest.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }
            
            BigDecimal itemPrice = product.getPrice();
            BigDecimal itemTotal = itemPrice.multiply(new BigDecimal(itemRequest.getQuantity()));
            
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .productName(product.getName())
                    .productImage(product.getImageUrls() != null && !product.getImageUrls().isEmpty() 
                            ? product.getImageUrls().get(0) : null)
                    .quantity(itemRequest.getQuantity())
                    .price(itemPrice)
                    .totalPrice(itemTotal)
                    .discount(BigDecimal.ZERO)
                    .build();
            
            orderItems.add(orderItem);
            subtotal = subtotal.add(itemTotal);
            
            // Update product stock and sold count
            product.setStockQuantity(product.getStockQuantity() - itemRequest.getQuantity());
            product.setSoldCount(product.getSoldCount() + itemRequest.getQuantity());
            productRepository.save(product);
        }
        
        // Calculate total
        BigDecimal total = subtotal
                .add(request.getShippingFee() != null ? request.getShippingFee() : BigDecimal.ZERO)
                .subtract(request.getDiscount() != null ? request.getDiscount() : BigDecimal.ZERO)
                .add(request.getTax() != null ? request.getTax() : BigDecimal.ZERO);
        
        // Generate order number
        String orderNumber = "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        
        // Create order
        Order order = Order.builder()
                .orderNumber(orderNumber)
                .user(user)
                .status(Order.OrderStatus.PENDING)
                .paymentStatus(Order.PaymentStatus.UNPAID)
                .paymentMethod(request.getPaymentMethod())
                .subtotal(subtotal)
                .shippingFee(request.getShippingFee())
                .discount(request.getDiscount())
                .tax(request.getTax())
                .total(total)
                .shippingName(request.getShippingName())
                .shippingPhone(request.getShippingPhone())
                .shippingAddress(request.getShippingAddress())
                .shippingCity(request.getShippingCity())
                .shippingDistrict(request.getShippingDistrict())
                .shippingWard(request.getShippingWard())
                .shippingNote(request.getShippingNote())
                .note(request.getNote())
                .build();
        
        // Set order reference in items
        for (OrderItem item : orderItems) {
            item.setOrder(order);
        }
        order.setItems(orderItems);
        
        order = orderRepository.save(order);
        return convertToResponse(order);
    }
    
    public Page<OrderResponse> getOrders(String username, int page, int size) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> orders = orderRepository.findByUser_IdOrderByCreatedAtDesc(user.getId(), pageable);
        return orders.map(this::convertToResponse);
    }
    
    public OrderResponse getOrder(Long id, String username) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check ownership
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        
        return convertToResponse(order);
    }
    
    public OrderResponse getOrderByNumber(String orderNumber, String username) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check ownership
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        
        return convertToResponse(order);
    }
    
    @Transactional
    public OrderResponse updateOrderStatus(Long id, Order.OrderStatus status, String username) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setStatus(status);
        
        // Set timestamps based on status
        if (status == Order.OrderStatus.SHIPPING && order.getShippedAt() == null) {
            order.setShippedAt(LocalDateTime.now());
        } else if (status == Order.OrderStatus.DELIVERED && order.getDeliveredAt() == null) {
            order.setDeliveredAt(LocalDateTime.now());
        } else if (status == Order.OrderStatus.CANCELLED && order.getCancelledAt() == null) {
            order.setCancelledAt(LocalDateTime.now());
        }
        
        order = orderRepository.save(order);
        return convertToResponse(order);
    }
    
    @Transactional
    public void cancelOrder(Long id, String reason, String username) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Check ownership
        if (!order.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Access denied");
        }
        
        // Can only cancel PENDING or CONFIRMED orders
        if (order.getStatus() != Order.OrderStatus.PENDING && 
            order.getStatus() != Order.OrderStatus.CONFIRMED) {
            throw new RuntimeException("Cannot cancel order in status: " + order.getStatus());
        }
        
        order.setStatus(Order.OrderStatus.CANCELLED);
        order.setCancelledAt(LocalDateTime.now());
        order.setCancellationReason(reason);
        
        // Restore product stock
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStockQuantity(product.getStockQuantity() + item.getQuantity());
            product.setSoldCount(product.getSoldCount() - item.getQuantity());
            productRepository.save(product);
        }
        
        orderRepository.save(order);
    }
    
    @Transactional
    public OrderResponse updatePaymentStatus(Long id, Order.PaymentStatus status, String username) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setPaymentStatus(status);
        
        if (status == Order.PaymentStatus.PAID && order.getPaidAt() == null) {
            order.setPaidAt(LocalDateTime.now());
        }
        
        order = orderRepository.save(order);
        return convertToResponse(order);
    }
    
    @Transactional
    public OrderResponse updateTrackingNumber(Long id, String trackingNumber, String username) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        order.setTrackingNumber(trackingNumber);
        order = orderRepository.save(order);
        return convertToResponse(order);
    }
    
    private OrderResponse convertToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .userId(order.getUser().getId())
                .userName(order.getUser().getFullName())
                .items(order.getItems().stream()
                        .map(this::convertItemToResponse)
                        .collect(Collectors.toList()))
                .status(order.getStatus())
                .paymentStatus(order.getPaymentStatus())
                .paymentMethod(order.getPaymentMethod())
                .subtotal(order.getSubtotal())
                .shippingFee(order.getShippingFee())
                .discount(order.getDiscount())
                .tax(order.getTax())
                .total(order.getTotal())
                .shippingName(order.getShippingName())
                .shippingPhone(order.getShippingPhone())
                .shippingAddress(order.getShippingAddress())
                .shippingCity(order.getShippingCity())
                .shippingDistrict(order.getShippingDistrict())
                .shippingWard(order.getShippingWard())
                .shippingNote(order.getShippingNote())
                .trackingNumber(order.getTrackingNumber())
                .paidAt(order.getPaidAt())
                .shippedAt(order.getShippedAt())
                .deliveredAt(order.getDeliveredAt())
                .cancelledAt(order.getCancelledAt())
                .cancellationReason(order.getCancellationReason())
                .note(order.getNote())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
    
    private OrderItemResponse convertItemToResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .id(item.getId())
                .productId(item.getProduct().getId())
                .productName(item.getProductName())
                .productImage(item.getProductImage())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .totalPrice(item.getTotalPrice())
                .discount(item.getDiscount())
                .build();
    }
}
