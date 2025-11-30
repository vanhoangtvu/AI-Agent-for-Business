package com.business.springservice.controller;

import com.business.springservice.dto.CategoryDTO;
import com.business.springservice.dto.ProductDTO;
import com.business.springservice.service.CategoryService;
import com.business.springservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
@RequiredArgsConstructor
@Tag(name = "Shop", description = "Public APIs for browsing products (no authentication required)")
public class ShopController {
    
    private final ProductService productService;
    private final CategoryService categoryService;
    
    @GetMapping("/products")
    @Operation(summary = "Get all active products", description = "Retrieve all active products. No authentication required.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list")
    })
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllActiveProducts());
    }
    
    @GetMapping("/products/{id}")
    @Operation(summary = "Get product by ID", description = "Retrieve product details by ID. No authentication required.")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    
    @GetMapping("/categories")
    @Operation(summary = "Get all active categories", description = "Retrieve all active categories. No authentication required.")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllActiveCategories());
    }
    
    @GetMapping("/categories/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieve category details by ID. No authentication required.")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    
    @GetMapping("/products/category/{categoryId}")
    @Operation(summary = "Get active products by category", description = "Retrieve all active products in a specific active category. No authentication required.")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(productService.getActiveProductsByCategory(categoryId));
    }
    
    @GetMapping("/products/search")
    @Operation(summary = "Search active products", description = "Search active products by name. No authentication required.")
    public ResponseEntity<List<ProductDTO>> searchProducts(@RequestParam String keyword) {
        return ResponseEntity.ok(productService.searchActiveProducts(keyword));
    }
}
