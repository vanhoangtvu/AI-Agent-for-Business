import { useQuery } from '@tanstack/react-query'
import { analyticsApi } from '@/lib/api'

export function useAnalytics(dateRange?: { from: string; to: string }) {
  return useQuery({
    queryKey: ['analytics', dateRange],
    queryFn: () => analyticsApi.getOverview(dateRange),
  })
}

export function useRevenueChart(dateRange?: { from: string; to: string }) {
  return useQuery({
    queryKey: ['revenue-chart', dateRange],
    queryFn: () => analyticsApi.getRevenue(dateRange),
  })
}

export function useTopProducts(limit: number = 10) {
  return useQuery({
    queryKey: ['top-products', limit],
    queryFn: () => analyticsApi.getTopProducts({ limit }),
  })
}

export function useCustomerSegments() {
  return useQuery({
    queryKey: ['customer-segments'],
    queryFn: analyticsApi.getRFM,
  })
}

export function useRFMAnalysis() {
  return useQuery({
    queryKey: ['rfm-analysis'],
    queryFn: analyticsApi.getRFM,
  })
}

export function useAIInsights() {
  return useQuery({
    queryKey: ['ai-insights'],
    queryFn: analyticsApi.getInsights,
    refetchInterval: 60000, // Refetch every minute
  })
}

