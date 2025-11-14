package com.business.aiagent.controller;

import com.business.aiagent.dto.ProductRequest;
import com.business.aiagent.dto.ProductResponse;
import com.business.aiagent.entity.Role;
import com.business.aiagent.security.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;

/**
 * Product Management Controller
 * ADMIN: Manage all products
 * BUSINESS: Manage own products
 * CUSTOMER: View products only
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Product management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {
    
    @GetMapping
    @Operation(summary = "Get all products", description = "Public API - No authentication required")
    public ResponseEntity<Page<ProductResponse>> getAllProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "DESC") String sortDir,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) BigDecimal minPrice,
        @RequestParam(required = false) BigDecimal maxPrice
    ) {
        // TODO: Implement service call
        return ResponseEntity.ok(Page.empty());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get product details", description = "Public API")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        // TODO: Implement service call
        return ResponseEntity.ok(ProductResponse.builder().build());
    }
    
    @PostMapping
    @Operation(summary = "Create new product")
    @RequirePermission(Role.Permission.PRODUCT_CREATE)
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        // TODO: Implement service call
        return ResponseEntity.ok(ProductResponse.builder().build());
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    @RequirePermission(Role.Permission.PRODUCT_UPDATE)
    public ResponseEntity<ProductResponse> updateProduct(
        @PathVariable Long id,
        @Valid @RequestBody ProductRequest request
    ) {
        // TODO: Implement service call
        return ResponseEntity.ok(ProductResponse.builder().build());
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    @RequirePermission(Role.Permission.PRODUCT_DELETE)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        // TODO: Implement service call
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/my-products")
    @Operation(summary = "Get my products", description = "BUSINESS role - View own products")
    @RequirePermission(Role.Permission.PRODUCT_READ)
    public ResponseEntity<Page<ProductResponse>> getMyProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        // TODO: Implement service call
        return ResponseEntity.ok(Page.empty());
    }
    
    @GetMapping("/top-selling")
    @Operation(summary = "Top selling products", description = "Public API")
    public ResponseEntity<?> getTopSellingProducts(@RequestParam(defaultValue = "10") int limit) {
        // TODO: Implement service call
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/featured")
    @Operation(summary = "Featured products", description = "Public API")
    public ResponseEntity<Page<ProductResponse>> getFeaturedProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        // TODO: Implement service call
        return ResponseEntity.ok(Page.empty());
    }
    
    @GetMapping("/low-stock")
    @Operation(summary = "Low stock products", description = "BUSINESS/ADMIN - Products with low inventory")
    @RequirePermission(Role.Permission.PRODUCT_READ)
    public ResponseEntity<?> getLowStockProducts(@RequestParam(defaultValue = "10") int threshold) {
        // TODO: Implement service call
        return ResponseEntity.ok().build();
    }
}
