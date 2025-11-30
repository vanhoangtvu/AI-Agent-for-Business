'use client';

import { useState, useEffect } from 'react';
import { useRouter, useParams } from 'next/navigation';
import Link from 'next/link';
import { apiClient, ProductDTO } from '@/lib/api';

export default function ProductDetailPage() {
  const router = useRouter();
  const params = useParams();
  const productId = params.id as string;
  
  const [product, setProduct] = useState<ProductDTO | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [quantity, setQuantity] = useState(1);
  const [selectedImage, setSelectedImage] = useState(0);
  const [addingToCart, setAddingToCart] = useState(false);
  const [success, setSuccess] = useState('');

  useEffect(() => {
    loadProduct();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [productId]);

  const loadProduct = async () => {
    setLoading(true);
    setError('');
    try {
      const data = await apiClient.getProductById(Number(productId));
      setProduct(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Không thể tải thông tin sản phẩm');
    } finally {
      setLoading(false);
    }
  };

  const handleAddToCart = async (e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();
    console.log('Add to cart clicked');
    
    const token = apiClient.getAuthToken();
    if (!token) {
      router.push('/login');
      return;
    }

    setAddingToCart(true);
    setError('');
    setSuccess('');
    try {
      await apiClient.addToCart(Number(productId), quantity);
      setSuccess('Đã thêm vào giỏ hàng!');
      setTimeout(() => setSuccess(''), 3000);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Không thể thêm vào giỏ hàng');
    } finally {
      setAddingToCart(false);
    }
  };

  const handleBuyNow = async (e: React.MouseEvent) => {
    e.preventDefault();
    e.stopPropagation();
    console.log('Buy now clicked');
    
    const token = apiClient.getAuthToken();
    if (!token) {
      router.push('/login');
      return;
    }

    setAddingToCart(true);
    try {
      await apiClient.addToCart(Number(productId), quantity);
      router.push('/cart');
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Không thể thêm vào giỏ hàng');
    } finally {
      setAddingToCart(false);
    }
  };

  const incrementQuantity = () => {
    if (product && quantity < product.quantity) {
      setQuantity(quantity + 1);
    }
  };

  const decrementQuantity = () => {
    if (quantity > 1) {
      setQuantity(quantity - 1);
    }
  };

  const formatPrice = (price: number) => {
    return new Intl.NumberFormat('vi-VN', {
      style: 'currency',
      currency: 'VND',
    }).format(price);
  };

  if (loading) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-indigo-50 dark:from-gray-900 dark:via-gray-800 dark:to-gray-900 flex items-center justify-center">
        <div className="text-center">
          <div className="animate-spin rounded-full h-16 w-16 border-b-2 border-blue-600 mx-auto"></div>
          <p className="mt-4 text-gray-600 dark:text-gray-400">Đang tải thông tin sản phẩm...</p>
        </div>
      </div>
    );
  }

  if (error && !product) {
    return (
      <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-indigo-50 dark:from-gray-900 dark:via-gray-800 dark:to-gray-900 flex items-center justify-center p-4">
        <div className="text-center">
          <svg className="w-24 h-24 text-red-500 mx-auto mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
          <h2 className="text-2xl font-bold mb-2">Không tìm thấy sản phẩm</h2>
          <p className="text-gray-600 dark:text-gray-400 mb-6">{error}</p>
          <Link
            href="/shop"
            className="inline-block px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors"
          >
            Quay lại cửa hàng
          </Link>
        </div>
      </div>
    );
  }

  if (!product) return null;

  return (
    <div className="min-h-screen bg-gradient-to-br from-blue-50 via-white to-indigo-50 dark:from-gray-900 dark:via-gray-800 dark:to-gray-900">
      {/* Header */}
      <header className="border-b border-gray-200 dark:border-gray-700 bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm sticky top-0 z-50">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex items-center justify-between">
            <Link href="/shop" className="flex items-center gap-2">
              <div className="w-8 h-8 bg-gradient-to-br from-blue-600 to-indigo-600 rounded-lg flex items-center justify-center">
                <svg className="w-5 h-5 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9.75 17L9 20l-1 1h8l-1-1-.75-3M3 13h18M5 17h14a2 2 0 002-2V5a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                </svg>
              </div>
              <span className="text-xl font-bold bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent">
                AI Agent Shop
              </span>
            </Link>
            <div className="flex items-center space-x-4">
              <Link href="/shop" className="text-gray-700 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400 transition-colors">
                Cửa hàng
              </Link>
              <Link href="/cart" className="text-gray-700 dark:text-gray-300 hover:text-blue-600 dark:hover:text-blue-400 transition-colors">
                <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
                </svg>
              </Link>
            </div>
          </div>
        </div>
      </header>

      {/* Breadcrumb */}
      <div className="bg-white/80 dark:bg-gray-800/80 backdrop-blur-sm border-b border-gray-200 dark:border-gray-700">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-3">
          <div className="flex items-center space-x-2 text-sm">
            <Link href="/shop" className="text-blue-600 dark:text-blue-400 hover:text-blue-700 dark:hover:text-blue-300">Cửa hàng</Link>
            <span className="text-gray-400 dark:text-gray-600">/</span>
            <Link href={`/shop?category=${product.categoryId}`} className="text-blue-600 dark:text-blue-400 hover:text-blue-700 dark:hover:text-blue-300">
              {product.categoryName}
            </Link>
            <span className="text-gray-400 dark:text-gray-600">/</span>
            <span className="text-gray-600 dark:text-gray-400">{product.name}</span>
          </div>
        </div>
      </div>

      {/* Product Detail */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Success Message */}
        {success && (
          <div className="mb-6 p-4 bg-green-50 border border-green-200 rounded-lg flex items-center">
            <svg className="w-5 h-5 text-green-600 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
            </svg>
            <span className="text-green-800">{success}</span>
          </div>
        )}

        {/* Error Message */}
        {error && (
          <div className="mb-6 p-4 bg-red-50 border border-red-200 rounded-lg flex items-center">
            <svg className="w-5 h-5 text-red-600 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 8v4m0 4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
            </svg>
            <span className="text-red-800">{error}</span>
          </div>
        )}

        <div className="grid grid-cols-1 lg:grid-cols-2 gap-8">
          {/* Image Gallery */}
          <div className="space-y-4">
            {/* Main Image */}
            <div className="bg-white dark:bg-gray-800 rounded-2xl shadow-lg overflow-hidden aspect-square">
              <img
                src={product.imageUrls[selectedImage] || '/placeholder-product.png'}
                alt={product.name}
                className="w-full h-full object-cover"
                onError={(e) => {
                  e.currentTarget.src = '/placeholder-product.png';
                }}
              />
            </div>

            {/* Thumbnail Images */}
            {product.imageUrls.length > 1 && (
              <div className="grid grid-cols-5 gap-2">
                {product.imageUrls.map((url, index) => (
                  <button
                    key={index}
                    onClick={() => setSelectedImage(index)}
                    className={`aspect-square rounded-lg overflow-hidden border-2 transition-all ${
                      selectedImage === index ? 'border-blue-600 dark:border-blue-400 ring-2 ring-blue-200 dark:ring-blue-900' : 'border-gray-200 dark:border-gray-700 hover:border-gray-300 dark:hover:border-gray-600'
                    }`}
                  >
                    <img
                      src={url}
                      alt={`${product.name} - Ảnh ${index + 1}`}
                      className="w-full h-full object-cover"
                      onError={(e) => {
                        e.currentTarget.src = '/placeholder-product.png';
                      }}
                    />
                  </button>
                ))}
              </div>
            )}
          </div>

          {/* Product Info */}
          <div className="bg-white dark:bg-gray-800 rounded-2xl shadow-lg p-6 space-y-6">
            {/* Product Name */}
            <div>
              <h1 className="text-3xl font-bold mb-2">{product.name}</h1>
              <div className="flex items-center space-x-4 text-sm">
                <span className="text-gray-600 dark:text-gray-400">
                  Danh mục: <span className="text-blue-600 dark:text-blue-400 font-medium">{product.categoryName}</span>
                </span>
                <span className="text-gray-400 dark:text-gray-600">|</span>
                <span className="text-gray-600 dark:text-gray-400">
                  Người bán: <span className="font-medium">{product.sellerUsername}</span>
                </span>
              </div>
            </div>

            {/* Status Badge */}
            <div>
              <span className={`inline-flex items-center px-3 py-1 rounded-full text-sm font-medium ${
                product.status === 'AVAILABLE' ? 'bg-green-100 text-green-800' :
                product.status === 'OUT_OF_STOCK' ? 'bg-red-100 text-red-800' :
                'bg-gray-100 text-gray-800'
              }`}>
                {product.status === 'AVAILABLE' ? 'Còn hàng' :
                 product.status === 'OUT_OF_STOCK' ? 'Hết hàng' :
                 product.status}
              </span>
            </div>

            {/* Price */}
            <div className="border-t border-b border-gray-200 dark:border-gray-700 py-4">
              <div className="text-4xl font-bold text-blue-600 dark:text-blue-400">{formatPrice(product.price)}</div>
            </div>

            {/* Quantity Selector */}
            <div className="space-y-2">
              <label className="block text-sm font-medium text-gray-700 dark:text-gray-300">Số lượng</label>
              <div className="flex items-center space-x-4">
                <div className="flex items-center border border-gray-300 dark:border-gray-600 rounded-lg">
                  <button
                    onClick={decrementQuantity}
                    disabled={quantity <= 1}
                    className="px-4 py-2 text-gray-600 dark:text-gray-400 hover:bg-gray-100 dark:hover:bg-gray-700 disabled:opacity-50 disabled:cursor-not-allowed"
                  >
                    <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M20 12H4" />
                    </svg>
                  </button>
                  <input
                    type="text"
                    value={quantity}
                    readOnly
                    className="w-16 text-center border-x border-gray-300 dark:border-gray-600 bg-white dark:bg-gray-800 py-2 focus:outline-none"
                  />
                  <button
                    onClick={incrementQuantity}
                    disabled={quantity >= product.quantity}
                    className="px-4 py-2 text-gray-600 dark:text-gray-400 hover:bg-gray-100 dark:hover:bg-gray-700 disabled:opacity-50 disabled:cursor-not-allowed"
                  >
                    <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 4v16m8-8H4" />
                    </svg>
                  </button>
                </div>
                <span className="text-sm text-gray-600">
                  {product.quantity} sản phẩm có sẵn
                </span>
              </div>
            </div>

            {/* Action Buttons */}
            <div className="space-y-3 relative z-10">
              <button
                type="button"
                onClick={handleBuyNow}
                disabled={product.quantity === 0 || addingToCart}
                className="w-full px-6 py-3 bg-blue-600 hover:bg-blue-700 disabled:bg-gray-400 disabled:hover:bg-gray-400 text-white font-semibold rounded-lg transition-colors disabled:cursor-not-allowed flex items-center justify-center shadow-md cursor-pointer select-none"
              >
                <svg className="w-5 h-5 mr-2 pointer-events-none" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
                </svg>
                <span className="pointer-events-none">
                  {product.quantity === 0 ? 'Hết hàng' : 'Mua ngay'}
                </span>
              </button>
              <button
                type="button"
                onClick={handleAddToCart}
                disabled={product.quantity === 0 || addingToCart}
                className="w-full px-6 py-3 bg-white dark:bg-gray-700 text-blue-600 dark:text-blue-400 font-semibold rounded-lg border-2 border-blue-600 dark:border-blue-400 hover:bg-blue-50 dark:hover:bg-gray-600 transition-colors disabled:bg-gray-100 disabled:text-gray-400 disabled:border-gray-300 disabled:cursor-not-allowed flex items-center justify-center shadow-md cursor-pointer select-none"
              >
                {addingToCart ? (
                  <>
                    <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-blue-600 dark:border-blue-400 mr-2 pointer-events-none"></div>
                    <span className="pointer-events-none">Đang thêm...</span>
                  </>
                ) : (
                  <>
                    <svg className="w-5 h-5 mr-2 pointer-events-none" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M12 6v6m0 0v6m0-6h6m-6 0H6" />
                    </svg>
                    <span className="pointer-events-none">
                      {product.quantity === 0 ? 'Hết hàng' : 'Thêm vào giỏ hàng'}
                    </span>
                  </>
                )}
              </button>
            </div>

            {/* Product Info */}
            <div className="border-t border-gray-200 dark:border-gray-700 pt-6 space-y-4">
              <div className="flex items-start">
                <svg className="w-5 h-5 text-gray-400 dark:text-gray-600 mr-3 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
                </svg>
                <div>
                  <p className="font-medium">Đảm bảo chất lượng</p>
                  <p className="text-sm text-gray-600 dark:text-gray-400">Sản phẩm được kiểm tra kỹ trước khi giao</p>
                </div>
              </div>
              <div className="flex items-start">
                <svg className="w-5 h-5 text-gray-400 dark:text-gray-600 mr-3 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 8h14M5 8a2 2 0 110-4h14a2 2 0 110 4M5 8v10a2 2 0 002 2h10a2 2 0 002-2V8m-9 4h4" />
                </svg>
                <div>
                  <p className="font-medium">Giao hàng toàn quốc</p>
                  <p className="text-sm text-gray-600 dark:text-gray-400">Miễn phí vận chuyển cho đơn hàng trên 500.000đ</p>
                </div>
              </div>
              <div className="flex items-start">
                <svg className="w-5 h-5 text-gray-400 dark:text-gray-600 mr-3 mt-0.5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                  <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 10h10a8 8 0 018 8v2M3 10l6 6m-6-6l6-6" />
                </svg>
                <div>
                  <p className="font-medium">Đổi trả dễ dàng</p>
                  <p className="text-sm text-gray-600 dark:text-gray-400">Đổi trả trong vòng 7 ngày nếu có vấn đề</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        {/* Product Description */}
        <div className="mt-8 bg-white dark:bg-gray-800 rounded-2xl shadow-lg p-6">
          <h2 className="text-2xl font-bold mb-4">Mô tả sản phẩm</h2>
          <div className="prose max-w-none text-gray-700 dark:text-gray-300 leading-relaxed whitespace-pre-wrap">
            {product.description || 'Chưa có mô tả chi tiết cho sản phẩm này.'}
          </div>
        </div>

        {/* Additional Info */}
        <div className="mt-8 bg-white dark:bg-gray-800 rounded-2xl shadow-lg p-6">
          <h2 className="text-2xl font-bold mb-4">Thông tin bổ sung</h2>
          <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
            <div className="flex border-b border-gray-200 dark:border-gray-700 pb-2">
              <span className="font-medium text-gray-700 dark:text-gray-300 w-40">Mã sản phẩm:</span>
              <span className="text-gray-600 dark:text-gray-400">SP{product.id.toString().padStart(6, '0')}</span>
            </div>
            <div className="flex border-b border-gray-200 dark:border-gray-700 pb-2">
              <span className="font-medium text-gray-700 dark:text-gray-300 w-40">Danh mục:</span>
              <span className="text-gray-600 dark:text-gray-400">{product.categoryName}</span>
            </div>
            <div className="flex border-b border-gray-200 dark:border-gray-700 pb-2">
              <span className="font-medium text-gray-700 dark:text-gray-300 w-40">Tình trạng:</span>
              <span className="text-gray-600 dark:text-gray-400">
                {product.status === 'AVAILABLE' ? 'Còn hàng' : 'Hết hàng'}
              </span>
            </div>
            <div className="flex border-b border-gray-200 dark:border-gray-700 pb-2">
              <span className="font-medium text-gray-700 dark:text-gray-300 w-40">Số lượng:</span>
              <span className="text-gray-600 dark:text-gray-400">{product.quantity} sản phẩm</span>
            </div>
            <div className="flex border-b border-gray-200 dark:border-gray-700 pb-2">
              <span className="font-medium text-gray-700 dark:text-gray-300 w-40">Ngày đăng:</span>
              <span className="text-gray-600 dark:text-gray-400">{new Date(product.createdAt).toLocaleDateString('vi-VN')}</span>
            </div>
            <div className="flex border-b border-gray-200 dark:border-gray-700 pb-2">
              <span className="font-medium text-gray-700 dark:text-gray-300 w-40">Cập nhật:</span>
              <span className="text-gray-600 dark:text-gray-400">{new Date(product.updatedAt).toLocaleDateString('vi-VN')}</span>
            </div>
          </div>
        </div>
      </div>

      {/* Footer */}
      <footer className="bg-gray-900 dark:bg-black text-white mt-16">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
          <div className="text-center">
            <p className="text-gray-400 dark:text-gray-500">© 2025 AI Agent for Business. All rights reserved.</p>
          </div>
        </div>
      </footer>
    </div>
  );
}
