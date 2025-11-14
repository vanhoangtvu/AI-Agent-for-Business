import { useQuery } from '@tanstack/react-query';
import api from '@/lib/api';
import { Activity, PageResponse } from '@/types';

export const useActivities = (page: number = 0, size: number = 10) => {
  return useQuery<PageResponse<Activity>>({
    queryKey: ['activities', page, size],
    queryFn: async () => {
      const response = await api.get(`/api/activities?page=${page}&size=${size}`);
      return response.data;
    },
  });
};
