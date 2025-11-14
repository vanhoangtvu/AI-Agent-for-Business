package com.business.aiagent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal compareAtPrice;
    private Integer stockQuantity;
    private String sku;
    private String barcode;
    private CategoryResponse category;
    private Boolean isActive;
    private Boolean isFeatured;
    private List<String> imageUrls;
    private List<String> tags;
    private Integer weight;
    private String manufacturer;
    private String origin;
    private BigDecimal rating;
    private Integer reviewCount;
    private Integer viewCount;
    private Integer soldCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Computed fields
    private Boolean inStock;
    private Boolean onSale;
    private BigDecimal discountPercentage;
}
