package com.business.aiagent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    
    private Long id;
    
    private Long productId;
    
    private Long userId;
    
    private String userName;
    
    private String userAvatar;
    
    private Integer rating;
    
    private String title;
    
    private String comment;
    
    private List<String> images;
    
    private Boolean verifiedPurchase;
    
    private Integer helpful;
    
    private Integer unhelpful;
    
    private Boolean isEdited;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}
