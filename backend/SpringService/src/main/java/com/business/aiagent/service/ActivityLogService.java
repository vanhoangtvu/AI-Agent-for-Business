package com.business.aiagent.service;

import com.business.aiagent.dto.ActivityLogResponse;
import com.business.aiagent.entity.ActivityLog;
import com.business.aiagent.entity.ActivityLog.ActivityType;
import com.business.aiagent.entity.User;
import com.business.aiagent.repository.ActivityLogRepository;
import com.business.aiagent.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ActivityLogService {
    
    private final ActivityLogRepository activityLogRepository;
    private final UserRepository userRepository;
    
    @Transactional
    public void logActivity(String username, ActivityType activityType, String description, String ipAddress) {
        User user = userRepository.findByUsername(username).orElse(null);
        
        ActivityLog log = new ActivityLog();
        log.setUser(user);
        log.setActivityType(activityType);
        log.setDescription(description);
        log.setIpAddress(ipAddress);
        
        activityLogRepository.save(log);
    }
    
    public Page<ActivityLogResponse> getUserActivities(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return activityLogRepository.findByUserOrderByCreatedAtDesc(user, pageable)
                .map(this::mapToResponse);
    }
    
    public Page<ActivityLogResponse> getAllActivities(Pageable pageable) {
        return activityLogRepository.findAllByOrderByCreatedAtDesc(pageable)
                .map(this::mapToResponse);
    }
    
    private ActivityLogResponse mapToResponse(ActivityLog log) {
        return new ActivityLogResponse(
                log.getId(),
                log.getActivityType(),
                log.getDescription(),
                log.getIpAddress(),
                log.getCreatedAt()
        );
    }
}
