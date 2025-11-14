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
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal compareAtPrice; // Giá gốc để hiển thị giảm giá
    
    @Column(nullable = false)
    private Integer stockQuantity = 0;
    
    private String sku; // Stock Keeping Unit - Mã sản phẩm
    
    private String barcode;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_owner_id")
    private User businessOwner; // Chủ doanh nghiệp sở hữu sản phẩm này
    
    @Column(nullable = false)
    private Boolean isActive = true;
    
    private Boolean isFeatured = false; // Sản phẩm nổi bật
    
    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image_url")
    private List<String> imageUrls = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "tag")
    private List<String> tags = new ArrayList<>();
    
    private Integer weight; // Trọng lượng (gram)
    
    private String manufacturer; // Nhà sản xuất
    
    private String origin; // Xuất xứ
    
    @Column(precision = 3, scale = 2)
    private BigDecimal rating = BigDecimal.ZERO; // Đánh giá trung bình
    
    private Integer reviewCount = 0; // Số lượng đánh giá
    
    private Integer viewCount = 0; // Lượt xem
    
    private Integer soldCount = 0; // Đã bán
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private User createdBy;
    
    // Computed fields
    @Transient
    public boolean isInStock() {
        return stockQuantity != null && stockQuantity > 0;
    }
    
    @Transient
    public boolean isOnSale() {
        return compareAtPrice != null && compareAtPrice.compareTo(price) > 0;
    }
    
    @Transient
    public BigDecimal getDiscountPercentage() {
        if (isOnSale()) {
            BigDecimal discount = compareAtPrice.subtract(price);
            return discount.divide(compareAtPrice, 2, BigDecimal.ROUND_HALF_UP)
                          .multiply(BigDecimal.valueOf(100));
        }
        return BigDecimal.ZERO;
    }
}
