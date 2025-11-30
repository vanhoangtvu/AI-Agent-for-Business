package com.business.springservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDocumentDTO {
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Document ID")
    private Long id;
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Business user ID")
    private Long businessId;
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Business username")
    private String businessUsername;
    
    @Schema(description = "File name", example = "business_license.pdf")
    private String fileName;
    
    @Schema(description = "File type", example = "application/pdf")
    private String fileType;
    
    @Schema(description = "File path/URL", example = "/uploads/documents/business_license.pdf")
    private String filePath;
    
    @Schema(description = "File size in bytes", example = "1048576")
    private Long fileSize;
    
    @Schema(description = "Document description", example = "Giấy phép kinh doanh")
    private String description;
    
    @Schema(accessMode = Schema.AccessMode.READ_ONLY, description = "Upload timestamp")
    private LocalDateTime uploadedAt;
}
