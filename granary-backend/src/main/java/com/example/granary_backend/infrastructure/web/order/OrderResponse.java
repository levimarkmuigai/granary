package com.example.granary_backend.infrastructure.web.order;

public record OrderResponse(
                String orderId,
                int amount,
                String customerName,
                String status,
                boolean requiresPayment) {
}