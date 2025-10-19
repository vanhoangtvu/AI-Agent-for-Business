import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { ordersApi } from '@/lib/api'
import { Order } from '@/types'
import { toast } from 'react-hot-toast'

export function useOrders() {
  const queryClient = useQueryClient()

  const { data: orders, isLoading, error } = useQuery({
    queryKey: ['orders'],
    queryFn: ordersApi.getAll,
  })

  const createMutation = useMutation({
    mutationFn: ordersApi.create,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['orders'] })
      toast.success('Order created successfully!')
    },
    onError: () => {
      toast.error('Failed to create order')
    },
  })

  const updateMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: Partial<Order> }) =>
      ordersApi.update(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['orders'] })
      toast.success('Order updated successfully!')
    },
    onError: () => {
      toast.error('Failed to update order')
    },
  })

  const deleteMutation = useMutation({
    mutationFn: ordersApi.delete,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['orders'] })
      toast.success('Order deleted successfully!')
    },
    onError: () => {
      toast.error('Failed to delete order')
    },
  })

  return {
    orders: orders?.data,
    isLoading,
    error,
    createOrder: createMutation.mutate,
    updateOrder: updateMutation.mutate,
    deleteOrder: deleteMutation.mutate,
    isCreating: createMutation.isPending,
    isUpdating: updateMutation.isPending,
    isDeleting: deleteMutation.isPending,
  }
}

export function useOrder(id: number) {
  return useQuery({
    queryKey: ['order', id],
    queryFn: () => ordersApi.getById(id),
    enabled: !!id,
  })
}

