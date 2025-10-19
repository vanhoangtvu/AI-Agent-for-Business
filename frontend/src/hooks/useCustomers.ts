import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { customersApi } from '@/lib/api'
import { Customer } from '@/types'
import { toast } from 'react-hot-toast'

export function useCustomers() {
  const queryClient = useQueryClient()

  const { data: customers, isLoading, error } = useQuery({
    queryKey: ['customers'],
    queryFn: customersApi.getAll,
  })

  const createMutation = useMutation({
    mutationFn: customersApi.create,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['customers'] })
      toast.success('Customer created successfully!')
    },
    onError: () => {
      toast.error('Failed to create customer')
    },
  })

  const updateMutation = useMutation({
    mutationFn: ({ id, data }: { id: number; data: Partial<Customer> }) =>
      customersApi.update(id, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['customers'] })
      toast.success('Customer updated successfully!')
    },
    onError: () => {
      toast.error('Failed to update customer')
    },
  })

  const deleteMutation = useMutation({
    mutationFn: customersApi.delete,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['customers'] })
      toast.success('Customer deleted successfully!')
    },
    onError: () => {
      toast.error('Failed to delete customer')
    },
  })

  return {
    customers: customers?.data,
    isLoading,
    error,
    createCustomer: createMutation.mutate,
    updateCustomer: updateMutation.mutate,
    deleteCustomer: deleteMutation.mutate,
    isCreating: createMutation.isPending,
    isUpdating: updateMutation.isPending,
    isDeleting: deleteMutation.isPending,
  }
}

export function useCustomer(id: number) {
  return useQuery({
    queryKey: ['customer', id],
    queryFn: () => customersApi.getById(id),
    enabled: !!id,
  })
}

