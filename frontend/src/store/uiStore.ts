import { create } from 'zustand'

interface UIState {
  sidebarOpen: boolean
  toggleSidebar: () => void
  setSidebarOpen: (open: boolean) => void
  
  theme: 'light' | 'dark'
  setTheme: (theme: 'light' | 'dark') => void
  
  notificationCount: number
  setNotificationCount: (count: number) => void
  
  loading: boolean
  setLoading: (loading: boolean) => void
}

export const useUIStore = create<UIState>((set) => ({
  sidebarOpen: true,
  toggleSidebar: () => set((state) => ({ sidebarOpen: !state.sidebarOpen })),
  setSidebarOpen: (open) => set({ sidebarOpen: open }),
  
  theme: 'light',
  setTheme: (theme) => set({ theme }),
  
  notificationCount: 0,
  setNotificationCount: (count) => set({ notificationCount: count }),
  
  loading: false,
  setLoading: (loading) => set({ loading }),
}))

