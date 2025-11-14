'use client';

import { useState } from 'react';
import { useActivities } from '@/hooks/useActivities';

export default function ActivitiesPage() {
  const [page, setPage] = useState(0);
  const { data: activitiesData, isLoading, error } = useActivities(page, 20);

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleString('vi-VN');
  };

  const getActionIcon = (action: string) => {
    switch (action.toLowerCase()) {
      case 'upload':
      case 'document_upload':
        return (
          <svg className="w-5 h-5 text-blue-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12" />
          </svg>
        );
      case 'delete':
      case 'document_delete':
        return (
          <svg className="w-5 h-5 text-red-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
          </svg>
        );
      case 'chat':
      case 'message_sent':
        return (
          <svg className="w-5 h-5 text-green-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z" />
          </svg>
        );
      case 'login':
        return (
          <svg className="w-5 h-5 text-purple-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1" />
          </svg>
        );
      case 'profile_update':
        return (
          <svg className="w-5 h-5 text-orange-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
          </svg>
        );
      default:
        return (
          <svg className="w-5 h-5 text-gray-500" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
        );
    }
  };

  return (
    <div className="space-y-6">
      <div>
        <h1 className="text-3xl font-bold text-gray-900">Nhật ký Hoạt động</h1>
        <p className="text-gray-600 mt-1">Theo dõi các hoạt động của bạn trong hệ thống</p>
      </div>

      <div className="bg-white rounded-lg shadow">
        <div className="p-6 border-b">
          <h2 className="text-xl font-semibold">Lịch sử hoạt động</h2>
        </div>

        {isLoading ? (
          <div className="p-8 text-center">
            <div className="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            <p className="mt-2 text-gray-600">Đang tải...</p>
          </div>
        ) : error ? (
          <div className="p-8 text-center text-red-600">
            Không thể tải nhật ký hoạt động. Vui lòng thử lại.
          </div>
        ) : !activitiesData?.content || activitiesData.content.length === 0 ? (
          <div className="p-8 text-center text-gray-500">
            <svg
              className="mx-auto h-12 w-12 text-gray-400"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"
              />
            </svg>
            <p className="mt-2">Chưa có hoạt động nào</p>
            <p className="text-sm">Các hoạt động của bạn sẽ hiển thị ở đây</p>
          </div>
        ) : (
          <>
            {/* Timeline */}
            <div className="p-6">
              <div className="space-y-4">
                {activitiesData.content.map((activity, index) => (
                  <div key={activity.id} className="relative flex items-start space-x-4">
                    {/* Timeline line */}
                    {index !== activitiesData.content.length - 1 && (
                      <div className="absolute left-5 top-10 bottom-0 w-0.5 bg-gray-200"></div>
                    )}

                    {/* Icon */}
                    <div className="relative flex-shrink-0 w-10 h-10 bg-gray-100 rounded-full flex items-center justify-center">
                      {getActionIcon(activity.action)}
                    </div>

                    {/* Content */}
                    <div className="flex-1 min-w-0 bg-gray-50 rounded-lg p-4">
                      <div className="flex justify-between items-start">
                        <div className="flex-1">
                          <p className="font-medium text-gray-900">{activity.action}</p>
                          <p className="text-sm text-gray-600 mt-1">{activity.description}</p>
                        </div>
                        <span className="text-xs text-gray-500 whitespace-nowrap ml-4">
                          {formatDate(activity.timestamp)}
                        </span>
                      </div>
                    </div>
                  </div>
                ))}
              </div>
            </div>

            {/* Pagination */}
            {activitiesData && activitiesData.totalPages > 1 && (
              <div className="px-6 py-4 border-t flex items-center justify-between">
                <div className="text-sm text-gray-600">
                  Trang {page + 1} / {activitiesData.totalPages} (Tổng: {activitiesData.totalElements} hoạt động)
                </div>
                <div className="flex space-x-2">
                  <button
                    onClick={() => setPage(p => Math.max(0, p - 1))}
                    disabled={page === 0}
                    className="px-3 py-1 border rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
                  >
                    Trước
                  </button>
                  <button
                    onClick={() => setPage(p => Math.min(activitiesData.totalPages - 1, p + 1))}
                    disabled={page >= activitiesData.totalPages - 1}
                    className="px-3 py-1 border rounded-lg hover:bg-gray-50 disabled:opacity-50 disabled:cursor-not-allowed"
                  >
                    Sau
                  </button>
                </div>
              </div>
            )}
          </>
        )}
      </div>

      {/* Info Card */}
      <div className="bg-blue-50 border border-blue-200 rounded-lg p-4">
        <div className="flex">
          <svg className="w-5 h-5 text-blue-600 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <div className="ml-3 flex-1">
            <h3 className="text-sm font-medium text-blue-900">Thông tin</h3>
            <p className="mt-1 text-sm text-blue-700">
              Nhật ký hoạt động ghi lại tất cả các hành động của bạn trong hệ thống, 
              bao gồm upload tài liệu, chat, cập nhật hồ sơ và các hoạt động khác.
            </p>
          </div>
        </div>
      </div>
    </div>
  );
}
