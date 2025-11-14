package com.business.aiagent.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "strategic_reports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StrategicReport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false, length = 200)
    private String title;
    
    @Column(length = 50)
    private String reportType;
    
    @Column(columnDefinition = "TEXT")
    private String inputMetrics;  // JSON string
    
    @Column(columnDefinition = "TEXT")
    private String swotAnalysis;  // JSON string
    
    @Column(columnDefinition = "TEXT")
    private String recommendations;  // JSON string
    
    @Column(columnDefinition = "TEXT")
    private String marketInsights;
    
    @Column(columnDefinition = "TEXT")
    private String riskAssessment;
    
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
