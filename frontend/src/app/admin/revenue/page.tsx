'use client';

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import Link from 'next/link';
import { apiClient } from '@/lib/api';

interface RevenueData {
  date: string;
  revenue: number;
  orderCount: number;
}

export default function RevenueManagement() {
  const router = useRouter();
  const [userData, setUserData] = useState<any>(null);
  const [loading, setLoading] = useState(true);
  const [period, setPeriod] = useState<'daily' | 'weekly' | 'monthly'>('daily');
  const [duration, setDuration] = useState(7);
  const [revenueData, setRevenueData] = useState<RevenueData[]>([]);
  const [stats, setStats] = useState<any>(null);

  useEffect(() => {
    if (!apiClient.isAuthenticated()) {
      router.push('/login');
      return;
    }

    const user = apiClient.getUserData();
    if (!user || (user.role !== 'ADMIN' && user.role !== 'BUSINESS')) {
      router.push('/shop');
      return;
    }

    setUserData(user);
    loadData(user);
  }, [router, period, duration]);

  const loadData = async (user: any) => {
    try {
      setLoading(true);
      
      // Load stats
      const statsData = user.role === 'ADMIN' 
        ? await apiClient.getAdminStats() 
        : await apiClient.getBusinessStats();
      setStats(statsData);

      // Load revenue data based on period
      let data: RevenueData[] = [];
      if (user.role === 'ADMIN') {
        if (period === 'daily') {
          data = await apiClient.getAdminDailyRevenue(duration);
        } else if (period === 'weekly') {
          data = await apiClient.getAdminWeeklyRevenue(duration);
        } else {
          data = await apiClient.getAdminMonthlyRevenue(duration);
        }
      } else {
        if (period === 'daily') {
          data = await apiClient.getDailyRevenue(duration);
        } else if (period === 'weekly') {
          data = await apiClient.getWeeklyRevenue(duration);
        } else {
          data = await apiClient.getMonthlyRevenue(duration);
        }
      }
      
      setRevenueData(data);
    } catch (error) {
      console.error('Failed to load revenue data:', error);
      alert('Không thể tải dữ liệu doanh thu. Vui lòng thử lại.');
    } finally {
      setLoading(false);
    }
  };

  const totalRevenue = revenueData.reduce((sum, item) => sum + item.revenue, 0);
  const totalOrders = revenueData.reduce((sum, item) => sum + item.orderCount, 0);
  const avgRevenue = revenueData.length > 0 ? totalRevenue / revenueData.length : 0;
  const maxRevenue = Math.max(...revenueData.map(item => item.revenue), 0);

  if (loading || !userData) {
    return (
      <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-gray-50 to-gray-100 dark:from-gray-900 dark:to-gray-800">
        <div className="text-center">
          <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-blue-600 mx-auto mb-4"></div>
          <p className="text-gray-600 dark:text-gray-400">Đang tải...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-br from-gray-50 to-gray-100 dark:from-gray-900 dark:to-gray-800">
      {/* Header */}
      <header className="bg-white dark:bg-gray-800 shadow-lg sticky top-0 z-50">
        <div className="container mx-auto px-4 py-4">
          <div className="flex items-center justify-between">
            <h1 className="text-2xl font-bold bg-gradient-to-r from-purple-600 to-pink-600 bg-clip-text text-transparent">
              Báo cáo Doanh thu
            </h1>
            <div className="flex items-center gap-4">
              <Link href="/shop" className="px-4 py-2 text-gray-700 dark:text-gray-300 hover:text-blue-600 transition-colors">
                Cửa hàng
              </Link>
              <button onClick={() => apiClient.logout()} className="px-4 py-2 text-red-600 hover:text-red-700 transition-colors">
                Đăng xuất
              </button>
            </div>
          </div>
        </div>
      </header>

      {/* Navigation */}
      <div className="bg-white dark:bg-gray-800 shadow-md">
        <div className="container mx-auto px-4">
          <div className="flex gap-2 overflow-x-auto">
            <Link href="/admin" className="px-6 py-4 text-gray-700 dark:text-gray-300 hover:text-purple-600 transition-colors whitespace-nowrap">
              Tổng quan
            </Link>
            <Link href="/admin/products" className="px-6 py-4 text-gray-700 dark:text-gray-300 hover:text-purple-600 transition-colors whitespace-nowrap">
              Sản phẩm
            </Link>
            <Link href="/admin/orders" className="px-6 py-4 text-gray-700 dark:text-gray-300 hover:text-purple-600 transition-colors whitespace-nowrap">
              Đơn hàng
            </Link>
            <Link href="/admin/categories" className="px-6 py-4 text-gray-700 dark:text-gray-300 hover:text-purple-600 transition-colors whitespace-nowrap">
              Danh mục
            </Link>
            <Link href="/admin/revenue" className="px-6 py-4 font-semibold border-b-4 border-purple-600 text-purple-600 whitespace-nowrap">
              Doanh thu
            </Link>
            {userData.role === 'ADMIN' && (
              <Link href="/admin/users" className="px-6 py-4 text-gray-700 dark:text-gray-300 hover:text-purple-600 transition-colors whitespace-nowrap">
                Người dùng
              </Link>
            )}
          </div>
        </div>
      </div>

      {/* Main Content */}
      <main className="container mx-auto px-4 py-8">
        {/* Filters */}
        <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 mb-6">
          <div className="flex flex-col md:flex-row gap-4 items-center">
            <div className="flex gap-2">
              <button
                onClick={() => setPeriod('daily')}
                className={`px-4 py-2 rounded-lg font-semibold transition-colors ${
                  period === 'daily' 
                    ? 'bg-purple-600 text-white' 
                    : 'bg-gray-200 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-300 dark:hover:bg-gray-600'
                }`}
              >
                Theo ngày
              </button>
              <button
                onClick={() => setPeriod('weekly')}
                className={`px-4 py-2 rounded-lg font-semibold transition-colors ${
                  period === 'weekly' 
                    ? 'bg-purple-600 text-white' 
                    : 'bg-gray-200 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-300 dark:hover:bg-gray-600'
                }`}
              >
                Theo tuần
              </button>
              <button
                onClick={() => setPeriod('monthly')}
                className={`px-4 py-2 rounded-lg font-semibold transition-colors ${
                  period === 'monthly' 
                    ? 'bg-purple-600 text-white' 
                    : 'bg-gray-200 dark:bg-gray-700 text-gray-700 dark:text-gray-300 hover:bg-gray-300 dark:hover:bg-gray-600'
                }`}
              >
                Theo tháng
              </button>
            </div>
            <div className="flex items-center gap-2">
              <label className="text-gray-700 dark:text-gray-300 font-semibold">
                {period === 'daily' ? 'Số ngày:' : period === 'weekly' ? 'Số tuần:' : 'Số tháng:'}
              </label>
              <select
                value={duration}
                onChange={(e) => setDuration(Number(e.target.value))}
                className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-purple-500 dark:bg-gray-700 dark:text-white"
              >
                {period === 'daily' && (
                  <>
                    <option value={7}>7</option>
                    <option value={14}>14</option>
                    <option value={30}>30</option>
                    <option value={60}>60</option>
                    <option value={90}>90</option>
                  </>
                )}
                {period === 'weekly' && (
                  <>
                    <option value={4}>4</option>
                    <option value={8}>8</option>
                    <option value={12}>12</option>
                    <option value={24}>24</option>
                  </>
                )}
                {period === 'monthly' && (
                  <>
                    <option value={3}>3</option>
                    <option value={6}>6</option>
                    <option value={12}>12</option>
                    <option value={24}>24</option>
                  </>
                )}
              </select>
            </div>
          </div>
        </div>

        {/* Summary Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 border-l-4 border-blue-500">
            <p className="text-gray-600 dark:text-gray-400 text-sm font-medium mb-2">Tổng doanh thu</p>
            <p className="text-2xl font-bold text-gray-800 dark:text-white">
              {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(totalRevenue)}
            </p>
          </div>
          <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 border-l-4 border-green-500">
            <p className="text-gray-600 dark:text-gray-400 text-sm font-medium mb-2">Tổng đơn hàng</p>
            <p className="text-2xl font-bold text-gray-800 dark:text-white">{totalOrders}</p>
          </div>
          <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 border-l-4 border-purple-500">
            <p className="text-gray-600 dark:text-gray-400 text-sm font-medium mb-2">Doanh thu trung bình</p>
            <p className="text-2xl font-bold text-gray-800 dark:text-white">
              {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(avgRevenue)}
            </p>
          </div>
          <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 border-l-4 border-yellow-500">
            <p className="text-gray-600 dark:text-gray-400 text-sm font-medium mb-2">Doanh thu cao nhất</p>
            <p className="text-2xl font-bold text-gray-800 dark:text-white">
              {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(maxRevenue)}
            </p>
          </div>
        </div>

        {/* Revenue Chart */}
        <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg p-6 mb-8">
          <h2 className="text-xl font-bold text-gray-800 dark:text-white mb-6">Biểu đồ doanh thu</h2>
          <div className="space-y-4">
            {revenueData.map((item, index) => {
              const percentage = maxRevenue > 0 ? (item.revenue / maxRevenue) * 100 : 0;
              return (
                <div key={index} className="space-y-2">
                  <div className="flex items-center justify-between text-sm">
                    <span className="font-semibold text-gray-700 dark:text-gray-300">
                      {new Date(item.date).toLocaleDateString('vi-VN')}
                    </span>
                    <div className="flex items-center gap-4">
                      <span className="text-gray-600 dark:text-gray-400">
                        {item.orderCount} đơn
                      </span>
                      <span className="font-bold text-purple-600 dark:text-purple-400">
                        {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(item.revenue)}
                      </span>
                    </div>
                  </div>
                  <div className="w-full bg-gray-200 dark:bg-gray-700 rounded-full h-4 overflow-hidden">
                    <div
                      className="h-full bg-gradient-to-r from-purple-600 to-pink-600 rounded-full transition-all duration-500 flex items-center justify-end px-2"
                      style={{ width: `${percentage}%` }}
                    >
                      {percentage > 20 && (
                        <span className="text-xs font-bold text-white">
                          {percentage.toFixed(1)}%
                        </span>
                      )}
                    </div>
                  </div>
                </div>
              );
            })}
          </div>
          {revenueData.length === 0 && (
            <div className="text-center py-12">
              <svg className="w-16 h-16 text-gray-400 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
              </svg>
              <p className="text-gray-500 dark:text-gray-400">Không có dữ liệu doanh thu</p>
            </div>
          )}
        </div>

        {/* Revenue Table */}
        <div className="bg-white dark:bg-gray-800 rounded-xl shadow-lg overflow-hidden">
          <div className="p-6 border-b border-gray-200 dark:border-gray-700">
            <h2 className="text-xl font-bold text-gray-800 dark:text-white">Chi tiết doanh thu</h2>
          </div>
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-gray-50 dark:bg-gray-700">
                <tr>
                  <th className="px-6 py-4 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Ngày</th>
                  <th className="px-6 py-4 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Số đơn hàng</th>
                  <th className="px-6 py-4 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Doanh thu</th>
                  <th className="px-6 py-4 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Trung bình/đơn</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200 dark:divide-gray-700">
                {revenueData.map((item, index) => (
                  <tr key={index} className="hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors">
                    <td className="px-6 py-4 font-semibold text-gray-800 dark:text-white">
                      {new Date(item.date).toLocaleDateString('vi-VN', { 
                        year: 'numeric', 
                        month: 'long', 
                        day: 'numeric' 
                      })}
                    </td>
                    <td className="px-6 py-4 text-gray-700 dark:text-gray-300">{item.orderCount}</td>
                    <td className="px-6 py-4 font-semibold text-purple-600 dark:text-purple-400">
                      {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(item.revenue)}
                    </td>
                    <td className="px-6 py-4 text-gray-700 dark:text-gray-300">
                      {new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(
                        item.orderCount > 0 ? item.revenue / item.orderCount : 0
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </main>
    </div>
  );
}
