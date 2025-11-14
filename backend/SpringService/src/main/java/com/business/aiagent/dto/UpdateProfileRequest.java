package com.business.aiagent.dto;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String email;
    private String fullName;
    private String phoneNumber;
}
