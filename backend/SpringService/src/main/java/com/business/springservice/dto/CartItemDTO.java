package com.business.springservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Cart item details")
public class CartItemDTO {
    
    @Schema(description = "Cart item ID")
    private Long id;
    
    @Schema(description = "Product ID")
    private Long productId;
    
    @Schema(description = "Product name")
    private String productName;
    
    @Schema(description = "Product price")
    private BigDecimal productPrice;
    
    @Schema(description = "Product image URL")
    private String productImageUrl;
    
    @Schema(description = "Quantity")
    private Integer quantity;
    
    @Schema(description = "Subtotal price")
    private BigDecimal subtotal;
    
    @Schema(description = "Added at")
    private LocalDateTime addedAt;
}
