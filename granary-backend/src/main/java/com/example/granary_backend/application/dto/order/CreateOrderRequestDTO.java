package com.example.granary_backend.application.dto.order;

public record CreateOrderRequestDTO (
  String productId,
  int quantityOrdered,
  String customerName,
  String customerEmail,
  String customerPhone,
  String customerAddress,
  String deliveryMethod
) {}
