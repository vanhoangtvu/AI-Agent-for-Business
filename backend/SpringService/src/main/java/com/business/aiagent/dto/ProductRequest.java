package com.business.aiagent.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductRequest {
    
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 3, max = 200, message = "Tên sản phẩm phải từ 3-200 ký tự")
    private String name;
    
    @Size(max = 1000, message = "Mô tả không được quá 1000 ký tự")
    private String description;
    
    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private BigDecimal price;
    
    @DecimalMin(value = "0.0", message = "Giá so sánh phải >= 0")
    private BigDecimal compareAtPrice;
    
    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho phải >= 0")
    private Integer stockQuantity;
    
    private String sku;
    
    private String barcode;
    
    private Long categoryId;
    
    private Boolean isActive = true;
    
    private Boolean isFeatured = false;
    
    private List<String> imageUrls = new ArrayList<>();
    
    private List<String> tags = new ArrayList<>();
    
    @Min(value = 0, message = "Trọng lượng phải >= 0")
    private Integer weight;
    
    private String manufacturer;
    
    private String origin;
}
