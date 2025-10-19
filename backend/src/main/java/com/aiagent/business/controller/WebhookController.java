package com.aiagent.business.controller;

import com.aiagent.business.dto.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@Tag(name = "Webhooks", description = "Webhooks từ các kênh bên ngoài (Zalo, Website)")
public class WebhookController {

    @PostMapping("/zalo/oa")
    @Operation(summary = "Zalo OA Webhook", 
               description = "Webhook nhận tin nhắn từ Zalo Official Account")
    public ResponseEntity<ApiResponse<?>> zaloOAWebhook(@RequestBody Object payload) {
        return ResponseEntity.ok(ApiResponse.success("Webhook processed"));
    }

    @PostMapping("/zalo/personal")
    @Operation(summary = "Zalo Personal Webhook", 
               description = "Webhook nhận tin nhắn từ Zalo Personal Account")
    public ResponseEntity<ApiResponse<?>> zaloPersonalWebhook(@RequestBody Object payload) {
        return ResponseEntity.ok(ApiResponse.success("Webhook processed"));
    }

    @PostMapping("/website/message")
    @Operation(summary = "Website Widget Message", 
               description = "Nhận tin nhắn từ Website Chat Widget")
    public ResponseEntity<ApiResponse<?>> websiteMessage(
            @Parameter(description = "Business ID") @RequestParam Long businessId,
            @Parameter(description = "Session ID") @RequestParam String sessionId,
            @RequestBody Object messageDto
    ) {
        return ResponseEntity.ok(ApiResponse.success("Message received"));
    }
}

