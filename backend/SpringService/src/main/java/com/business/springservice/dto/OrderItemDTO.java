package com.business.springservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Order item ID")
    private Long id;
    
    @Schema(description = "Product ID", example = "1")
    private Long productId;
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Product name")
    private String productName;
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Product price at time of order")
    private BigDecimal productPrice;
    
    @Schema(description = "Quantity ordered", example = "2")
    private Integer quantity;
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Subtotal (price x quantity)")
    private BigDecimal subtotal;
}
