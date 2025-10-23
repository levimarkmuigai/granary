package com.example.granary_backend.infrastructure.web.admin.dto;

public record DashboardSummaryDTO(
        long newOrders,
        long packagingOrders,
        long readyOrders,
        long totalProducts) {
}
