package com.business.aiagent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String orderNumber; // Mã đơn hàng duy nhất
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.PENDING;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.UNPAID;
    
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal subtotal = BigDecimal.ZERO; // Tổng tiền hàng
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal shippingFee = BigDecimal.ZERO; // Phí ship
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO; // Giảm giá
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tax = BigDecimal.ZERO; // Thuế
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total = BigDecimal.ZERO; // Tổng thanh toán
    
    // Shipping Information
    @Column(nullable = false)
    private String shippingName;
    
    @Column(nullable = false)
    private String shippingPhone;
    
    @Column(nullable = false)
    private String shippingAddress;
    
    private String shippingCity;
    
    private String shippingDistrict;
    
    private String shippingWard;
    
    private String shippingNote;
    
    // Tracking
    private String trackingNumber;
    
    private LocalDateTime paidAt;
    
    private LocalDateTime shippedAt;
    
    private LocalDateTime deliveredAt;
    
    private LocalDateTime cancelledAt;
    
    private String cancellationReason;
    
    @Column(length = 1000)
    private String note;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    public enum OrderStatus {
        PENDING,        // Chờ xác nhận
        CONFIRMED,      // Đã xác nhận
        PROCESSING,     // Đang xử lý
        SHIPPING,       // Đang giao hàng
        DELIVERED,      // Đã giao hàng
        COMPLETED,      // Hoàn thành
        CANCELLED,      // Đã hủy
        REFUNDED        // Đã hoàn tiền
    }
    
    public enum PaymentStatus {
        UNPAID,         // Chưa thanh toán
        PAID,           // Đã thanh toán
        REFUNDED,       // Đã hoàn tiền
        FAILED          // Thanh toán thất bại
    }
    
    public enum PaymentMethod {
        COD,            // Thanh toán khi nhận hàng
        BANK_TRANSFER,  // Chuyển khoản ngân hàng
        CREDIT_CARD,    // Thẻ tín dụng
        E_WALLET,       // Ví điện tử (Momo, ZaloPay, etc.)
        PAYPAL
    }
    
    // Helper methods
    public void calculateTotal() {
        this.subtotal = items.stream()
            .map(OrderItem::getTotalPrice)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        this.total = subtotal
            .add(shippingFee)
            .add(tax)
            .subtract(discount);
    }
    
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
        calculateTotal();
    }
    
    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
        calculateTotal();
    }
}
