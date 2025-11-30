package com.business.springservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateAddressRequest {
    
    @Schema(description = "New shipping address", example = "456 Le Loi, Q3, TPHCM", required = true)
    private String shippingAddress;
}
