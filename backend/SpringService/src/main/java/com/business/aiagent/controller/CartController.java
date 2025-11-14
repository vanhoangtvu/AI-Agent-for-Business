package com.business.aiagent.controller;

import com.business.aiagent.dto.CartItemRequest;
import com.business.aiagent.dto.CartResponse;
import com.business.aiagent.entity.Role;
import com.business.aiagent.security.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

/**
 * Shopping Cart Controller
 * CUSTOMER only - Each user has their own cart
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Tag(name = "Shopping Cart", description = "Shopping cart management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class CartController {
    
    @GetMapping
    @Operation(summary = "View cart", description = "CUSTOMER - Get user's cart")
    @RequirePermission(Role.Permission.ORDER_CREATE)
    public ResponseEntity<CartResponse> getCart() {
        // TODO: Implement service call
        // Auto get user from SecurityContext
        // Auto create cart if not exists
        return ResponseEntity.ok(CartResponse.builder().build());
    }
    
    @PostMapping("/items")
    @Operation(summary = "Add to cart", description = "CUSTOMER - Add product to cart")
    @RequirePermission(Role.Permission.ORDER_CREATE)
    public ResponseEntity<CartResponse> addItemToCart(@Valid @RequestBody CartItemRequest request) {
        // TODO: Implement service call
        // Check product exists and in stock
        // If product already in cart, increase quantity
        // Validate stock quantity
        return ResponseEntity.ok(CartResponse.builder().build());
    }
    
    @PutMapping("/items/{itemId}")
    @Operation(summary = "Update quantity", description = "CUSTOMER - Change product quantity")
    @RequirePermission(Role.Permission.ORDER_CREATE)
    public ResponseEntity<CartResponse> updateCartItemQuantity(
        @PathVariable Long itemId,
        @RequestParam Integer quantity
    ) {
        // TODO: Implement service call
        // Validate quantity > 0
        // Validate stock availability
        // If quantity = 0, remove item
        return ResponseEntity.ok(CartResponse.builder().build());
    }
    
    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "Remove from cart", description = "CUSTOMER - Remove product from cart")
    @RequirePermission(Role.Permission.ORDER_CREATE)
    public ResponseEntity<CartResponse> removeItemFromCart(@PathVariable Long itemId) {
        // TODO: Implement service call
        // Check ownership (item belongs to current user's cart)
        return ResponseEntity.ok(CartResponse.builder().build());
    }
    
    @DeleteMapping
    @Operation(summary = "Clear cart", description = "CUSTOMER - Clear entire cart")
    @RequirePermission(Role.Permission.ORDER_CREATE)
    public ResponseEntity<Void> clearCart() {
        // TODO: Implement service call
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/checkout")
    @Operation(summary = "Checkout", description = "CUSTOMER - Convert cart to order")
    @RequirePermission(Role.Permission.ORDER_CREATE)
    public ResponseEntity<?> checkout(@RequestBody @Valid CheckoutRequest request) {
        // TODO: Implement service call
        // Validate cart not empty
        // Validate all products still in stock
        // Create order from cart
        // Clear cart after successful order creation
        // Return OrderResponse
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/count")
    @Operation(summary = "Count cart items", description = "CUSTOMER - Cart badge count")
    @RequirePermission(Role.Permission.ORDER_CREATE)
    public ResponseEntity<Integer> getCartItemCount() {
        // TODO: Implement service call
        return ResponseEntity.ok(0);
    }
    
    @GetMapping("/validate")
    @Operation(summary = "Validate cart", description = "CUSTOMER - Validate stock availability")
    @RequirePermission(Role.Permission.ORDER_CREATE)
    public ResponseEntity<?> validateCart() {
        // TODO: Implement service call
        // Check all products still exist
        // Check stock availability
        // Check price changes
        // Return validation result
        return ResponseEntity.ok().build();
    }
    
    /**
     * DTO class for checkout request
     */
    public static class CheckoutRequest {
        private String shippingAddress;
        private String shippingCity;
        private String shippingProvince;
        private String shippingPostalCode;
        private String shippingPhone;
        private String paymentMethod; // COD, BANK_TRANSFER, CREDIT_CARD, E_WALLET, PAYPAL
        private String note;
    }
}
