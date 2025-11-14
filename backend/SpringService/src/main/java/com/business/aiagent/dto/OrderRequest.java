package com.business.aiagent.dto;

import com.business.aiagent.entity.Order;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequest {
    
    @NotEmpty(message = "Giỏ hàng không được trống")
    @Valid
    private List<OrderItemRequest> items = new ArrayList<>();
    
    @NotBlank(message = "Tên người nhận không được để trống")
    private String shippingName;
    
    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)(\\d{9,10})$", message = "Số điện thoại không hợp lệ")
    private String shippingPhone;
    
    @NotBlank(message = "Địa chỉ giao hàng không được để trống")
    private String shippingAddress;
    
    private String shippingCity;
    private String shippingDistrict;
    private String shippingWard;
    private String shippingNote;
    
    private Order.PaymentMethod paymentMethod = Order.PaymentMethod.COD;
    
    private BigDecimal shippingFee = BigDecimal.ZERO;
    private BigDecimal discount = BigDecimal.ZERO;
    private BigDecimal tax = BigDecimal.ZERO;
    
    private String note;
    
    @Data
    public static class OrderItemRequest {
        private Long productId;
        private Integer quantity;
    }
}
