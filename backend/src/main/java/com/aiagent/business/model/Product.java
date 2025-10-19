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
 * Product Entity - Bảng sản phẩm
 */
@Entity
@Table(name = "products", indexes = {
    @Index(name = "idx_business", columnList = "business_id"),
    @Index(name = "idx_category", columnList = "category_id"),
    @Index(name = "idx_sku", columnList = "sku"),
    @Index(name = "idx_slug", columnList = "slug"),
    @Index(name = "idx_is_active", columnList = "is_active")
})
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "business_id", nullable = false)
    private Long businessId;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(unique = true, nullable = false)
    private String slug;

    @Column(unique = true)
    private String sku;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "images", columnDefinition = "TEXT")
    private String images; // CSV of image URLs

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "sale_price")
    private Double salePrice;

    @Column(name = "cost_price")
    private Double costPrice;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity = 0;

    @Column(name = "low_stock_threshold")
    private Integer lowStockThreshold = 10;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "dimensions")
    private String dimensions;

    @Column(name = "total_sold", nullable = false)
    private Integer totalSold = 0;

    @Column(name = "total_revenue", nullable = false)
    private Double totalRevenue = 0.0;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "review_count", nullable = false)
    private Integer reviewCount = 0;

    @Column(name = "tags")
    private String tags; // CSV: 'bestseller,new-arrival'

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "is_featured", nullable = false)
    private Boolean isFeatured = false;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}

