package com.example.granary_backend.application.dto.order;

public record MarkOrderPaidRequestDTO(
  String orderId,
  String mpesaTransactionId
) {}
