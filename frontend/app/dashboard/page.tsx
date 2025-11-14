'use client';

import { useAuthStore } from '@/store/auth';
import { useDocuments } from '@/hooks/useDocuments';
import { useConversations } from '@/hooks/useConversations';
import Link from 'next/link';

export default function DashboardPage() {
  const { user } = useAuthStore();
  const { data: documentsData } = useDocuments(0, 5);
  const { data: conversationsData } = useConversations(0, 5);

  return (
    <div>
      <h1 className="text-3xl font-bold text-gray-900 mb-8">
        Welcome, {user?.username}!
      </h1>

      {/* Stats Cards */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
        <div className="bg-white p-6 rounded-lg shadow">
          <h3 className="text-sm font-medium text-gray-500 mb-2">Total Documents</h3>
          <p className="text-3xl font-bold text-gray-900">
            {documentsData?.totalElements || 0}
          </p>
        </div>
        
        <div className="bg-white p-6 rounded-lg shadow">
          <h3 className="text-sm font-medium text-gray-500 mb-2">Conversations</h3>
          <p className="text-3xl font-bold text-gray-900">
            {conversationsData?.totalElements || 0}
          </p>
        </div>
        
        <div className="bg-white p-6 rounded-lg shadow">
          <h3 className="text-sm font-medium text-gray-500 mb-2">Role</h3>
          <p className="text-xl font-semibold text-blue-600">
            {user?.roles.join(', ')}
          </p>
        </div>
      </div>

      {/* Recent Documents */}
      <div className="bg-white rounded-lg shadow mb-8">
        <div className="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
          <h2 className="text-lg font-semibold text-gray-900">Recent Documents</h2>
          <Link
            href="/dashboard/documents"
            className="text-sm text-blue-600 hover:text-blue-700"
          >
            View all
          </Link>
        </div>
        <div className="p-6">
          {documentsData?.content.length === 0 ? (
            <p className="text-gray-500 text-center py-4">No documents yet</p>
          ) : (
            <div className="space-y-3">
              {documentsData?.content.slice(0, 5).map((doc) => (
                <div key={doc.id} className="flex items-center justify-between py-2 border-b border-gray-100 last:border-0">
                  <div>
                    <p className="font-medium text-gray-900">{doc.fileName}</p>
                    <p className="text-sm text-gray-500">
                      {new Date(doc.createdAt).toLocaleDateString()} • {(doc.fileSize / 1024).toFixed(2)} KB
                    </p>
                  </div>
                  <span className={`px-2 py-1 text-xs rounded-full ${
                    doc.status === 'COMPLETED' ? 'bg-green-100 text-green-800' :
                    doc.status === 'PENDING' ? 'bg-yellow-100 text-yellow-800' :
                    'bg-gray-100 text-gray-800'
                  }`}>
                    {doc.status}
                  </span>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      {/* Recent Conversations */}
      <div className="bg-white rounded-lg shadow">
        <div className="px-6 py-4 border-b border-gray-200 flex justify-between items-center">
          <h2 className="text-lg font-semibold text-gray-900">Recent Conversations</h2>
          <Link
            href="/dashboard/chat"
            className="text-sm text-blue-600 hover:text-blue-700"
          >
            View all
          </Link>
        </div>
        <div className="p-6">
          {conversationsData?.content.length === 0 ? (
            <p className="text-gray-500 text-center py-4">No conversations yet</p>
          ) : (
            <div className="space-y-3">
              {conversationsData?.content.slice(0, 5).map((conv) => (
                <Link
                  key={conv.id}
                  href={`/dashboard/chat?id=${conv.id}`}
                  className="block py-2 border-b border-gray-100 last:border-0 hover:bg-gray-50"
                >
                  <p className="font-medium text-gray-900">{conv.title}</p>
                  <p className="text-sm text-gray-500">
                    {conv.messageCount} messages • {new Date(conv.updatedAt).toLocaleDateString()}
                  </p>
                </Link>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
