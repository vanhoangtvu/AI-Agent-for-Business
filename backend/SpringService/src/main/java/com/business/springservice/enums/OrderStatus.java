package com.business.springservice.enums;

public enum OrderStatus {
    PENDING,        // Đơn hàng mới tạo, chờ xác nhận
    CONFIRMED,      // Đã xác nhận
    PROCESSING,     // Đang xử lý/đóng gói
    SHIPPING,       // Đang giao hàng
    DELIVERED,      // Đã giao hàng
    CANCELLED,      // Đã hủy
    RETURNED        // Đã trả hàng
}
