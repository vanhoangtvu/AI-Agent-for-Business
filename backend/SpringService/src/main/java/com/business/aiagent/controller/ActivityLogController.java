package com.business.aiagent.controller;

import com.business.aiagent.dto.ActivityLogResponse;
import com.business.aiagent.service.ActivityLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
@Tag(name = "📝 Activity Logs", description = "API theo dõi hoạt động người dùng")
public class ActivityLogController {
    
    private final ActivityLogService activityLogService;
    
    @GetMapping
    public ResponseEntity<Page<ActivityLogResponse>> getUserActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            Authentication authentication) {
        
        Page<ActivityLogResponse> activities = activityLogService.getUserActivities(
                authentication.getName(),
                PageRequest.of(page, size)
        );
        return ResponseEntity.ok(activities);
    }
    
    @GetMapping("/all")
    public ResponseEntity<Page<ActivityLogResponse>> getAllActivities(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        
        Page<ActivityLogResponse> activities = activityLogService.getAllActivities(
                PageRequest.of(page, size)
        );
        return ResponseEntity.ok(activities);
    }
}
