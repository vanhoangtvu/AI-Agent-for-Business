package com.business.aiagent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
    
    private Long productId;
    
    private Integer rating; // 1-5
    
    private String title;
    
    private String comment;
    
    private List<String> images;
}
