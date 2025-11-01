package com.aiagent.controller.api;

import com.aiagent.model.dto.StrategicAnalysisRequest;
import com.aiagent.model.dto.StrategicAnalysisResponse;
import com.aiagent.service.StrategicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Strategic Analysis Controller
 * Phân tích và đề xuất chiến lược kinh doanh
 * 
 * @author Nguyễn Văn Hoàng
 */
@RestController
@RequestMapping("/api/strategic")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8100"})
@Tag(name = "Strategic Analysis", description = "API phân tích chiến lược kinh doanh")
@SecurityRequirement(name = "Bearer Authentication")
@PreAuthorize("hasAnyRole('ADMIN', 'BUSINESS')")
public class StrategicController {

    private final StrategicService strategicService;

    @Operation(
            summary = "Phân tích chiến lược",
            description = """
                Phân tích chiến lược kinh doanh với AI.
                
                Các loại phân tích:
                - SWOT: Phân tích điểm mạnh, điểm yếu, cơ hội, thách thức
                - MARKET_OPPORTUNITY: Phân tích cơ hội thị trường
                - RISK_ASSESSMENT: Đánh giá rủi ro
                - COMPETITIVE_ANALYSIS: Phân tích cạnh tranh
                - GROWTH_STRATEGY: Chiến lược tăng trưởng
                - FULL_STRATEGIC: Phân tích toàn diện
                """
    )
    @PostMapping("/analyze")
    public ResponseEntity<StrategicAnalysisResponse> analyzeStrategy(
            @Valid @RequestBody StrategicAnalysisRequest request) {
        
        StrategicAnalysisResponse response = strategicService.analyzeStrategy(request);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Lấy báo cáo phân tích",
            description = "Lấy báo cáo phân tích đã tạo trước đó theo ID"
    )
    @GetMapping("/reports/{id}")
    public ResponseEntity<StrategicAnalysisResponse> getReportById(@PathVariable Long id) {
        StrategicAnalysisResponse response = strategicService.getReportById(id);
        return ResponseEntity.ok(response);
    }

}

