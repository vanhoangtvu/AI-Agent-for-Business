'use client';

import { useState } from 'react';
import { useDocuments, useUploadDocument, useDeleteDocument } from '@/hooks/useDocuments';
import { useAuthStore } from '@/store/auth';
import Link from 'next/link';

export default function DocumentsPage() {
  const { user } = useAuthStore();
  const [page, setPage] = useState(0);
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [isDragging, setIsDragging] = useState(false);

  const { data: documentsData, isLoading, error } = useDocuments(page, 10);
  const uploadMutation = useUploadDocument();
  const deleteMutation = useDeleteDocument();

  const handleFileSelect = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.files && e.target.files[0]) {
      setSelectedFile(e.target.files[0]);
    }
  };

  const handleDragOver = (e: React.DragEvent) => {
    e.preventDefault();
    setIsDragging(true);
  };

  const handleDragLeave = () => {
    setIsDragging(false);
  };

  const handleDrop = (e: React.DragEvent) => {
    e.preventDefault();
    setIsDragging(false);
    if (e.dataTransfer.files && e.dataTransfer.files[0]) {
      setSelectedFile(e.dataTransfer.files[0]);
    }
  };

  const handleUpload = async () => {
    if (!selectedFile) return;

    try {
      const formData = new FormData();
      formData.append('file', selectedFile);
      await uploadMutation.mutateAsync(formData);
      setSelectedFile(null);
      // Reset file input
      const fileInput = document.getElementById('file-input') as HTMLInputElement;
      if (fileInput) fileInput.value = '';
    } catch (error) {
      console.error('Upload failed:', error);
    }
  };

  const handleDelete = async (id: number) => {
    if (confirm('Bạn có chắc muốn xóa tài liệu này?')) {
      try {
        await deleteMutation.mutateAsync(id);
      } catch (error) {
        console.error('Delete failed:', error);
      }
    }
  };

  const getStatusBadge = (status: string) => {
    const statusConfig = {
      PENDING: { bg: 'bg-yellow-100', text: 'text-yellow-800', label: 'Đang xử lý' },
      COMPLETED: { bg: 'bg-green-100', text: 'text-green-800', label: 'Hoàn thành' },
      FAILED: { bg: 'bg-red-100', text: 'text-red-800', label: 'Thất bại' },
    };

    const config = statusConfig[status as keyof typeof statusConfig] || statusConfig.PENDING;

    return (
      <span className={`px-2 py-1 text-xs font-semibold rounded-full ${config.bg} ${config.text}`}>
        {config.label}
      </span>
    );
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleString('vi-VN');
  };

  const formatFileSize = (bytes: number) => {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return Math.round(bytes / Math.pow(k, i) * 100) / 100 + ' ' + sizes[i];
  };

  return (
    <div className="space-y-6">
      <div className="flex justify-between items-center">
        <div>
          <h1 className="text-3xl font-bold text-gray-900">Quản lý Tài liệu</h1>
          <p className="text-gray-600 mt-1">Upload và quản lý tài liệu của bạn</p>
        </div>
      </div>

      {/* Upload Section */}
      <div className="bg-white rounded-lg shadow p-6">
        <h2 className="text-xl font-semibold mb-4">Upload Tài liệu mới</h2>
        
        {/* Drag & Drop Area */}
        <div
          onDragOver={handleDragOver}
          onDragLeave={handleDragLeave}
          onDrop={handleDrop}
          className={`border-2 border-dashed rounded-lg p-8 text-center transition-colors ${
            isDragging ? 'border-blue-500 bg-blue-50' : 'border-gray-300'
          }`}
        >
          <svg
            className="mx-auto h-12 w-12 text-gray-400"
            stroke="currentColor"
            fill="none"
            viewBox="0 0 48 48"
          >
            <path
              d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12a4 4 0 01-4-4v-4m32-4l-3.172-3.172a4 4 0 00-5.656 0L28 28M8 32l9.172-9.172a4 4 0 015.656 0L28 28m0 0l4 4m4-24h8m-4-4v8m-12 4h.02"
              strokeWidth={2}
              strokeLinecap="round"
              strokeLinejoin="round"
            />
          </svg>
          <div className="mt-4">
            <label htmlFor="file-input" className="cursor-pointer">
              <span className="text-blue-600 hover:text-blue-500 font-medium">
                Chọn file
              </span>
              <span className="text-gray-600"> hoặc kéo thả vào đây</span>
            </label>
            <input
              id="file-input"
              type="file"
              className="hidden"
              onChange={handleFileSelect}
              accept=".pdf,.doc,.docx,.txt,.xlsx,.xls"
            />
          </div>
          <p className="text-xs text-gray-500 mt-2">
            PDF, DOC, DOCX, TXT, XLSX, XLS (Max 10MB)
          </p>
        </div>

        {/* Selected File */}
        {selectedFile && (
          <div className="mt-4 p-4 bg-gray-50 rounded-lg flex items-center justify-between">
            <div className="flex items-center space-x-3">
              <svg className="w-8 h-8 text-gray-400" fill="currentColor" viewBox="0 0 20 20">
                <path
                  fillRule="evenodd"
                  d="M4 4a2 2 0 012-2h4.586A2 2 0 0112 2.586L15.414 6A2 2 0 0116 7.414V16a2 2 0 01-2 2H6a2 2 0 01-2-2V4z"
                  clipRule="evenodd"
                />
              </svg>
              <div>
                <p className="font-medium text-gray-900">{selectedFile.name}</p>
                <p className="text-sm text-gray-500">{formatFileSize(selectedFile.size)}</p>
              </div>
            </div>
            <div className="flex space-x-2">
              <button
                onClick={handleUpload}
                disabled={uploadMutation.isPending}
                className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                {uploadMutation.isPending ? 'Đang upload...' : 'Upload'}
              </button>
              <button
                onClick={() => setSelectedFile(null)}
                className="px-4 py-2 bg-gray-200 text-gray-700 rounded-lg hover:bg-gray-300"
              >
                Hủy
              </button>
            </div>
          </div>
        )}

        {uploadMutation.isError && (
          <div className="mt-4 p-4 bg-red-50 text-red-700 rounded-lg">
            Upload thất bại. Vui lòng thử lại.
          </div>
        )}

        {uploadMutation.isSuccess && (
          <div className="mt-4 p-4 bg-green-50 text-green-700 rounded-lg">
            Upload thành công!
          </div>
        )}
      </div>

      {/* Documents List */}
      <div className="bg-white rounded-lg shadow">
        <div className="p-6 border-b">
          <h2 className="text-xl font-semibold">Danh sách Tài liệu</h2>
        </div>

        {isLoading ? (
          <div className="p-8 text-center">
            <div className="inline-block animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            <p className="mt-2 text-gray-600">Đang tải...</p>
          </div>
        ) : error ? (
          <div className="p-8 text-center text-red-600">
            Không thể tải danh sách tài liệu. Vui lòng thử lại.
          </div>
        ) : !documentsData?.content || documentsData.content.length === 0 ? (
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
                d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z"
              />
            </svg>
            <p className="mt-2">Chưa có tài liệu nào</p>
            <p className="text-sm">Upload tài liệu đầu tiên của bạn</p>
          </div>
        ) : (
          <>
            <div className="overflow-x-auto">
              <table className="min-w-full divide-y divide-gray-200">
                <thead className="bg-gray-50">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Tên tài liệu
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Loại
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Kích thước
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Danh mục
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Tags
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Vector
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Chunks
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Trạng thái
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Ngày tạo
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Xử lý xong
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      Thao tác
                    </th>
                  </tr>
                </thead>
                <tbody className="bg-white divide-y divide-gray-200">
                  {documentsData.content.map((doc) => (
                    <tr key={doc.id} className="hover:bg-gray-50">
                      <td className="px-6 py-4 whitespace-nowrap">
                        <div className="flex items-center">
                          <svg className="w-5 h-5 text-gray-400 mr-2" fill="currentColor" viewBox="0 0 20 20">
                            <path
                              fillRule="evenodd"
                              d="M4 4a2 2 0 012-2h4.586A2 2 0 0112 2.586L15.414 6A2 2 0 0116 7.414V16a2 2 0 01-2 2H6a2 2 0 01-2-2V4z"
                              clipRule="evenodd"
                            />
                          </svg>
                          <span className="text-sm font-medium text-gray-900">{doc.fileName}</span>
                        </div>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className="text-sm text-gray-600">{doc.fileType}</span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className="text-sm text-gray-600">{formatFileSize(doc.fileSize)}</span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className="text-sm text-gray-600">
                          {doc.category || <span className="text-gray-400 italic">Chưa phân loại</span>}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span className="text-sm text-gray-600">
                          {doc.tags || <span className="text-gray-400 italic">-</span>}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-center">
                        {doc.vectorized ? (
                          <svg className="w-5 h-5 text-green-500 inline" fill="currentColor" viewBox="0 0 20 20">
                            <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clipRule="evenodd" />
                          </svg>
                        ) : (
                          <svg className="w-5 h-5 text-gray-300 inline" fill="currentColor" viewBox="0 0 20 20">
                            <path fillRule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zM8.707 7.293a1 1 0 00-1.414 1.414L8.586 10l-1.293 1.293a1 1 0 101.414 1.414L10 11.414l1.293 1.293a1 1 0 001.414-1.414L11.414 10l1.293-1.293a1 1 0 00-1.414-1.414L10 8.586 8.707 7.293z" clipRule="evenodd" />
                          </svg>
                        )}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-center">
                        <span className="text-sm text-gray-900 font-medium">{doc.chunkCount}</span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        {getStatusBadge(doc.status)}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                        {formatDate(doc.createdAt)}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-gray-600">
                        {doc.processedAt ? formatDate(doc.processedAt) : <span className="text-gray-400 italic">-</span>}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm font-medium">
                        <button
                          onClick={() => handleDelete(doc.id)}
                          disabled={deleteMutation.isPending}
                          className="text-red-600 hover:text-red-900 disabled:opacity-50"
                        >
                          Xóa
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            {/* Pagination */}
            {documentsData && documentsData.totalPages > 1 && (
              <div className="px-6 py-4 border-t flex items-center justify-between">
                <div className="text-sm text-gray-600">
                  Trang {page + 1} / {documentsData.totalPages} (Tổng: {documentsData.totalElements} tài liệu)
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
                    onClick={() => setPage(p => Math.min(documentsData.totalPages - 1, p + 1))}
                    disabled={page >= documentsData.totalPages - 1}
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
    </div>
  );
}
