package com.aiagent.business.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * Order Entity - Bảng đơn hàng
 */
@Entity
@Table(name = "orders", indexes = {
    @Index(name = "idx_business", columnList = "business_id"),
    @Index(name = "idx_customer", columnList = "customer_id"),
    @Index(name = "idx_order_number", columnList = "order_number"),
    @Index(name = "idx_status", columnList = "status"),
    @Index(name = "idx_created_at", columnList = "created_at")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_id", nullable = false)
    private Long businessId;

    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "subtotal", precision = 15, scale = 2, nullable = false)
    private Double subtotal;

    @Column(name = "discount", precision = 15, scale = 2, nullable = false)
    private Double discount = 0.0;

    @Column(name = "shipping_fee", precision = 15, scale = 2, nullable = false)
    private Double shippingFee = 0.0;

    @Column(name = "tax", precision = 15, scale = 2, nullable = false)
    private Double tax = 0.0;

    @Column(name = "total_amount", precision = 15, scale = 2, nullable = false)
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "shipping_name")
    private String shippingName;

    @Column(name = "shipping_phone")
    private String shippingPhone;

    @Column(name = "shipping_address", columnDefinition = "TEXT")
    private String shippingAddress;

    @Column(name = "shipping_city")
    private String shippingCity;

    @Column(name = "shipping_district")
    private String shippingDistrict;

    @Column(name = "shipping_ward")
    private String shippingWard;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "internal_notes", columnDefinition = "TEXT")
    private String internalNotes;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private OrderSource source = OrderSource.MANUAL;

    @Column(name = "source_id")
    private String sourceId;

    @Column(name = "assigned_to")
    private Long assignedTo;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "shipped_at")
    private LocalDateTime shippedAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancellation_reason", columnDefinition = "TEXT")
    private String cancellationReason;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public enum OrderStatus {
        PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, REFUNDED
    }

    public enum PaymentMethod {
        COD, BANK_TRANSFER, CREDIT_CARD, E_WALLET, OTHER
    }

    public enum PaymentStatus {
        PENDING, PAID, FAILED, REFUNDED
    }

    public enum OrderSource {
        WEBSITE, ZALO_OA, ZALO_PERSONAL, MANUAL, IMPORT
    }
}

