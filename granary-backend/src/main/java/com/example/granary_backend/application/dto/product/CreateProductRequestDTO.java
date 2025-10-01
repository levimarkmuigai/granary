package com.example.granary_backend.application.dto.product;

public record CreateProductRequestDTO(
  String name,
  String size,
  int priceCents,
  int stockQuantity,
  int lowStockAlert,
  String imageUrl
) {}
