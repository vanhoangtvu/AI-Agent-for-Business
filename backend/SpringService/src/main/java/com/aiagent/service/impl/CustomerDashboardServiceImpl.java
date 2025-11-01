package com.aiagent.service.impl;

import com.aiagent.model.dto.CustomerDashboardResponse;
import com.aiagent.model.entity.Conversation;
import com.aiagent.model.entity.Document;
import com.aiagent.model.entity.User;
import com.aiagent.repository.ConversationRepository;
import com.aiagent.repository.DocumentRepository;
import com.aiagent.repository.MessageRepository;
import com.aiagent.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Customer Dashboard Service Implementation
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class CustomerDashboardServiceImpl {

    private final UserService userService;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final DocumentRepository documentRepository;

    public CustomerDashboardResponse getCustomerDashboard() {
        User currentUser = userService.getCurrentUser();
        log.info("Fetching customer dashboard for: {}", currentUser.getEmail());

        // Customer info
        CustomerDashboardResponse.CustomerInfo customerInfo = CustomerDashboardResponse.CustomerInfo.builder()
                .fullName(currentUser.getFullName())
                .email(currentUser.getEmail())
                .phoneNumber(currentUser.getPhoneNumber())
                .joinedDate(currentUser.getCreatedAt())
                .status(currentUser.getActive() ? "Active" : "Inactive")
                .build();

        // Conversations statistics
        List<Conversation> conversations = conversationRepository.findByUserId(currentUser.getId());
        long totalConversations = conversations.size();

        // Messages count
        long totalMessages = conversations.stream()
                .flatMap(c -> c.getMessages().stream())
                .filter(m -> m.getRole() == Conversation.Message.MessageRole.USER)
                .count();

        // Recent conversations (last 5)
        List<CustomerDashboardResponse.RecentConversation> recentConversations = conversations.stream()
                .sorted((c1, c2) -> {
                    if (c1.getLastMessageAt() == null) return 1;
                    if (c2.getLastMessageAt() == null) return -1;
                    return c2.getLastMessageAt().compareTo(c1.getLastMessageAt());
                })
                .limit(5)
                .map(c -> CustomerDashboardResponse.RecentConversation.builder()
                        .id(c.getId())
                        .title(c.getTitle())
                        .lastMessageAt(c.getLastMessageAt())
                        .messageCount(c.getMessages() != null ? c.getMessages().size() : 0)
                        .active(c.getActive())
                        .build())
                .collect(Collectors.toList());

        // Shared documents (all documents for now - in real app, filter by shared status)
        List<CustomerDashboardResponse.SharedDocument> sharedDocuments = documentRepository.findAll().stream()
                .limit(10)
                .map(d -> CustomerDashboardResponse.SharedDocument.builder()
                        .id(d.getId())
                        .title(d.getTitle())
                        .category(d.getCategory())
                        .fileType(d.getFileType())
                        .sharedAt(d.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        // Available actions
        List<String> availableActions = Arrays.asList(
                "Start New Conversation",
                "View Chat History",
                "Browse Documents",
                "Update Profile",
                "Contact Support"
        );

        // Support info
        CustomerDashboardResponse.SupportInfo supportInfo = CustomerDashboardResponse.SupportInfo.builder()
                .supportEmail("support@aiagent.com")
                .supportPhone("1900-xxxx")
                .businessHours("Mon-Fri: 8AM - 6PM")
                .chatAvailable(true)
                .build();

        return CustomerDashboardResponse.builder()
                .customerInfo(customerInfo)
                .totalConversations(totalConversations)
                .totalMessages(totalMessages)
                .recentConversations(recentConversations)
                .sharedDocuments(sharedDocuments)
                .availableActions(availableActions)
                .supportInfo(supportInfo)
                .build();
    }

}

