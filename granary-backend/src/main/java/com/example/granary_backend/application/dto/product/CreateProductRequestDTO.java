package com.example.granary_backend.application.dto.product;

public record CreateProductRequestDTO(
  String productId,
  String name,
  String size,
  int priceCents,
  int stockQuantity,
  int lowStockAlert,
  String imageUrl
) {}
