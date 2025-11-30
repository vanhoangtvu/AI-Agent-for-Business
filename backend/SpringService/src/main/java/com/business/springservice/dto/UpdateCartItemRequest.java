package com.business.springservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request to update cart item quantity")
public class UpdateCartItemRequest {
    
    @Schema(description = "New quantity", example = "3", required = true)
    private Integer quantity;
}
