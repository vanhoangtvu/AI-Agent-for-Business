package com.business.springservice.controller;

import com.business.springservice.dto.ProductCreateRequest;
import com.business.springservice.dto.ProductDTO;
import com.business.springservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
@Tag(name = "Product Management", description = "APIs for managing products (ADMIN and BUSINESS only)")
public class ProductManagementController {
    
    private final ProductService productService;
    
    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieve list of all products. Requires ADMIN or BUSINESS role.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve a specific product by ID")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    
    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get products by category", description = "Retrieve all products in a specific category")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
    }
    
    @GetMapping("/seller/{sellerId}")
    @Operation(summary = "Get products by seller", description = "Retrieve all products from a specific seller")
    public ResponseEntity<List<ProductDTO>> getProductsBySeller(@PathVariable Long sellerId) {
        return ResponseEntity.ok(productService.getProductsBySeller(sellerId));
    }
    
    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Search products by name")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchProducts(keyword));
    }
    
    @PostMapping
    @Operation(summary = "Create new product", description = "Create a new product. Seller will be set to current user.")
    public ResponseEntity<ProductDTO> createProduct(
            HttpServletRequest request,
            @RequestBody ProductCreateRequest productRequest) {
        Long sellerId = (Long) request.getAttribute("userId");
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(productRequest, sellerId));
    }
    
    @PatchMapping("/{id}")
    @Operation(summary = "Update product", description = "Update an existing product")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductCreateRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Delete a product by ID")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update product status", description = "Update product status (ACTIVE/INACTIVE). Only ADMIN or BUSINESS role.")
    public ResponseEntity<ProductDTO> updateProductStatus(
            @PathVariable Long id,
            @RequestParam @Parameter(description = "Status: ACTIVE or INACTIVE") String status) {
        return ResponseEntity.ok(productService.updateProductStatus(id, status));
    }
}
