import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import api from '@/lib/api';
import { API_CONFIG } from '@/lib/config';
import { DocumentResponse, PageResponse } from '@/types';

export const useDocuments = (page: number = 0, size: number = 10) => {
  return useQuery<PageResponse<DocumentResponse>>({
    queryKey: ['documents', page, size],
    queryFn: async () => {
      const response = await api.get(`${API_CONFIG.ENDPOINTS.DOCUMENTS}?page=${page}&size=${size}`);
      return response.data;
    },
  });
};

export const useDocument = (id: number) => {
  return useQuery<DocumentResponse>({
    queryKey: ['document', id],
    queryFn: async () => {
      const response = await api.get(`${API_CONFIG.ENDPOINTS.DOCUMENTS}/${id}`);
      return response.data;
    },
    enabled: !!id,
  });
};

export const useUploadDocument = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (formData: FormData) => {
      const response = await api.post(API_CONFIG.ENDPOINTS.DOCUMENT_UPLOAD, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      });
      return response.data;
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['documents'] });
    },
  });
};

export const useDeleteDocument = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (id: number) => {
      await api.delete(`${API_CONFIG.ENDPOINTS.DOCUMENTS}/${id}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['documents'] });
    },
  });
};
