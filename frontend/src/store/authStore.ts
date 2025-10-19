import { create } from 'zustand'
import { persist } from 'zustand/middleware'
import { User, UserRole } from '@/types'

interface AuthState {
  user: User | null
  token: string | null
  isAuthenticated: boolean
  setAuth: (user: User, token: string) => void
  clearAuth: () => void
  logout: () => void
  updateUser: (user: Partial<User>) => void
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      token: null,
      isAuthenticated: false,
      
      setAuth: (user, token) => {
        if (typeof window !== 'undefined') {
          localStorage.setItem('token', token)
          // Set cookie for middleware
          document.cookie = `token=${token}; path=/; max-age=${7 * 24 * 60 * 60}` // 7 days
        }
        set({ user, token, isAuthenticated: true })
      },
      
      clearAuth: () => {
        if (typeof window !== 'undefined') {
          localStorage.removeItem('token')
          // Clear cookie
          document.cookie = 'token=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT'
        }
        set({ user: null, token: null, isAuthenticated: false })
      },
      
      logout: () => {
        if (typeof window !== 'undefined') {
          localStorage.removeItem('token')
          // Clear cookie
          document.cookie = 'token=; path=/; expires=Thu, 01 Jan 1970 00:00:00 GMT'
        }
        set({ user: null, token: null, isAuthenticated: false })
      },
      
      updateUser: (userData) => {
        set((state) => ({
          user: state.user ? { ...state.user, ...userData } : null,
        }))
      },
    }),
    {
      name: 'auth-storage',
    }
  )
)

// Helper functions
export const isAdmin = (role?: UserRole) => role === 'ADMIN'
export const isBusiness = (role?: UserRole) => role === 'BUSINESS'
export const isCustomer = (role?: UserRole) => role === 'CUSTOMER'

