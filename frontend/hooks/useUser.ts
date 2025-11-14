import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import api from '@/lib/api';
import { API_CONFIG } from '@/lib/config';
import { UserResponse, UpdateProfileRequest, ChangePasswordRequest } from '@/types';
import { useAuthStore } from '@/store/auth';

export const useUserProfile = () => {
  return useQuery<UserResponse>({
    queryKey: ['user-profile'],
    queryFn: async () => {
      const response = await api.get(API_CONFIG.ENDPOINTS.USER_PROFILE);
      return response.data;
    },
  });
};

export const useUpdateProfile = () => {
  const queryClient = useQueryClient();
  const setUser = useAuthStore((state) => state.setUser);

  return useMutation({
    mutationFn: async (data: UpdateProfileRequest) => {
      const response = await api.put(API_CONFIG.ENDPOINTS.UPDATE_PROFILE, data);
      return response.data;
    },
    onSuccess: (data) => {
      queryClient.invalidateQueries({ queryKey: ['user-profile'] });
      setUser(data);
    },
  });
};

export const useChangePassword = () => {
  return useMutation({
    mutationFn: async (data: ChangePasswordRequest) => {
      await api.post(API_CONFIG.ENDPOINTS.CHANGE_PASSWORD, data);
    },
  });
};
