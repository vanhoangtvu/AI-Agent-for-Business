package com.aiagent.controller.api;

import com.aiagent.model.dto.CustomerDashboardResponse;
import com.aiagent.model.entity.Conversation;
import com.aiagent.model.entity.Document;
import com.aiagent.model.entity.User;
import com.aiagent.service.ChatService;
import com.aiagent.service.DocumentService;
import com.aiagent.service.UserService;
import com.aiagent.service.impl.CustomerDashboardServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Customer Controller
 * API dành cho khách hàng (CUSTOMER role)
 * 
 * @author Nguyễn Văn Hoàng
 */
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8100"})
@Tag(name = "Customer", description = "API dành cho khách hàng")
@SecurityRequirement(name = "Bearer Authentication")
public class CustomerController {

    private final CustomerDashboardServiceImpl customerDashboardService;
    private final UserService userService;
    private final ChatService chatService;
    private final DocumentService documentService;

    @Operation(
            summary = "Customer Dashboard",
            description = """
                Dashboard cho khách hàng - Trang chủ của customer
                
                Bao gồm:
                - Thông tin cá nhân (profile)
                - Số conversations & messages
                - Conversations gần đây (5 cuộc gần nhất)
                - Documents được chia sẻ (10 docs mới nhất)
                - Quick actions (Start chat, View history, etc.)
                - Support info (Email, phone, hours)
                """
    )
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CustomerDashboardResponse.class)
            )
    )
    @GetMapping("/dashboard")
    public ResponseEntity<CustomerDashboardResponse> getCustomerDashboard() {
        log.info("Customer dashboard request");
        CustomerDashboardResponse dashboard = customerDashboardService.getCustomerDashboard();
        return ResponseEntity.ok(dashboard);
    }

    @Operation(
            summary = "Lấy profile của khách hàng",
            description = "Lấy thông tin cá nhân của customer hiện tại"
    )
    @GetMapping("/profile")
    public ResponseEntity<User> getProfile() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Cập nhật profile",
            description = "Customer cập nhật thông tin cá nhân (fullName, phoneNumber)"
    )
    @PutMapping("/profile")
    public ResponseEntity<User> updateProfile(@RequestBody User userUpdate) {
        User currentUser = userService.getCurrentUser();
        User updated = userService.updateUser(currentUser.getId(), userUpdate);
        return ResponseEntity.ok(updated);
    }

    @Operation(
            summary = "Lấy conversations của customer",
            description = "Lấy tất cả conversations của customer hiện tại"
    )
    @GetMapping("/conversations")
    public ResponseEntity<List<Conversation>> getMyConversations() {
        List<Conversation> conversations = chatService.getUserConversations();
        return ResponseEntity.ok(conversations);
    }

    @Operation(
            summary = "Xem documents được chia sẻ",
            description = "Customer xem documents được business chia sẻ (read-only)"
    )
    @GetMapping("/documents")
    public ResponseEntity<List<Document>> getSharedDocuments() {
        // TODO: Filter only shared documents
        // For now, return all documents (customer can view but not modify)
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    @Operation(
            summary = "Xem chi tiết document",
            description = "Customer xem chi tiết document được chia sẻ"
    )
    @GetMapping("/documents/{id}")
    public ResponseEntity<Document> getDocumentDetail(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id);
        return ResponseEntity.ok(document);
    }

    @Operation(
            summary = "Contact Support",
            description = "Lấy thông tin liên hệ support"
    )
    @GetMapping("/support")
    public ResponseEntity<Map<String, String>> getSupportInfo() {
        Map<String, String> support = new HashMap<>();
        support.put("email", "support@aiagent.com");
        support.put("phone", "1900-xxxx");
        support.put("hours", "Mon-Fri: 8AM - 6PM");
        support.put("chatAvailable", "true");
        return ResponseEntity.ok(support);
    }

    @Operation(
            summary = "Quick Actions",
            description = "Lấy danh sách quick actions khả dụng cho customer"
    )
    @GetMapping("/actions")
    public ResponseEntity<Map<String, Object>> getQuickActions() {
        Map<String, Object> actions = new HashMap<>();
        actions.put("actions", List.of(
                Map.of("id", "new-chat", "label", "Start New Conversation", "icon", "chat"),
                Map.of("id", "view-history", "label", "View Chat History", "icon", "history"),
                Map.of("id", "browse-docs", "label", "Browse Documents", "icon", "folder"),
                Map.of("id", "update-profile", "label", "Update Profile", "icon", "user"),
                Map.of("id", "contact-support", "label", "Contact Support", "icon", "help")
        ));
        return ResponseEntity.ok(actions);
    }

}

