import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { zaloApi } from '@/lib/api'
import { toast } from 'react-hot-toast'

export function useZaloPersonalConfig() {
  const queryClient = useQueryClient()

  const { data: config, isLoading, error } = useQuery({
    queryKey: ['zalo-personal-config'],
    queryFn: () => zaloApi.getOAStatus(),
  })

  const updateMutation = useMutation({
    mutationFn: zaloApi.personal.config,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['zalo-personal-config'] })
      toast.success('Zalo config updated!')
    },
    onError: () => {
      toast.error('Failed to update config')
    },
  })

  const disconnectMutation = useMutation({
    mutationFn: zaloApi.personal.disconnect,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['zalo-personal-config'] })
      toast.success('Disconnected from Zalo')
    },
    onError: () => {
      toast.error('Failed to disconnect')
    },
  })

  return {
    config: config?.data,
    isLoading,
    error,
    updateConfig: updateMutation.mutate,
    disconnect: disconnectMutation.mutate,
    isUpdating: updateMutation.isPending,
    isDisconnecting: disconnectMutation.isPending,
  }
}

export function useZaloQRLogin() {
  const { data: qrData, isLoading, error } = useQuery({
    queryKey: ['zalo-qr'],
    queryFn: zaloApi.personal.generateQR,
  })

  return {
    qrCode: qrData?.data?.qrCode,
    sessionId: qrData?.data?.sessionId,
    isLoading,
    error,
  }
}

export function useZaloLoginStatus(sessionId: string) {
  return useQuery({
    queryKey: ['zalo-login-status', sessionId],
    queryFn: () => zaloApi.personal.checkLoginStatus(sessionId),
    enabled: !!sessionId,
    refetchInterval: 2000, // Check every 2 seconds
  })
}

export function useZaloConversations() {
  return useQuery({
    queryKey: ['zalo-conversations'],
    queryFn: () => zaloApi.personal.getConversations(),
    refetchInterval: 5000, // Auto-refresh every 5 seconds
  })
}

export function useZaloSendMessage() {
  const queryClient = useQueryClient()

  return useMutation({
    mutationFn: zaloApi.personal.sendMessage,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['zalo-conversations'] })
      toast.success('Message sent!')
    },
    onError: () => {
      toast.error('Failed to send message')
    },
  })
}

