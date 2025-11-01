package com.aiagent.service;

import com.aiagent.model.dto.AdminDashboardResponse;
import com.aiagent.model.dto.BusinessDashboardResponse;

/**
 * Dashboard Service Interface
 */
public interface DashboardService {
    
    /**
     * Lấy dashboard data cho Admin
     */
    AdminDashboardResponse getAdminDashboard();
    
    /**
     * Lấy dashboard data cho Business
     */
    BusinessDashboardResponse getBusinessDashboard();
    
}

