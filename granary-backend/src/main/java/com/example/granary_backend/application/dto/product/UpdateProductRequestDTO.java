package com.example.granary_backend.application.dto.product;

public record UpdateProductRequestDTO(
  String productId,
  String name,
  Integer priceCents,
  Integer stockQuantity,
  Boolean active,
  String size,
  Integer lowStockAlert,
  String imageUrl
){}
