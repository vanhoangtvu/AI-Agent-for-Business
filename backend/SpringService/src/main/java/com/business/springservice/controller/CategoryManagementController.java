package com.business.springservice.controller;

import com.business.springservice.dto.CategoryDTO;
import com.business.springservice.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Tag(name = "Category Management", description = "APIs for managing categories (ADMIN and BUSINESS only)")
public class CategoryManagementController {
    
    private final CategoryService categoryService;
    
    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieve list of all categories. Requires ADMIN or BUSINESS role.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Access denied")
    })
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Retrieve a specific category by ID")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    
    @PostMapping
    @Operation(summary = "Create new category", description = "Create a new category. Requires ADMIN or BUSINESS role.")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
    }
    
    @PatchMapping("/{id}")
    @Operation(summary = "Update category", description = "Update an existing category")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category", description = "Delete a category by ID")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update category status", description = "Update category status (ACTIVE/INACTIVE). Only ADMIN or BUSINESS role.")
    public ResponseEntity<CategoryDTO> updateCategoryStatus(
            @PathVariable Long id,
            @RequestParam @Parameter(description = "Status: ACTIVE or INACTIVE") String status) {
        return ResponseEntity.ok(categoryService.updateCategoryStatus(id, status));
    }
}
