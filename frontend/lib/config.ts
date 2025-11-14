// API Configuration
export const API_CONFIG = {
  BASE_URL: 'http://113.187.152.149:8089',
  ENDPOINTS: {
    // Auth
    LOGIN: '/api/auth/login',
    REGISTER: '/api/auth/register',
    LOGOUT: '/api/auth/logout',
    
    // Documents
    DOCUMENTS: '/api/documents',
    DOCUMENT_UPLOAD: '/api/documents/upload',
    
    // Chat
    CONVERSATIONS: '/api/chat/conversations',
    SEND_MESSAGE: '/api/chat/send',
    
    // Reports
    REPORTS: '/api/reports',
    GENERATE_REPORT: '/api/reports/generate',
    
    // User
    USER_PROFILE: '/api/users/profile',
    UPDATE_PROFILE: '/api/users/profile',
    CHANGE_PASSWORD: '/api/users/change-password',
    
    // Activity Logs
    ACTIVITIES: '/api/activities',
    
    // Products
    PRODUCTS: '/api/products',
    MY_PRODUCTS: '/api/products/my-products',
    TOP_SELLING: '/api/products/top-selling',
    FEATURED: '/api/products/featured',
    LOW_STOCK: '/api/products/low-stock',
    
    // Orders
    ORDERS: '/api/orders',
    ORDER_STATISTICS: '/api/orders/statistics',
  }
};

export const STORAGE_KEYS = {
  TOKEN: 'auth_token',
  USER: 'user_data',
};
