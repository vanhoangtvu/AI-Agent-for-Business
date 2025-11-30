package com.business.springservice.controller;

import com.business.springservice.dto.UserDTO;
import com.business.springservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "APIs for managing users (ADMIN and BUSINESS only)")
public class UserController {
    
    private final UserService userService;
    
    @GetMapping
    @Operation(
        summary = "Get all users", 
        description = "Retrieve a list of all users. Requires ADMIN or BUSINESS role."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
        @ApiResponse(responseCode = "401", description = "Missing or invalid JWT token"),
        @ApiResponse(responseCode = "403", description = "Access denied. Only ADMIN and BUSINESS roles allowed")
    })
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
    @DeleteMapping("/{id}")
    @Operation(
        summary = "Delete user", 
        description = "Delete a user by their ID. Requires ADMIN or BUSINESS role."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "403", description = "Access denied. Only ADMIN and BUSINESS roles allowed")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID of user to delete") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
