package com.example.granary_backend.domain.model.value;

public record MpesaTransactionId(String value) {

  public MpesaTransactionId {
    if (value == null || value.isBlank()) {
      throw new IllegalArgumentException("MpesaTransactionId cannot be null or blank");
    }
  }
}
