package com.business.aiagent.controller;

import com.business.aiagent.dto.ProductRequest;
import com.business.aiagent.dto.ProductResponse;
import com.business.aiagent.entity.Role;
import com.business.aiagent.security.RequirePermission;
import com.business.aiagent.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "Product management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {
    
    private final ProductService productService;
    
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
        Page<ProductResponse> products = productService.getAllProducts(
            page, size, sortBy, sortDir, categoryId, keyword, minPrice, maxPrice
        );
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get product details", description = "Public API")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }
    
    @PostMapping
    @Operation(summary = "Create new product")
    @RequirePermission(Role.Permission.PRODUCT_CREATE)
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest request,
            Authentication authentication) {
        ProductResponse product = productService.createProduct(request, authentication.getName());
        return ResponseEntity.ok(product);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update product")
    @RequirePermission(Role.Permission.PRODUCT_UPDATE)
    public ResponseEntity<ProductResponse> updateProduct(
        @PathVariable Long id,
        @Valid @RequestBody ProductRequest request,
        Authentication authentication
    ) {
        ProductResponse product = productService.updateProduct(id, request, authentication.getName());
        return ResponseEntity.ok(product);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    @RequirePermission(Role.Permission.PRODUCT_DELETE)
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long id,
            Authentication authentication) {
        productService.deleteProduct(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/my-products")
    @Operation(summary = "Get my products", description = "BUSINESS role - View own products")
    @RequirePermission(Role.Permission.PRODUCT_READ)
    public ResponseEntity<Page<ProductResponse>> getMyProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Authentication authentication
    ) {
        Page<ProductResponse> products = productService.getMyProducts(authentication.getName(), page, size);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/top-selling")
    @Operation(summary = "Top selling products", description = "Public API")
    public ResponseEntity<List<ProductResponse>> getTopSellingProducts(@RequestParam(defaultValue = "10") int limit) {
        List<ProductResponse> products = productService.getTopSellingProducts(limit);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/featured")
    @Operation(summary = "Featured products", description = "Public API")
    public ResponseEntity<Page<ProductResponse>> getFeaturedProducts(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Page<ProductResponse> products = productService.getFeaturedProducts(page, size);
        return ResponseEntity.ok(products);
    }
    
    @GetMapping("/low-stock")
    @Operation(summary = "Low stock products", description = "BUSINESS/ADMIN - Products with low inventory")
    @RequirePermission(Role.Permission.PRODUCT_READ)
    public ResponseEntity<List<ProductResponse>> getLowStockProducts(@RequestParam(defaultValue = "10") int threshold) {
        List<ProductResponse> products = productService.getLowStockProducts(threshold);
        return ResponseEntity.ok(products);
    }
}
