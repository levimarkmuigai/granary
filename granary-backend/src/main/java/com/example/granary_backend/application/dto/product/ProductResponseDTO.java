package com.example.granary_backend.application.dto.product;

import java.time.LocalDateTime;

public record ProductResponseDTO(
  String productId,
  String name,
  String size,
  int priceCents,
  int stockQuantity,
  int lowStockAlert,
  String imageUrl,
  Boolean active,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
