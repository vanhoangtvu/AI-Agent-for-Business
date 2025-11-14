'use client';

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuthStore } from '@/store/auth';
import { useCartStore } from '@/store/cart';
import Link from 'next/link';

export default function DashboardLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const router = useRouter();
  const { isAuthenticated, user, logout } = useAuthStore();
  const cartItemCount = useCartStore((state) => state.getItemCount());

  useEffect(() => {
    if (!isAuthenticated) {
      router.push('/login');
    }
  }, [isAuthenticated, router]);

  if (!isAuthenticated) {
    return null;
  }

  const handleLogout = () => {
    logout();
    router.push('/login');
  };

  return (
    <div className="min-h-screen bg-gray-100">
      {/* Header */}
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center py-4">
            <div className="flex items-center space-x-8">
              <h1 className="text-xl font-bold text-gray-900">
                AI Agent for Business
              </h1>
              <nav className="hidden md:flex space-x-4">
                <Link
                  href="/dashboard"
                  className="text-gray-700 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium"
                >
                  Dashboard
                </Link>
                <Link
                  href="/dashboard/products"
                  className="text-gray-700 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium"
                >
                  Sản phẩm
                </Link>
                <Link
                  href="/dashboard/orders"
                  className="text-gray-700 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium"
                >
                  Đơn hàng
                </Link>
                <Link
                  href="/dashboard/documents"
                  className="text-gray-700 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium"
                >
                  Documents
                </Link>
                <Link
                  href="/dashboard/chat"
                  className="text-gray-700 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium"
                >
                  Chat
                </Link>
                <Link
                  href="/dashboard/profile"
                  className="text-gray-700 hover:text-gray-900 px-3 py-2 rounded-md text-sm font-medium"
                >
                  Profile
                </Link>
              </nav>
            </div>
            <div className="flex items-center space-x-4">
              {/* Cart Icon */}
              <Link href="/dashboard/cart" className="relative">
                <svg className="w-6 h-6 text-gray-700 hover:text-gray-900" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
                </svg>
                {cartItemCount > 0 && (
                  <span className="absolute -top-2 -right-2 bg-red-600 text-white text-xs rounded-full w-5 h-5 flex items-center justify-center">
                    {cartItemCount}
                  </span>
                )}
              </Link>
              <span className="text-sm text-gray-700">
                {user?.username} ({user?.roles.join(', ')})
              </span>
              <button
                onClick={handleLogout}
                className="bg-red-600 text-white px-4 py-2 rounded-md text-sm hover:bg-red-700"
              >
                Logout
              </button>
            </div>
          </div>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {children}
      </main>
    </div>
  );
}
