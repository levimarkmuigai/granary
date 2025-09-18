package com.example.granary_backend.application.dto.order;

public record OrderLineResponseDTO(
  String productId,
  String productName,
  int unitPriceCents,
  int quantityOrdered,
  int lineTotalCents
) {}
