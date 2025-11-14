package com.business.aiagent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    
    @Column(nullable = false)
    private String productName; // Lưu tên sản phẩm tại thời điểm đặt hàng
    
    private String productImage; // Lưu ảnh sản phẩm
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // Giá tại thời điểm đặt hàng
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice; // price * quantity
    
    private BigDecimal discount = BigDecimal.ZERO; // Giảm giá cho item này
    
    // Helper method
    public void calculateTotalPrice() {
        this.totalPrice = this.price
            .multiply(BigDecimal.valueOf(quantity))
            .subtract(discount);
    }
}
