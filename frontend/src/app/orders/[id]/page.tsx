'use client';

import { useState, useEffect } from 'react';
import { useRouter, useParams } from 'next/navigation';
import Link from 'next/link';
import CustomerLayout from '@/components/CustomerLayout';
import { apiClient, OrderDTO } from '@/lib/api';

export default function OrderDetailPage() {
  const router = useRouter();
  const params = useParams();
  const orderId = Number(params.id);
  
  const [order, setOrder] = useState<OrderDTO | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [editingAddress, setEditingAddress] = useState(false);
  const [newAddress, setNewAddress] = useState('');

  useEffect(() => {
    const token = apiClient.getAuthToken();
    if (!token) {
      router.push('/login');
      return;
    }
    if (orderId) {
      loadOrder();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [orderId]);

  const loadOrder = async () => {
    setLoading(true);
    setError('');
    try {
      const data = await apiClient.getOrderById(orderId);
      setOrder(data);
      setNewAddress(data.shippingAddress);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Không thể tải đơn hàng');
    } finally {
      setLoading(false);
    }
  };

  const handleUpdateAddress = async () => {
    if (!order) return;
    try {
      const updated = await apiClient.updateShippingAddress(order.id, newAddress);
      setOrder(updated);
      setEditingAddress(false);
      alert('Đã cập nhật địa chỉ giao hàng');
    } catch (err) {
      alert(err instanceof Error ? err.message : 'Không thể cập nhật địa chỉ');
    }
  };

  const handleCancel = async () => {
    if (!order || !confirm('Bạn có chắc muốn hủy đơn hàng này?')) return;
    try {
      await apiClient.cancelOrder(order.id);
      loadOrder();
      alert('Đã hủy đơn hàng');
    } catch (err) {
      alert(err instanceof Error ? err.message : 'Không thể hủy đơn hàng');
    }
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'PENDING': return 'bg-yellow-100 text-yellow-800 dark:bg-yellow-900/30 dark:text-yellow-400';
      case 'CONFIRMED': return 'bg-blue-100 text-blue-800 dark:bg-blue-900/30 dark:text-blue-400';
      case 'SHIPPING': return 'bg-purple-100 text-purple-800 dark:bg-purple-900/30 dark:text-purple-400';
      case 'DELIVERED': return 'bg-green-100 text-green-800 dark:bg-green-900/30 dark:text-green-400';
      case 'CANCELLED': return 'bg-red-100 text-red-800 dark:bg-red-900/30 dark:text-red-400';
      default: return 'bg-gray-100 text-gray-800 dark:bg-gray-700 dark:text-gray-400';
    }
  };

  const getStatusText = (status: string) => {
    switch (status) {
      case 'PENDING': return 'Chờ xác nhận';
      case 'CONFIRMED': return 'Đã xác nhận';
      case 'SHIPPING': return 'Đang giao';
      case 'DELIVERED': return 'Đã giao';
      case 'CANCELLED': return 'Đã hủy';
      default: return status;
    }
  };

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleString('vi-VN');
  };

  if (loading) {
    return (
      <CustomerLayout>
        <div className="container mx-auto px-4 py-8 flex items-center justify-center min-h-[50vh]">
          <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-blue-600"></div>
        </div>
      </CustomerLayout>
    );
  }

  if (error || !order) {
    return (
      <CustomerLayout>
        <div className="container mx-auto px-4 py-8 text-center">
          <p className="text-red-600 dark:text-red-400 mb-4">{error || 'Không tìm thấy đơn hàng'}</p>
          <Link href="/orders" className="px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 inline-block">
            Quay lại danh sách đơn hàng
          </Link>
        </div>
      </CustomerLayout>
    );
  }

  return (
    <CustomerLayout>
      <div className="container mx-auto px-4 py-8 max-w-4xl">
        <div className="flex items-center gap-4 mb-8">
          <Link href="/orders" className="text-blue-600 dark:text-blue-400 hover:underline">
            ← Quay lại
          </Link>
          <h1 className="text-3xl font-bold">Chi tiết đơn hàng #{order.id}</h1>
        </div>

        {/* Order Status */}
        <div className="bg-white dark:bg-gray-800 rounded-2xl shadow-lg p-6 mb-6">
          <div className="flex items-center justify-between mb-4">
            <span className={`px-4 py-2 rounded-full text-lg font-semibold ${getStatusColor(order.status)}`}>
              {getStatusText(order.status)}
            </span>
            <p className="text-sm text-gray-600 dark:text-gray-400">
              Đặt ngày: {formatDate(order.createdAt)}
            </p>
          </div>
        </div>

        {/* Order Items */}
        <div className="bg-white dark:bg-gray-800 rounded-2xl shadow-lg p-6 mb-6">
          <h2 className="text-xl font-bold mb-4">Sản phẩm</h2>
          <div className="space-y-4">
            {order.orderItems.map((item) => (
              <div key={item.id} className="flex justify-between items-center py-3 border-b border-gray-200 dark:border-gray-700 last:border-0">
                <div>
                  <p className="font-semibold">{item.productName}</p>
                  <p className="text-sm text-gray-600 dark:text-gray-400">
                    Số lượng: {item.quantity} × {formatPrice(item.productPrice)}
                  </p>
                </div>
                <p className="text-lg font-bold text-blue-600 dark:text-blue-400">
                  {formatPrice(item.subtotal)}
                </p>
              </div>
            ))}
          </div>
          <div className="border-t border-gray-200 dark:border-gray-700 pt-4 mt-4">
            <div className="flex justify-between text-xl font-bold">
              <span>Tổng cộng:</span>
              <span className="text-blue-600 dark:text-blue-400">{formatPrice(order.totalAmount)}</span>
            </div>
          </div>
        </div>

        {/* Customer Info */}
        <div className="bg-white dark:bg-gray-800 rounded-2xl shadow-lg p-6 mb-6">
          <h2 className="text-xl font-bold mb-4">Thông tin khách hàng</h2>
          <div className="space-y-2 text-sm">
            <p><span className="font-semibold">Họ tên:</span> {order.customerName}</p>
            <p><span className="font-semibold">Email:</span> {order.customerEmail}</p>
            <p><span className="font-semibold">Số điện thoại:</span> {order.customerPhone}</p>
          </div>
        </div>

        {/* Shipping Address */}
        <div className="bg-white dark:bg-gray-800 rounded-2xl shadow-lg p-6 mb-6">
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-xl font-bold">Địa chỉ giao hàng</h2>
            {(order.status === 'PENDING' || order.status === 'CONFIRMED') && !editingAddress && (
              <button
                onClick={() => setEditingAddress(true)}
                className="text-blue-600 dark:text-blue-400 hover:underline text-sm"
              >
                Chỉnh sửa
              </button>
            )}
          </div>
          {editingAddress ? (
            <div>
              <textarea
                value={newAddress}
                onChange={(e) => setNewAddress(e.target.value)}
                className="w-full px-4 py-3 border border-gray-300 dark:border-gray-600 rounded-lg focus:ring-2 focus:ring-blue-500 dark:bg-gray-700 mb-3"
                rows={3}
              />
              <div className="flex gap-3">
                <button
                  onClick={handleUpdateAddress}
                  className="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
                >
                  Lưu
                </button>
                <button
                  onClick={() => {
                    setEditingAddress(false);
                    setNewAddress(order.shippingAddress);
                  }}
                  className="px-4 py-2 border border-gray-300 dark:border-gray-600 rounded-lg hover:bg-gray-50 dark:hover:bg-gray-700"
                >
                  Hủy
                </button>
              </div>
            </div>
          ) : (
            <p className="text-gray-600 dark:text-gray-400">{order.shippingAddress}</p>
          )}
        </div>

        {/* Note */}
        {order.note && (
          <div className="bg-white dark:bg-gray-800 rounded-2xl shadow-lg p-6 mb-6">
            <h2 className="text-xl font-bold mb-4">Ghi chú</h2>
            <p className="text-gray-600 dark:text-gray-400">{order.note}</p>
          </div>
        )}

        {/* Actions */}
        {(order.status === 'PENDING' || order.status === 'CONFIRMED') && (
          <div className="flex justify-end">
            <button
              onClick={handleCancel}
              className="px-6 py-3 border-2 border-red-200 dark:border-red-800 text-red-600 dark:text-red-400 rounded-lg hover:bg-red-50 dark:hover:bg-red-900/20 transition-colors font-semibold"
            >
              Hủy đơn hàng
            </button>
          </div>
        )}
      </div>
    </CustomerLayout>
  );
}
