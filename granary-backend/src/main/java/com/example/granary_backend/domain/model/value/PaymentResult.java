package com.example.granary_backend.domain.model.value;

import com.example.granary_backend.domain.model.Order.PaymentStatus;

public record PaymentResult(
  PaymentStatus status,
  MpesaTransactionId transactionId,
  String rawResponse
) {

  public PaymentResult {
    if (status == PaymentStatus.SUCCESS && transactionId == null) {
      throw new IllegalArgumentException("Transaction ID must be provided for successful payments.");
    }
  }
}
