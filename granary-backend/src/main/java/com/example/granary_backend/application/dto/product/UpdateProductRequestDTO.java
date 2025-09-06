package com.example.granary_backend.application.dto.product;

public record UpdateProductRequestDTO(
  String productId,
  String name,
  String size,
  Integer priceCents,
  Integer stockQuantity,
  Integer lowStockAlert,
  String imageUrl,
  Boolean active
){}
