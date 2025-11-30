package com.business.springservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Cart details")
public class CartDTO {
    
    @Schema(description = "Cart ID")
    private Long id;
    
    @Schema(description = "User ID")
    private Long userId;
    
    @Schema(description = "Cart items")
    private List<CartItemDTO> items;
    
    @Schema(description = "Total price")
    private BigDecimal totalPrice;
    
    @Schema(description = "Created at")
    private LocalDateTime createdAt;
    
    @Schema(description = "Updated at")
    private LocalDateTime updatedAt;
}
