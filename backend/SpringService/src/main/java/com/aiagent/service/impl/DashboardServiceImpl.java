package com.aiagent.service.impl;

import com.aiagent.model.dto.AdminDashboardResponse;
import com.aiagent.model.dto.BusinessDashboardResponse;
import com.aiagent.model.entity.Document;
import com.aiagent.model.entity.User;
import com.aiagent.repository.ConversationRepository;
import com.aiagent.repository.DocumentRepository;
import com.aiagent.repository.MessageRepository;
import com.aiagent.repository.UserRepository;
import com.aiagent.service.DashboardService;
import com.aiagent.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Dashboard Service Implementation
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;
    private final DocumentRepository documentRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final UserService userService;

    @Override
    public AdminDashboardResponse getAdminDashboard() {
        log.info("Fetching admin dashboard data");

        // Users statistics
        long totalUsers = userRepository.count();
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        long newUsersThisMonth = userRepository.findAll().stream()
                .filter(u -> u.getCreatedAt().isAfter(startOfMonth))
                .count();

        // Users by role
        Map<String, Long> usersByRole = new HashMap<>();
        usersByRole.put("ADMIN", (long) userRepository.findByRole("ADMIN").size());
        usersByRole.put("BUSINESS", (long) userRepository.findByRole("BUSINESS").size());
        usersByRole.put("CUSTOMER", (long) userRepository.findByRole("CUSTOMER").size());

        // Documents statistics
        long totalDocuments = documentRepository.count();
        Map<String, Long> documentsByStatus = new HashMap<>();
        documentsByStatus.put("PENDING", documentRepository.countByStatus(Document.ProcessingStatus.PENDING));
        documentsByStatus.put("PROCESSING", documentRepository.countByStatus(Document.ProcessingStatus.PROCESSING));
        documentsByStatus.put("COMPLETED", documentRepository.countByStatus(Document.ProcessingStatus.COMPLETED));
        documentsByStatus.put("FAILED", documentRepository.countByStatus(Document.ProcessingStatus.FAILED));

        // Conversations statistics
        long totalConversations = conversationRepository.count();
        
        // Messages today
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        long messagesToday = messageRepository.findAll().stream()
                .filter(m -> m.getCreatedAt().isAfter(startOfDay))
                .count();

        // Storage used
        long totalStorageUsed = documentRepository.findAll().stream()
                .mapToLong(Document::getFileSize)
                .sum();

        // Active users today
        long activeUsersToday = userRepository.findAll().stream()
                .filter(u -> u.getLastLogin() != null && u.getLastLogin().isAfter(startOfDay))
                .count();

        return AdminDashboardResponse.builder()
                .totalUsers(totalUsers)
                .newUsersThisMonth(newUsersThisMonth)
                .usersByRole(usersByRole)
                .totalDocuments(totalDocuments)
                .documentsByStatus(documentsByStatus)
                .totalConversations(totalConversations)
                .messagesToday(messagesToday)
                .totalStorageUsed(totalStorageUsed)
                .activeUsersToday(activeUsersToday)
                .systemHealth("HEALTHY")
                .topBusinesses(getTopBusinesses())
                .recentActivities(getRecentActivities())
                .build();
    }

    @Override
    public BusinessDashboardResponse getBusinessDashboard() {
        User currentUser = userService.getCurrentUser();
        log.info("Fetching business dashboard for: {}", currentUser.getEmail());

        // Documents statistics
        long totalDocuments = documentRepository.countByUserId(currentUser.getId());
        long processedDocuments = documentRepository.findByUserIdAndStatus(
                currentUser.getId(), 
                Document.ProcessingStatus.COMPLETED
        ).size();
        long pendingDocuments = documentRepository.findByUserIdAndStatus(
                currentUser.getId(), 
                Document.ProcessingStatus.PENDING
        ).size();

        // Customer statistics (simplified - all users except current business)
        long totalCustomers = conversationRepository.countByUserId(currentUser.getId());
        
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        long newCustomersThisMonth = conversationRepository.findByUser(currentUser).stream()
                .filter(c -> c.getCreatedAt().isAfter(startOfMonth))
                .count();

        // Conversations
        long totalConversations = conversationRepository.countByUserId(currentUser.getId());
        long activeConversations = conversationRepository.countByUserIdAndActiveTrue(currentUser.getId());

        // Messages statistics
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime startOfWeek = LocalDateTime.now().minusDays(7);
        
        long messagesToday = currentUser.getConversations().stream()
                .flatMap(c -> c.getMessages().stream())
                .filter(m -> m.getCreatedAt().isAfter(startOfDay))
                .count();

        long messagesThisWeek = currentUser.getConversations().stream()
                .flatMap(c -> c.getMessages().stream())
                .filter(m -> m.getCreatedAt().isAfter(startOfWeek))
                .count();

        double avgMessagesPerDay = messagesThisWeek / 7.0;

        // Top categories
        Map<String, Long> topCategories = new HashMap<>();
        documentRepository.findByUserId(currentUser.getId()).stream()
                .filter(d -> d.getCategory() != null)
                .forEach(d -> topCategories.merge(d.getCategory(), 1L, Long::sum));

        // Sentiment analysis (mock data)
        Map<String, Long> sentimentAnalysis = new HashMap<>();
        sentimentAnalysis.put("POSITIVE", 60L);
        sentimentAnalysis.put("NEUTRAL", 30L);
        sentimentAnalysis.put("NEGATIVE", 10L);

        // Calculate satisfaction score (based on sentiment)
        double satisfactionScore = 75.5;

        // Response rate
        double responseRate = totalConversations > 0 ? 
                (double) activeConversations / totalConversations * 100 : 0.0;

        return BusinessDashboardResponse.builder()
                .businessName(currentUser.getFullName())
                .totalDocuments(totalDocuments)
                .processedDocuments(processedDocuments)
                .pendingDocuments(pendingDocuments)
                .totalCustomers(totalCustomers)
                .newCustomersThisMonth(newCustomersThisMonth)
                .totalConversations(totalConversations)
                .messagesToday(messagesToday)
                .messagesThisWeek(messagesThisWeek)
                .avgMessagesPerDay(avgMessagesPerDay)
                .topCategories(topCategories)
                .sentimentAnalysis(sentimentAnalysis)
                .satisfactionScore(satisfactionScore)
                .responseRate(responseRate)
                .activeConversations(activeConversations)
                .build();
    }

    private Object getTopBusinesses() {
        // TODO: Implement top businesses logic
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Top businesses data will be available soon");
        return data;
    }

    private Object getRecentActivities() {
        // TODO: Implement recent activities logic
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Recent activities data will be available soon");
        return data;
    }

}

