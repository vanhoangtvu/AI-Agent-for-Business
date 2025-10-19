import axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios'
import { ApiResponse } from '@/types'

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8087/api/v1'

// Create axios instance
const apiClient: AxiosInstance = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  timeout: 30000,
})

// Request interceptor - Add auth token
apiClient.interceptors.request.use(
  (config) => {
    if (typeof window !== 'undefined') {
      const token = localStorage.getItem('token')
      if (token) {
        config.headers.Authorization = `Bearer ${token}`
      }
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor - Handle errors
apiClient.interceptors.response.use(
  (response: AxiosResponse<ApiResponse>) => {
    return response
  },
  (error) => {
    if (error.response?.status === 401) {
      // Unauthorized - clear token and redirect to login
      if (typeof window !== 'undefined') {
        localStorage.removeItem('token')
        window.location.href = '/login'
      }
    }
    return Promise.reject(error)
  }
)

// Generic API methods
export const api = {
  get: async <T = any>(url: string, config?: AxiosRequestConfig): Promise<T> => {
    const response = await apiClient.get<ApiResponse<T>>(url, config)
    return response.data.data as T
  },

  post: async <T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> => {
    const response = await apiClient.post<ApiResponse<T>>(url, data, config)
    return response.data.data as T
  },

  put: async <T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> => {
    const response = await apiClient.put<ApiResponse<T>>(url, data, config)
    return response.data.data as T
  },

  delete: async <T = any>(url: string, config?: AxiosRequestConfig): Promise<T> => {
    const response = await apiClient.delete<ApiResponse<T>>(url, config)
    return response.data.data as T
  },

  upload: async <T = any>(url: string, formData: FormData, onProgress?: (progress: number) => void): Promise<T> => {
    const response = await apiClient.post<ApiResponse<T>>(url, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
      onUploadProgress: (progressEvent) => {
        if (onProgress && progressEvent.total) {
          const progress = Math.round((progressEvent.loaded * 100) / progressEvent.total)
          onProgress(progress)
        }
      },
    })
    return response.data.data as T
  },
}

// API endpoints organized by feature
export const authApi = {
  login: (data: { email: string; password: string }) => api.post('/auth/login', data),
  register: (data: any) => api.post('/auth/register', data),
  registerBusiness: (data: any) => api.post('/auth/register/business', data),
  registerCustomer: (data: any) => api.post('/auth/register/customer', data),
  me: () => api.get('/auth/me'),
}

export const businessApi = {
  getProfile: () => api.get('/business/profile'),
  updateProfile: (data: any) => api.put('/business/profile', data),
  getStats: () => api.get('/business/stats'),
}

export const customersApi = {
  getAll: (params?: any) => api.get('/business/customers', { params }),
  getById: (id: number) => api.get(`/business/customers/${id}`),
  create: (data: any) => api.post('/business/customers', data),
  update: (id: number, data: any) => api.put(`/business/customers/${id}`, data),
  delete: (id: number) => api.delete(`/business/customers/${id}`),
}

export const productsApi = {
  getAll: (params?: any) => api.get('/business/products', { params }),
  getById: (id: number) => api.get(`/business/products/${id}`),
  create: (data: any) => api.post('/business/products', data),
  update: (id: number, data: any) => api.put(`/business/products/${id}`, data),
  delete: (id: number) => api.delete(`/business/products/${id}`),
}

export const ordersApi = {
  getAll: (params?: any) => api.get('/business/orders', { params }),
  getById: (id: number) => api.get(`/business/orders/${id}`),
  create: (data: any) => api.post('/business/orders', data),
  update: (id: number, data: any) => api.put(`/business/orders/${id}`, data),
  delete: (id: number) => api.delete(`/business/orders/${id}`),
}

export const conversationsApi = {
  getAll: (params?: any) => api.get('/business/conversations', { params }),
  getById: (id: number) => api.get(`/business/conversations/${id}`),
  getMessages: (id: number) => api.get(`/business/conversations/${id}/messages`),
  sendMessage: (id: number, data: any) => api.post(`/business/conversations/${id}/messages`, data),
  updateStatus: (id: number, status: string) => api.put(`/business/conversations/${id}/status`, { status }),
}

export const analyticsApi = {
  getOverview: (params?: any) => api.get('/analytics/overview', { params }),
  getRevenue: (params?: any) => api.get('/analytics/revenue', { params }),
  getTopProducts: (params?: any) => api.get('/analytics/products/top-selling', { params }),
  getRFM: () => api.get('/analytics/customers/rfm'),
  getInsights: () => api.get('/analytics/ai-insights'),
  getChatbotPerformance: (params?: any) => api.get('/analytics/chatbot/performance', { params }),
}

export const chatbotApi = {
  getConfig: () => api.get('/chatbot/config'),
  updateConfig: (data: any) => api.post('/chatbot/config', data),
  getDocuments: (params?: any) => api.get('/chatbot/documents', { params }),
  uploadDocument: (file: File, onProgress?: (progress: number) => void) => {
    const formData = new FormData()
    formData.append('file', file)
    return api.upload('/chatbot/documents/upload', formData, onProgress)
  },
  deleteDocument: (id: number) => api.delete(`/chatbot/documents/${id}`),
  test: (data: any) => api.post('/chatbot/test', data),
  chat: (data: any) => api.post('/chatbot/chat', data),
}

export const zaloApi = {
  // Zalo OA
  configOA: (data: any) => api.post('/zalo/oa/config', data),
  getOAStatus: () => api.get('/zalo/oa/status'),
  getOAConversations: (params?: any) => api.get('/zalo/oa/conversations', { params }),
  sendOAMessage: (data: any) => api.post('/zalo/oa/send-message', data),
  
  // Zalo Personal
  personal: {
    generateQR: () => api.post('/zalo/personal/generate-qr'),
    checkLoginStatus: (sessionId: string) => api.get(`/zalo/personal/login-status/${sessionId}`),
    config: (data: any) => api.post('/zalo/personal/config', data),
    getConversations: (params?: any) => api.get('/zalo/personal/conversations', { params }),
    sendMessage: (data: any) => api.post('/zalo/personal/send-message', data),
    disconnect: () => api.delete('/zalo/personal/disconnect'),
  },
  
  // Backwards compatibility
  generateQR: () => api.post('/zalo/personal/generate-qr'),
  checkLoginStatus: (sessionId: string) => api.get(`/zalo/personal/login-status/${sessionId}`),
  configPersonal: (data: any) => api.post('/zalo/personal/config', data),
  getPersonalConversations: (params?: any) => api.get('/zalo/personal/conversations', { params }),
  sendPersonalMessage: (data: any) => api.post('/zalo/personal/send-message', data),
  disconnect: () => api.delete('/zalo/personal/disconnect'),
}

export const adminApi = {
  getBusinesses: (params?: any) => api.get('/admin/businesses', { params }),
  getBusinessById: (id: number) => api.get(`/admin/businesses/${id}`),
  updateBusiness: (id: number, data: any) => api.put(`/admin/businesses/${id}`, data),
  deleteBusiness: (id: number) => api.delete(`/admin/businesses/${id}`),
  suspendBusiness: (id: number) => api.put(`/admin/businesses/${id}/suspend`),
  activateBusiness: (id: number) => api.put(`/admin/businesses/${id}/activate`),
  getStats: () => api.get('/admin/stats'),
  getUsers: (params?: any) => api.get('/admin/users', { params }),
  getLogs: (params?: any) => api.get('/admin/logs', { params }),
}

export const customerApi = {
  getProfile: () => api.get('/customer/profile'),
  updateProfile: (data: any) => api.put('/customer/profile', data),
  getOrders: (params?: any) => api.get('/customer/orders', { params }),
  getOrderById: (id: number) => api.get(`/customer/orders/${id}`),
  getConversations: () => api.get('/customer/conversations'),
  getMessages: (id: number) => api.get(`/customer/conversations/${id}/messages`),
  sendMessage: (id: number, data: any) => api.post(`/customer/conversations/${id}/messages`, data),
  getNotifications: () => api.get('/customer/notifications'),
  markAsRead: (id: number) => api.put(`/customer/notifications/${id}/read`),
}

export default api

