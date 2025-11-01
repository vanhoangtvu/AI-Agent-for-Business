package com.aiagent.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Document Request DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private String category;

    private Set<String> tags;

}

