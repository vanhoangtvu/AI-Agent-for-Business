import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { conversationsApi } from '@/lib/api'
import { toast } from 'react-hot-toast'

export function useConversations() {
  const queryClient = useQueryClient()

  const { data: conversations, isLoading, error } = useQuery({
    queryKey: ['conversations'],
    queryFn: conversationsApi.getAll,
  })

  const updateStatusMutation = useMutation({
    mutationFn: ({ id, status }: { id: number; status: string }) =>
      conversationsApi.updateStatus(id, status),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['conversations'] })
      toast.success('Conversation status updated!')
    },
    onError: () => {
      toast.error('Failed to update status')
    },
  })

  return {
    conversations: conversations?.data,
    isLoading,
    error,
    updateStatus: updateStatusMutation.mutate,
    isUpdating: updateStatusMutation.isPending,
  }
}

export function useConversation(id: number) {
  return useQuery({
    queryKey: ['conversation', id],
    queryFn: () => conversationsApi.getById(id),
    enabled: !!id,
  })
}

export function useMessages(conversationId: number) {
  return useQuery({
    queryKey: ['messages', conversationId],
    queryFn: () => conversationsApi.getMessages(conversationId),
    enabled: !!conversationId,
    refetchInterval: 3000, // Auto-refresh every 3 seconds
  })
}

