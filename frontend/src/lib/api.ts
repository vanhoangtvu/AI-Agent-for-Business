const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://113.178.203.147:8089/api/v1';

export interface LoginRequest {
  username: string;
  password: string;
}

export interface RegisterRequest {
  username: string;
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  userId: number;
  username: string;
  email: string;
  role: string;
}

export interface ProductDTO {
  id: number;
  name: string;
  description: string;
  price: number;
  quantity: number;
  imageUrls: string[];
  status: string;
  categoryId: number;
  categoryName: string;
  sellerId: number;
  sellerUsername: string;
  createdAt: string;
  updatedAt: string;
}

export interface CategoryDTO {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  status: string;
}

export interface CartItemDTO {
  id: number;
  productId: number;
  productName: string;
  productPrice: number;
  productImageUrl: string;
  quantity: number;
  subtotal: number;
  addedAt: string;
}

export interface CartDTO {
  id: number;
  userId: number;
  items: CartItemDTO[];
  totalPrice: number;
  createdAt: string;
  updatedAt: string;
}

export interface OrderItemDTO {
  id: number;
  productId: number;
  productName: string;
  productPrice: number;
  quantity: number;
  subtotal: number;
}

export interface OrderDTO {
  id: number;
  customerId: number;
  customerUsername: string;
  customerName: string;
  customerEmail: string;
  customerPhone: string;
  shippingAddress: string;
  totalAmount: number;
  status: string;
  note: string;
  orderItems: OrderItemDTO[];
  createdAt: string;
  updatedAt: string;
}

export interface UserDTO {
  id: number;
  username: string;
  email: string;
  address: string;
  phoneNumber: string;
  avatarUrl: string;
  role: string;
  createdAt: string;
  updatedAt: string;
}

export interface ProfileUpdateRequest {
  username?: string;
  email?: string;
  address?: string;
  phoneNumber?: string;
  avatarUrl?: string;
}

export interface PasswordChangeRequest {
  oldPassword: string;
  newPassword: string;
}

export interface OrderCreateRequest {
  note?: string;
  items: {
    productId: number;
    quantity: number;
  }[];
}

class ApiClient {
  private baseURL: string;

  constructor() {
    this.baseURL = API_URL;
  }

  async login(data: LoginRequest): Promise<LoginResponse> {
    const response = await fetch(`${this.baseURL}/auth/login`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      const error = await response.json().catch(() => ({ message: 'Đăng nhập thất bại' }));
      throw new Error(error.message || 'Đăng nhập thất bại');
    }

    return response.json();
  }

  async register(data: RegisterRequest): Promise<LoginResponse> {
    const response = await fetch(`${this.baseURL}/auth/register`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(data),
    });

    if (!response.ok) {
      const error = await response.json().catch(() => ({ message: 'Đăng ký thất bại' }));
      throw new Error(error.message || 'Đăng ký thất bại');
    }

