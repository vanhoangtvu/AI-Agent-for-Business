package com.business.springservice.controller;

import com.business.springservice.dto.AddToCartRequest;
import com.business.springservice.dto.CartDTO;
import com.business.springservice.dto.UpdateCartItemRequest;
import com.business.springservice.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Tag(name = "Cart", description = "APIs for managing shopping cart (requires authentication)")
public class CartController {
    
    private final CartService cartService;
    
    @GetMapping
    @Operation(summary = "Get my cart", description = "Retrieve current user's shopping cart")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved cart"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<CartDTO> getCart(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(cartService.getCart(userId));
    }
    
    @PostMapping("/items")
    @Operation(summary = "Add item to cart", description = "Add a product to the shopping cart")
    public ResponseEntity<CartDTO> addToCart(
            HttpServletRequest request,
            @RequestBody AddToCartRequest addRequest) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(cartService.addToCart(userId, addRequest.getProductId(), addRequest.getQuantity()));
    }
    
    @PatchMapping("/items/{itemId}")
    @Operation(summary = "Update cart item quantity", description = "Update the quantity of a cart item")
    public ResponseEntity<CartDTO> updateCartItem(
            HttpServletRequest request,
            @PathVariable Long itemId,
            @RequestBody UpdateCartItemRequest updateRequest) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(cartService.updateCartItem(userId, itemId, updateRequest.getQuantity()));
    }
    
    @DeleteMapping("/items/{itemId}")
    @Operation(summary = "Remove item from cart", description = "Remove a product from the shopping cart")
    public ResponseEntity<CartDTO> removeCartItem(
            HttpServletRequest request,
            @PathVariable Long itemId) {
        Long userId = (Long) request.getAttribute("userId");
        return ResponseEntity.ok(cartService.removeCartItem(userId, itemId));
    }
    
    @DeleteMapping
    @Operation(summary = "Clear cart", description = "Remove all items from the shopping cart")
    public ResponseEntity<Void> clearCart(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        cartService.clearCart(userId);
        return ResponseEntity.noContent().build();
    }
}
