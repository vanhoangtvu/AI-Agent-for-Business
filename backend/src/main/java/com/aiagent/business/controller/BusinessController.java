package com.aiagent.business.controller;

import com.aiagent.business.dto.response.ApiResponse;
import com.aiagent.business.model.Business;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/business")
@Tag(name = "Business Management", description = "Quản lý doanh nghiệp (BUSINESS role only)")
@SecurityRequirement(name = "bearerAuth")
public class BusinessController {

    @GetMapping("/profile")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Lấy thông tin doanh nghiệp của mình", description = "Business xem thông tin doanh nghiệp của mình")
    public ResponseEntity<ApiResponse<Business>> getProfile() {
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin thành công", null));
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Cập nhật thông tin doanh nghiệp", description = "Cập nhật thông tin doanh nghiệp của mình")
    public ResponseEntity<ApiResponse<Business>> updateProfile(@RequestBody Business business) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", null));
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Thống kê tổng quan doanh nghiệp", description = "Doanh thu, đơn hàng, khách hàng, tin nhắn")
    public ResponseEntity<ApiResponse<?>> getStats() {
        return ResponseEntity.ok(ApiResponse.success("Lấy thống kê thành công", null));
    }

    // Customer Management
    @GetMapping("/customers")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Danh sách khách hàng", description = "Lấy danh sách khách hàng của doanh nghiệp")
    public ResponseEntity<ApiResponse<?>> getCustomers(
            @Parameter(description = "Trang (bắt đầu từ 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng mỗi trang") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Tìm kiếm theo tên, email, SĐT") @RequestParam(required = false) String search,
            @Parameter(description = "Phân loại: VIP, REGULAR, POTENTIAL") @RequestParam(required = false) String segment
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách thành công", null));
    }

    @GetMapping("/customers/{id}")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Chi tiết khách hàng", description = "Xem chi tiết khách hàng và lịch sử mua hàng")
    public ResponseEntity<ApiResponse<?>> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin thành công", null));
    }

    @PostMapping("/customers")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Thêm khách hàng mới", description = "Tạo khách hàng mới cho doanh nghiệp")
    public ResponseEntity<ApiResponse<?>> createCustomer(@RequestBody Object customerDto) {
        return ResponseEntity.ok(ApiResponse.success("Tạo khách hàng thành công", null));
    }

    @PutMapping("/customers/{id}")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Cập nhật khách hàng", description = "Cập nhật thông tin khách hàng")
    public ResponseEntity<ApiResponse<?>> updateCustomer(@PathVariable Long id, @RequestBody Object customerDto) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", null));
    }

    @DeleteMapping("/customers/{id}")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Xóa khách hàng", description = "Xóa khách hàng (soft delete)")
    public ResponseEntity<ApiResponse<?>> deleteCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Xóa khách hàng thành công", null));
    }

    // Product Management
    @GetMapping("/products")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Danh sách sản phẩm", description = "Lấy danh sách sản phẩm của doanh nghiệp")
    public ResponseEntity<ApiResponse<?>> getProducts(
            @Parameter(description = "Trang") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Danh mục") @RequestParam(required = false) String category,
            @Parameter(description = "Trạng thái: ACTIVE, INACTIVE, OUT_OF_STOCK") @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách thành công", null));
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Chi tiết sản phẩm", description = "Xem chi tiết sản phẩm")
    public ResponseEntity<ApiResponse<?>> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin thành công", null));
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Thêm sản phẩm mới", description = "Tạo sản phẩm mới")
    public ResponseEntity<ApiResponse<?>> createProduct(@RequestBody Object productDto) {
        return ResponseEntity.ok(ApiResponse.success("Tạo sản phẩm thành công", null));
    }

    @PutMapping("/products/{id}")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Cập nhật sản phẩm", description = "Cập nhật thông tin sản phẩm")
    public ResponseEntity<ApiResponse<?>> updateProduct(@PathVariable Long id, @RequestBody Object productDto) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", null));
    }

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Xóa sản phẩm", description = "Xóa sản phẩm (soft delete)")
    public ResponseEntity<ApiResponse<?>> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Xóa sản phẩm thành công", null));
    }

    // Order Management
    @GetMapping("/orders")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Danh sách đơn hàng", description = "Lấy danh sách đơn hàng của doanh nghiệp")
    public ResponseEntity<ApiResponse<?>> getOrders(
            @Parameter(description = "Trang") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng") @RequestParam(defaultValue = "20") int size,
            @Parameter(description = "Trạng thái: PENDING, PROCESSING, COMPLETED, CANCELLED") @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách thành công", null));
    }

    @GetMapping("/orders/{id}")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Chi tiết đơn hàng", description = "Xem chi tiết đơn hàng")
    public ResponseEntity<ApiResponse<?>> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin thành công", null));
    }

    @PostMapping("/orders")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Tạo đơn hàng mới", description = "Tạo đơn hàng cho khách hàng")
    public ResponseEntity<ApiResponse<?>> createOrder(@RequestBody Object orderDto) {
        return ResponseEntity.ok(ApiResponse.success("Tạo đơn hàng thành công", null));
    }

    @PutMapping("/orders/{id}")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Cập nhật đơn hàng", description = "Cập nhật trạng thái đơn hàng")
    public ResponseEntity<ApiResponse<?>> updateOrder(@PathVariable Long id, @RequestBody Object orderDto) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", null));
    }

    // Conversations
    @GetMapping("/conversations")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Danh sách hội thoại", description = "Tất cả hội thoại của doanh nghiệp (Website, Zalo OA, Zalo Personal)")
    public ResponseEntity<ApiResponse<?>> getConversations(
            @Parameter(description = "Kênh: WEBSITE, ZALO_OA, ZALO_PERSONAL") @RequestParam(required = false) String channel,
            @Parameter(description = "Trạng thái: ACTIVE, RESOLVED, ARCHIVED") @RequestParam(required = false) String status
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách thành công", null));
    }

    @GetMapping("/conversations/{id}/messages")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Lịch sử tin nhắn", description = "Xem tất cả tin nhắn trong hội thoại")
    public ResponseEntity<ApiResponse<?>> getMessages(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy tin nhắn thành công", null));
    }

    @PostMapping("/conversations/{id}/messages")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Gửi tin nhắn", description = "Doanh nghiệp trả lời khách hàng")
    public ResponseEntity<ApiResponse<?>> sendMessage(@PathVariable Long id, @RequestBody Object messageDto) {
        return ResponseEntity.ok(ApiResponse.success("Gửi tin nhắn thành công", null));
    }
}

