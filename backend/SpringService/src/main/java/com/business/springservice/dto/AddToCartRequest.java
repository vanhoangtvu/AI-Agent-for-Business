package com.business.springservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to add item to cart")
public class AddToCartRequest {
    
    @Schema(description = "Product ID", example = "1", required = true)
    private Long productId;
    
    @Schema(description = "Quantity", example = "2", required = true)
    private Integer quantity;
}
