'use client';

import { useState, useEffect, useRef } from 'react';
import {
  useConversations,
  useConversationMessages,
  useCreateConversation,
  useSendMessage,
  useDeleteConversation,
} from '@/hooks/useConversations';
import { useAuthStore } from '@/store/auth';

export default function ChatPage() {
  const { user } = useAuthStore();
  const [selectedConversationId, setSelectedConversationId] = useState<number | null>(null);
  const [message, setMessage] = useState('');
  const [newConversationTitle, setNewConversationTitle] = useState('');
  const [showNewConversationModal, setShowNewConversationModal] = useState(false);
  const messagesEndRef = useRef<HTMLDivElement>(null);

  const { data: conversationsData } = useConversations(0, 50);
  const { data: messagesData, refetch: refetchMessages } = useConversationMessages(
    selectedConversationId || 0
  );
  const createConversation = useCreateConversation();
  const sendMessage = useSendMessage();
  const deleteConversation = useDeleteConversation();

  const scrollToBottom = () => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  };

  useEffect(() => {
    scrollToBottom();
  }, [messagesData]);

  // Auto-select first conversation
  useEffect(() => {
    if (!selectedConversationId && conversationsData?.content && conversationsData.content.length > 0) {
      setSelectedConversationId(conversationsData.content[0].id);
    }
  }, [conversationsData, selectedConversationId]);

  const handleCreateConversation = async () => {
    if (!newConversationTitle.trim()) return;

    try {
      const newConv = await createConversation.mutateAsync(newConversationTitle);
      setSelectedConversationId(newConv.id);
      setNewConversationTitle('');
      setShowNewConversationModal(false);
    } catch (error) {
      console.error('Failed to create conversation:', error);
    }
  };

  const handleSendMessage = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!message.trim() || !selectedConversationId) return;

    try {
      await sendMessage.mutateAsync({
        conversationId: selectedConversationId,
        message: message.trim(),
      });
      setMessage('');
      // Refetch messages to show new message and AI response
      setTimeout(() => refetchMessages(), 500);
    } catch (error) {
      console.error('Failed to send message:', error);
    }
  };

  const handleDeleteConversation = async (id: number) => {
    if (confirm('Bạn có chắc muốn xóa cuộc hội thoại này?')) {
      try {
        await deleteConversation.mutateAsync(id);
        if (selectedConversationId === id) {
          setSelectedConversationId(null);
        }
      } catch (error) {
        console.error('Failed to delete conversation:', error);
      }
    }
  };

  const formatTime = (timestamp: string) => {
    return new Date(timestamp).toLocaleTimeString('vi-VN', {
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  return (
    <div className="flex h-[calc(100vh-4rem)] bg-gray-50">
      {/* Conversations Sidebar */}
      <div className="w-80 bg-white border-r flex flex-col">
        <div className="p-4 border-b">
          <button
            onClick={() => setShowNewConversationModal(true)}
            className="w-full px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 flex items-center justify-center"
          >
            <svg className="w-5 h-5 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
            </svg>
            Cuộc hội thoại mới
          </button>
        </div>

        <div className="flex-1 overflow-y-auto">
          {conversationsData?.content && conversationsData.content.length > 0 ? (
            <div className="divide-y">
              {conversationsData.content.map((conv) => (
                <div
                  key={conv.id}
                  onClick={() => setSelectedConversationId(conv.id)}
                  className={`p-4 cursor-pointer hover:bg-gray-50 transition-colors ${
                    selectedConversationId === conv.id ? 'bg-blue-50 border-l-4 border-blue-600' : ''
                  }`}
                >
                  <div className="flex justify-between items-start">
                    <div className="flex-1 min-w-0">
                      <div className="flex items-center space-x-2">
                        <h3 className="font-medium text-gray-900 truncate">{conv.title}</h3>
                        <span className="flex-shrink-0 px-2 py-0.5 text-xs font-medium text-blue-600 bg-blue-100 rounded-full">
                          {conv.messageCount}
                        </span>
                      </div>
                      <p className="text-xs text-gray-500 mt-1">
                        Cập nhật: {new Date(conv.updatedAt).toLocaleString('vi-VN', {
                          day: '2-digit',
                          month: '2-digit',
                          year: 'numeric',
                          hour: '2-digit',
                          minute: '2-digit'
                        })}
                      </p>
                    </div>
                    <button
                      onClick={(e) => {
                        e.stopPropagation();
                        handleDeleteConversation(conv.id);
                      }}
                      className="ml-2 text-gray-400 hover:text-red-600"
                    >
                      <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth={2}
                          d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
                        />
                      </svg>
                    </button>
                  </div>
                </div>
              ))}
            </div>
          ) : (
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
                  d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"
                />
              </svg>
              <p className="mt-2">Chưa có cuộc hội thoại</p>
              <p className="text-sm">Tạo cuộc hội thoại mới để bắt đầu</p>
            </div>
          )}
        </div>
      </div>

      {/* Chat Area */}
      <div className="flex-1 flex flex-col">
        {selectedConversationId ? (
          <>
            {/* Chat Header */}
            <div className="bg-white border-b px-6 py-4">
              <h2 className="text-xl font-semibold text-gray-900">
                {conversationsData?.content.find((c) => c.id === selectedConversationId)?.title}
              </h2>
            </div>

            {/* Messages Area */}
            <div className="flex-1 overflow-y-auto p-6 space-y-4">
              {messagesData && messagesData.length > 0 ? (
                <>
                  {messagesData.map((msg) => (
                    <div
                      key={msg.id}
                      className={`flex ${msg.sender === 'USER' ? 'justify-end' : 'justify-start'}`}
                    >
                      <div
                        className={`max-w-[70%] rounded-lg px-4 py-3 ${
                          msg.sender === 'USER'
                            ? 'bg-blue-600 text-white'
                            : 'bg-white border border-gray-200 text-gray-900'
                        }`}
                      >
                        <div className="flex items-start space-x-2">
                          {msg.sender === 'AI' && (
                            <div className="flex-shrink-0 w-6 h-6 bg-gradient-to-br from-purple-500 to-blue-500 rounded-full flex items-center justify-center">
                              <svg
                                className="w-4 h-4 text-white"
                                fill="currentColor"
                                viewBox="0 0 20 20"
                              >
                                <path d="M13 6a3 3 0 11-6 0 3 3 0 016 0zM18 8a2 2 0 11-4 0 2 2 0 014 0zM14 15a4 4 0 00-8 0v3h8v-3zM6 8a2 2 0 11-4 0 2 2 0 014 0zM16 18v-3a5.972 5.972 0 00-.75-2.906A3.005 3.005 0 0119 15v3h-3zM4.75 12.094A5.973 5.973 0 004 15v3H1v-3a3 3 0 013.75-2.906z" />
                              </svg>
                            </div>
                          )}
                          <div className="flex-1">
                            <p className="whitespace-pre-wrap break-words">{msg.content}</p>
                            
                            {/* Document Sources */}
                            {msg.sources && msg.sources.length > 0 && (
                              <div className="mt-3 pt-3 border-t border-gray-200">
                                <p className="text-xs font-semibold text-gray-600 mb-2">📚 Nguồn tham khảo:</p>
                                <div className="space-y-1">
                                  {msg.sources.map((source, idx) => (
                                    <div key={idx} className="text-xs bg-gray-50 rounded px-2 py-1 flex items-center space-x-1">
                                      <svg className="w-3 h-3 text-gray-500" fill="currentColor" viewBox="0 0 20 20">
                                        <path fillRule="evenodd" d="M4 4a2 2 0 012-2h4.586A2 2 0 0112 2.586L15.414 6A2 2 0 0116 7.414V16a2 2 0 01-2 2H6a2 2 0 01-2-2V4z" clipRule="evenodd" />
                                      </svg>
                                      <span className="text-gray-700">{source.documentName}</span>
                                      {source.pageNumber && (
                                        <span className="text-gray-500">- Trang {source.pageNumber}</span>
                                      )}
                                      {source.score && (
                                        <span className="text-gray-500 ml-auto">({Math.round(source.score * 100)}%)</span>
                                      )}
                                    </div>
                                  ))}
                                </div>
                              </div>
                            )}
                            
                            <p
                              className={`text-xs mt-1 ${
                                msg.sender === 'USER' ? 'text-blue-100' : 'text-gray-500'
                              }`}
                            >
                              {formatTime(msg.timestamp)}
                            </p>
                          </div>
                        </div>
                      </div>
                    </div>
                  ))}
                  <div ref={messagesEndRef} />
                </>
              ) : (
                <div className="flex items-center justify-center h-full text-gray-500">
                  <div className="text-center">
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
                        d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z"
                      />
                    </svg>
                    <p className="mt-2">Bắt đầu cuộc hội thoại</p>
                    <p className="text-sm">Gửi tin nhắn đầu tiên của bạn</p>
                  </div>
                </div>
              )}
            </div>

            {/* Input Area */}
            <div className="bg-white border-t px-6 py-4">
              <form onSubmit={handleSendMessage} className="flex space-x-4">
                <input
                  type="text"
                  value={message}
                  onChange={(e) => setMessage(e.target.value)}
                  placeholder="Nhập tin nhắn của bạn..."
                  className="flex-1 px-4 py-3 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                  disabled={sendMessage.isPending}
                />
                <button
                  type="submit"
                  disabled={!message.trim() || sendMessage.isPending}
                  className="px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed flex items-center"
                >
                  {sendMessage.isPending ? (
                    <div className="w-5 h-5 border-2 border-white border-t-transparent rounded-full animate-spin" />
                  ) : (
                    <>
                      <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                        <path
                          strokeLinecap="round"
                          strokeLinejoin="round"
                          strokeWidth={2}
                          d="M12 19l9 2-9-18-9 18 9-2zm0 0v-8"
                        />
                      </svg>
                    </>
                  )}
                </button>
              </form>
            </div>
          </>
        ) : (
          <div className="flex items-center justify-center h-full text-gray-500">
            <div className="text-center">
              <svg
                className="mx-auto h-16 w-16 text-gray-400"
                fill="none"
                stroke="currentColor"
                viewBox="0 0 24 24"
              >
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"
                />
              </svg>
              <p className="mt-4 text-lg">Chọn một cuộc hội thoại</p>
              <p className="text-sm">Hoặc tạo cuộc hội thoại mới để bắt đầu</p>
            </div>
          </div>
        )}
      </div>

      {/* New Conversation Modal */}
      {showNewConversationModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="bg-white rounded-lg p-6 w-full max-w-md">
            <h3 className="text-xl font-semibold mb-4">Cuộc hội thoại mới</h3>
            <input
              type="text"
              value={newConversationTitle}
              onChange={(e) => setNewConversationTitle(e.target.value)}
              placeholder="Nhập tiêu đề cuộc hội thoại..."
              className="w-full px-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 mb-4"
              autoFocus
            />
            <div className="flex justify-end space-x-3">
              <button
                onClick={() => {
                  setShowNewConversationModal(false);
                  setNewConversationTitle('');
                }}
                className="px-4 py-2 text-gray-700 hover:bg-gray-100 rounded-lg"
              >
                Hủy
              </button>
              <button
                onClick={handleCreateConversation}
                disabled={!newConversationTitle.trim() || createConversation.isPending}
                className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:opacity-50 disabled:cursor-not-allowed"
              >
                {createConversation.isPending ? 'Đang tạo...' : 'Tạo'}
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}
