import { create } from 'zustand';
import { persist, createJSONStorage } from 'zustand/middleware';
import api from '@/lib/api';
import { API_CONFIG, STORAGE_KEYS } from '@/lib/config';
import { User, LoginRequest, RegisterRequest, AuthResponse } from '@/types';

interface AuthState {
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;
  isLoading: boolean;
  error: string | null;
  
  login: (credentials: LoginRequest) => Promise<void>;
  register: (data: RegisterRequest) => Promise<void>;
  logout: () => void;
  setUser: (user: User) => void;
  clearError: () => void;
}

export const useAuthStore = create<AuthState>()(
  persist(
    (set) => ({
      user: null,
      token: null,
      isAuthenticated: false,
      isLoading: false,
      error: null,

      login: async (credentials: LoginRequest) => {
        set({ isLoading: true, error: null });
        try {
          const response = await api.post<AuthResponse>(
            API_CONFIG.ENDPOINTS.LOGIN,
            credentials
          );
          
          const { token, id, username, email, roles } = response.data;
          const user: User = { id, username, email, roles };
          
          // Store token in localStorage
          if (typeof window !== 'undefined') {
            localStorage.setItem(STORAGE_KEYS.TOKEN, token);
            localStorage.setItem(STORAGE_KEYS.USER, JSON.stringify(user));
          }
          
          set({
            user,
            token,
            isAuthenticated: true,
            isLoading: false,
            error: null,
          });
        } catch (error: any) {
          const errorMessage = error.response?.data?.message || 'Đăng nhập thất bại';
          set({
            error: errorMessage,
            isLoading: false,
            isAuthenticated: false,
          });
          throw error;
        }
      },

      register: async (data: RegisterRequest) => {
        set({ isLoading: true, error: null });
        try {
          const response = await api.post<AuthResponse>(
            API_CONFIG.ENDPOINTS.REGISTER,
            data
          );
          
          const { token, id, username, email, roles } = response.data;
          const user: User = { id, username, email, roles };
          
          // Store token in localStorage
          if (typeof window !== 'undefined') {
            localStorage.setItem(STORAGE_KEYS.TOKEN, token);
            localStorage.setItem(STORAGE_KEYS.USER, JSON.stringify(user));
          }
          
          set({
            user,
            token,
            isAuthenticated: true,
            isLoading: false,
            error: null,
          });
        } catch (error: any) {
          const errorMessage = error.response?.data?.message || 'Đăng ký thất bại';
          set({
            error: errorMessage,
            isLoading: false,
            isAuthenticated: false,
          });
          throw error;
        }
      },

      logout: () => {
        if (typeof window !== 'undefined') {
          localStorage.removeItem(STORAGE_KEYS.TOKEN);
          localStorage.removeItem(STORAGE_KEYS.USER);
        }
        set({
          user: null,
          token: null,
          isAuthenticated: false,
          error: null,
        });
      },

      setUser: (user: User) => {
        set({ user });
      },

      clearError: () => {
        set({ error: null });
      },
    }),
    {
      name: 'auth-storage',
      storage: createJSONStorage(() => 
        typeof window !== 'undefined' ? localStorage : {
          getItem: () => null,
          setItem: () => {},
          removeItem: () => {},
        }
      ),
      partialize: (state) => ({
        user: state.user,
        token: state.token,
        isAuthenticated: state.isAuthenticated,
      }),
    }
  )
);
