import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { chatbotApi } from '@/lib/api'
import { toast } from 'react-hot-toast'

export function useChatbotConfig() {
  const queryClient = useQueryClient()

  const { data: config, isLoading, error } = useQuery({
    queryKey: ['chatbot-config'],
    queryFn: chatbotApi.getConfig,
  })

  const updateMutation = useMutation({
    mutationFn: chatbotApi.updateConfig,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['chatbot-config'] })
      toast.success('Chatbot config updated!')
    },
    onError: () => {
      toast.error('Failed to update config')
    },
  })

  return {
    config: config?.data,
    isLoading,
    error,
    updateConfig: updateMutation.mutate,
    isUpdating: updateMutation.isPending,
  }
}

export function useDocuments() {
  const queryClient = useQueryClient()

  const { data: documents, isLoading, error } = useQuery({
    queryKey: ['documents'],
    queryFn: chatbotApi.getDocuments,
  })

  const uploadMutation = useMutation({
    mutationFn: (file: File) => chatbotApi.uploadDocument(file),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['documents'] })
      toast.success('Document uploaded successfully!')
    },
    onError: () => {
      toast.error('Failed to upload document')
    },
  })

  const deleteMutation = useMutation({
    mutationFn: chatbotApi.deleteDocument,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['documents'] })
      toast.success('Document deleted!')
    },
    onError: () => {
      toast.error('Failed to delete document')
    },
  })

  return {
    documents: documents?.data,
    isLoading,
    error,
    uploadDocument: uploadMutation.mutate,
    deleteDocument: deleteMutation.mutate,
    isUploading: uploadMutation.isPending,
    isDeleting: deleteMutation.isPending,
  }
}

