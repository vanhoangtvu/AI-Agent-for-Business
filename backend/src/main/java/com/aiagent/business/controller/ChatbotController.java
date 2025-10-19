package com.aiagent.business.controller;

import com.aiagent.business.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/chatbot")
@Tag(name = "Chatbot & AI", description = "Cấu hình và quản lý AI Chatbot")
@SecurityRequirement(name = "bearerAuth")
public class ChatbotController {

    @PostMapping("/config")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Cấu hình chatbot", description = "Business cấu hình AI chatbot (tone, language, behavior)")
    public ResponseEntity<ApiResponse<?>> configureChatbot(@RequestBody Object chatbotConfigDto) {
        return ResponseEntity.ok(ApiResponse.success("Cấu hình chatbot thành công", null));
    }

    @GetMapping("/config")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Lấy cấu hình chatbot", description = "Xem cấu hình hiện tại của chatbot")
    public ResponseEntity<ApiResponse<?>> getChatbotConfig() {
        return ResponseEntity.ok(ApiResponse.success("Lấy cấu hình thành công", null));
    }

    @PostMapping(value = "/documents/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Upload tài liệu RAG", 
               description = "Upload PDF, DOCX, TXT để chatbot học. Hệ thống sẽ tự động chunking và tạo embeddings.",
               requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                   content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                   schema = @Schema(type = "object", implementation = Object.class))
               ))
    public ResponseEntity<ApiResponse<?>> uploadDocument(
            @Parameter(description = "File tài liệu (PDF, DOCX, TXT)", required = true)
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Loại tài liệu: PRODUCT_INFO, FAQ, POLICY, MARKETING")
            @RequestParam(required = false) String documentType,
            @Parameter(description = "Mô tả ngắn về tài liệu")
            @RequestParam(required = false) String description
    ) {
        return ResponseEntity.ok(ApiResponse.success("Upload tài liệu thành công", null));
    }

    @GetMapping("/documents")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Danh sách tài liệu RAG", description = "Tất cả tài liệu đã upload")
    public ResponseEntity<ApiResponse<?>> getDocuments(
            @Parameter(description = "Loại tài liệu") @RequestParam(required = false) String documentType,
            @Parameter(description = "Trang") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Số lượng") @RequestParam(defaultValue = "20") int size
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách thành công", null));
    }

    @DeleteMapping("/documents/{id}")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Xóa tài liệu RAG", description = "Xóa tài liệu và tất cả embeddings liên quan")
    public ResponseEntity<ApiResponse<?>> deleteDocument(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Xóa tài liệu thành công", null));
    }

    @PostMapping("/train")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Training chatbot", description = "Trigger re-indexing và training lại chatbot với tài liệu mới")
    public ResponseEntity<ApiResponse<?>> trainChatbot() {
        return ResponseEntity.ok(ApiResponse.success("Bắt đầu training", null));
    }

    @GetMapping("/training-status")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Trạng thái training", description = "Kiểm tra tiến độ training")
    public ResponseEntity<ApiResponse<?>> getTrainingStatus() {
        return ResponseEntity.ok(ApiResponse.success("Lấy trạng thái thành công", null));
    }

    @PostMapping("/test")
    @PreAuthorize("hasRole('BUSINESS')")
    @Operation(summary = "Test chatbot", description = "Business test chatbot với câu hỏi mẫu")
    public ResponseEntity<ApiResponse<?>> testChatbot(@RequestBody Object testMessageDto) {
        return ResponseEntity.ok(ApiResponse.success("Test thành công", null));
    }

    // Public endpoint - Website Widget Chat
    @PostMapping("/chat")
    @Operation(summary = "Chat với chatbot (Public)", 
               description = "Endpoint cho Website Widget. Không cần authentication. Khách hàng chat trực tiếp.")
    public ResponseEntity<ApiResponse<?>> chat(
            @Parameter(description = "Business ID") @RequestParam Long businessId,
            @Parameter(description = "Session ID (UUID)") @RequestParam String sessionId,
            @RequestBody Object chatMessageDto
    ) {
        return ResponseEntity.ok(ApiResponse.success("Trả lời thành công", null));
    }

    @GetMapping("/widget-config")
    @Operation(summary = "Cấu hình Widget (Public)", 
               description = "Lấy cấu hình chatbot widget cho website (màu sắc, logo, greeting message)")
    public ResponseEntity<ApiResponse<?>> getWidgetConfig(
            @Parameter(description = "Business ID") @RequestParam Long businessId
    ) {
        return ResponseEntity.ok(ApiResponse.success("Lấy cấu hình thành công", null));
    }
}

