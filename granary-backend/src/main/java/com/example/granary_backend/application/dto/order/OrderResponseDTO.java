package com.example.granary_backend.application.dto.order;

import java.time.LocalDateTime;

public record OrderResponseDTO(
  String orderId,
  String productId,
  String productName,
  int quantityOrdered,
  int totalAmountCents,
  String customerName,
  String customerEmail,
  String customerPhone,
  String customerAddress,
  String deliveryMethod,
  String paymentStatus,
  String orderStatus,
  String mpesaTransactionId,
  LocalDateTime createdAt,
  LocalDateTime updatedAt
) {}
