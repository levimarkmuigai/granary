package com.example.granary_backend.application.dto.order;

public record OrderLineRequestDTO(
    String productId,
    int quantityOrdered
) {}
