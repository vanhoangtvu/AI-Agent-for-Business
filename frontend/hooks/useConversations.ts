import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import api from '@/lib/api';
import { API_CONFIG } from '@/lib/config';
import { ConversationResponse, MessageResponse, ChatRequest, PageResponse } from '@/types';

export const useConversations = (page: number = 0, size: number = 10) => {
  return useQuery<PageResponse<ConversationResponse>>({
    queryKey: ['conversations', page, size],
    queryFn: async () => {
      const response = await api.get(`${API_CONFIG.ENDPOINTS.CONVERSATIONS}?page=${page}&size=${size}`);
      return response.data;
    },
  });
};

export const useConversation = (id: number) => {
  return useQuery<ConversationResponse>({
    queryKey: ['conversation', id],
    queryFn: async () => {
      const response = await api.get(`${API_CONFIG.ENDPOINTS.CONVERSATIONS}/${id}`);
      return response.data;
    },
    enabled: !!id,
  });
};

export const useConversationMessages = (conversationId: number) => {
  return useQuery<MessageResponse[]>({
    queryKey: ['messages', conversationId],
    queryFn: async () => {
      const response = await api.get(`${API_CONFIG.ENDPOINTS.CONVERSATIONS}/${conversationId}/messages`);
      return response.data;
    },
    enabled: !!conversationId,
  });
};

export const useCreateConversation = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (title?: string) => {
      const response = await api.post(API_CONFIG.ENDPOINTS.CONVERSATIONS, { title });
      return response.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['conversations'] });
    },
  });
};

export const useSendMessage = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: ChatRequest) => {
      const response = await api.post(API_CONFIG.ENDPOINTS.SEND_MESSAGE, data);
      return response.data;
    },
    onSuccess: (_, variables) => {
      queryClient.invalidateQueries({ queryKey: ['messages', variables.conversationId] });
      queryClient.invalidateQueries({ queryKey: ['conversations'] });
    },
  });
};

export const useDeleteConversation = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (id: number) => {
      await api.delete(`${API_CONFIG.ENDPOINTS.CONVERSATIONS}/${id}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['conversations'] });
    },
  });
};
