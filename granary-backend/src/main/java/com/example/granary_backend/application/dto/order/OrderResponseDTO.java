package com.example.granary_backend.application.dto.order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
  String orderId,
  List<OrderLineResponseDTO> orderLines,
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
