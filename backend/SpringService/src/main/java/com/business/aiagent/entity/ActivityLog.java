package com.business.aiagent.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private ActivityType activityType;
    
    @Column(length = 500)
    private String description;
    
    @Column(length = 50)
    private String ipAddress;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    public enum ActivityType {
        LOGIN,
        LOGOUT,
        REGISTER,
        UPLOAD_DOCUMENT,
        DELETE_DOCUMENT,
        SEND_MESSAGE,
        CREATE_CONVERSATION,
        GENERATE_REPORT,
        VIEW_REPORT
    }
}