    return response.json();
  }

  setAuthToken(token: string) {
    if (typeof window !== 'undefined') {
      localStorage.setItem('auth_token', token);
    }
  }

  getAuthToken(): string | null {
    if (typeof window !== 'undefined') {
      return localStorage.getItem('auth_token');
    }
    return null;
  }

  removeAuthToken() {
    if (typeof window !== 'undefined') {
      localStorage.removeItem('auth_token');
    }
  }

  setUserData(data: LoginResponse) {
    if (typeof window !== 'undefined') {
      localStorage.setItem('user_data', JSON.stringify(data));
    }
  }

  getUserData(): LoginResponse | null {
    if (typeof window !== 'undefined') {
      const data = localStorage.getItem('user_data');
      return data ? JSON.parse(data) : null;
    }
    return null;
  }

  removeUserData() {
    if (typeof window !== 'undefined') {
      localStorage.removeItem('user_data');
    }
  }

  logout() {
    this.removeAuthToken();
    this.removeUserData();
  }

  // Shop APIs (public - no auth required)
  async getAllProducts(): Promise<ProductDTO[]> {
    const response = await fetch(`${this.baseURL}/shop/products`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error('Không thể tải danh sách sản phẩm');
    }

    return response.json();
  }

  async getProductById(id: number): Promise<ProductDTO> {
    const response = await fetch(`${this.baseURL}/shop/products/${id}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error('Không thể tải thông tin sản phẩm');
    }

    return response.json();
  }

  async getAllCategories(): Promise<CategoryDTO[]> {
    const response = await fetch(`${this.baseURL}/shop/categories`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error('Không thể tải danh sách danh mục');
    }

    return response.json();
  }

  async getProductsByCategory(categoryId: number): Promise<ProductDTO[]> {
    const response = await fetch(`${this.baseURL}/shop/products/category/${categoryId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error('Không thể tải sản phẩm theo danh mục');
    }

    return response.json();
  }

  async searchProducts(keyword: string): Promise<ProductDTO[]> {
    const response = await fetch(`${this.baseURL}/shop/products/search?keyword=${encodeURIComponent(keyword)}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    if (!response.ok) {
      throw new Error('Không thể tìm kiếm sản phẩm');
    }

    return response.json();
  }

  // Cart APIs (requires auth)
  async getCart(): Promise<CartDTO> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/cart`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      const errorText = await response.text();
      console.error('Cart API error:', response.status, errorText);
      throw new Error(`Không thể tải giỏ hàng: ${response.status} - ${errorText}`);
    }

    return response.json();
  }

  async addToCart(productId: number, quantity: number): Promise<CartDTO> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/cart/items`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify({ productId, quantity }),
    });

    if (!response.ok) {
      throw new Error('Không thể thêm vào giỏ hàng');
    }

    return response.json();
  }

  async updateCartItem(itemId: number, quantity: number): Promise<CartDTO> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/cart/items/${itemId}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify({ quantity }),
    });

    if (!response.ok) {
      throw new Error('Không thể cập nhật giỏ hàng');
    }

    return response.json();
  }

  async removeCartItem(itemId: number): Promise<CartDTO> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/cart/items/${itemId}`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error('Không thể xóa sản phẩm khỏi giỏ hàng');
    }

    return response.json();
  }

  async clearCart(): Promise<void> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/cart`, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error('Không thể xóa giỏ hàng');
    }
  }

  // Order APIs (requires auth)
  async createOrder(orderData: OrderCreateRequest): Promise<OrderDTO> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/orders`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(orderData),
    });

    if (!response.ok) {
      const error = await response.json().catch(() => ({ message: 'Không thể tạo đơn hàng' }));
      throw new Error(error.message || 'Không thể tạo đơn hàng');
    }

    return response.json();
  }

  async getMyOrders(): Promise<OrderDTO[]> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/orders/my-orders`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      const errorText = await response.text();
      console.error('Orders API error:', response.status, errorText);
      throw new Error(`Không thể tải danh sách đơn hàng: ${response.status} - ${errorText}`);
    }

    return response.json();
  }

  async getOrderById(id: number): Promise<OrderDTO> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/orders/${id}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error('Không thể tải thông tin đơn hàng');
    }

    return response.json();
  }

  async cancelOrder(id: number): Promise<void> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/orders/${id}/cancel`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      throw new Error('Không thể hủy đơn hàng');
    }
  }

  async updateShippingAddress(id: number, shippingAddress: string): Promise<OrderDTO> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/orders/${id}/address`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify({ shippingAddress }),
    });

    if (!response.ok) {
      throw new Error('Không thể cập nhật địa chỉ giao hàng');
    }

    return response.json();
  }

  // Profile APIs (requires auth)
  async getCurrentProfile(): Promise<UserDTO> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/profile`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
    });

    if (!response.ok) {
      const errorText = await response.text();
      console.error('Profile API error:', response.status, errorText);
      throw new Error(`Không thể tải thông tin profile: ${response.status} - ${errorText}`);
    }

    return response.json();
  }

  async updateProfile(profileData: ProfileUpdateRequest): Promise<UserDTO> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/profile`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify(profileData),
    });

    if (!response.ok) {
      throw new Error('Không thể cập nhật profile');
    }

    return response.json();
  }

  async changePassword(oldPassword: string, newPassword: string): Promise<string> {
    const token = this.getAuthToken();
    const response = await fetch(`${this.baseURL}/profile/password`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`,
      },
      body: JSON.stringify({ oldPassword, newPassword }),
    });

    if (!response.ok) {
      const error = await response.json().catch(() => ({ message: 'Không thể đổi mật khẩu' }));
      throw new Error(error.message || 'Không thể đổi mật khẩu');
    }

    return response.text();
  }
}

export const apiClient = new ApiClient();
