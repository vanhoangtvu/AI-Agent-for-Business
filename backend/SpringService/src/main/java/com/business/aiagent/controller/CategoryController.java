package com.business.aiagent.controller;

import com.business.aiagent.dto.CategoryRequest;
import com.business.aiagent.dto.CategoryResponse;
import com.business.aiagent.entity.Role;
import com.business.aiagent.security.RequirePermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

/**
 * Category Management Controller
 * ADMIN: Manage all categories (create, update, delete)
 * BUSINESS, CUSTOMER: View only
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Category Management", description = "Category management APIs")
@SecurityRequirement(name = "Bearer Authentication")
public class CategoryController {
    
    @GetMapping
    @Operation(summary = "Get all categories", description = "Public API - Flat category list")
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        // TODO: Implement service call
        return ResponseEntity.ok(List.of());
    }
    
    @GetMapping("/tree")
    @Operation(summary = "Get category tree", description = "Public API - Hierarchical parent-child category tree")
    public ResponseEntity<List<CategoryResponse>> getCategoryTree() {
        // TODO: Implement service call
        return ResponseEntity.ok(List.of());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get category details", description = "Public API - Includes product list")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        // TODO: Implement service call
        return ResponseEntity.ok(CategoryResponse.builder().build());
    }
    
    @GetMapping("/slug/{slug}")
    @Operation(summary = "Get category by slug", description = "Public API - SEO friendly URL")
    public ResponseEntity<CategoryResponse> getCategoryBySlug(@PathVariable String slug) {
        // TODO: Implement service call
        return ResponseEntity.ok(CategoryResponse.builder().build());
    }
    
    @PostMapping
    @Operation(summary = "Create category", description = "ADMIN only")
    @RequirePermission(Role.Permission.CATEGORY_CREATE)
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        // TODO: Implement service call
        return ResponseEntity.ok(CategoryResponse.builder().build());
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Update category", description = "ADMIN only")
    @RequirePermission(Role.Permission.CATEGORY_UPDATE)
    public ResponseEntity<CategoryResponse> updateCategory(
        @PathVariable Long id,
        @Valid @RequestBody CategoryRequest request
    ) {
        // TODO: Implement service call
        return ResponseEntity.ok(CategoryResponse.builder().build());
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "ADMIN only")
    @RequirePermission(Role.Permission.CATEGORY_DELETE)
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        // TODO: Implement service call
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/parents")
    @Operation(summary = "Get parent categories", description = "Public API - Top level categories")
    public ResponseEntity<List<CategoryResponse>> getParentCategories() {
        // TODO: Implement service call
        return ResponseEntity.ok(List.of());
    }
    
    @GetMapping("/{id}/children")
    @Operation(summary = "Get child categories", description = "Public API - Sub-categories of a category")
    public ResponseEntity<List<CategoryResponse>> getChildCategories(@PathVariable Long id) {
        // TODO: Implement service call
        return ResponseEntity.ok(List.of());
    }
}
