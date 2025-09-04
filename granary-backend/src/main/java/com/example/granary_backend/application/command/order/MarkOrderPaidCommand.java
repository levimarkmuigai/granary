package com.example.granary_backend.application.command.order;

import java.util.Objects;

public class MarkOrderPaidCommand {

  private final String orderId;
  private final String mpesaTransactionId;

  public MarkOrderPaidCommand(String orderId, String mpesaTransactionId) {
    this.orderId = Objects.requireNonNull(orderId, "Order ID cannot be null");
    this.mpesaTransactionId = Objects.requireNonNull(mpesaTransactionId, "Mpesa Transaction ID cannot be null").trim();
    if (this.orderId.isBlank()) {
      throw new IllegalArgumentException("Order ID cannot be blank");
    }
    if (this.mpesaTransactionId.isBlank()) {
      throw new IllegalArgumentException("Mpesa Transaction ID cannot be blank");
    }
  }

  public String getOrderId() {
    return orderId;
  }

  public String getMpesaTransactionId() {
    return mpesaTransactionId;
  }

  public String toString() {
    return "MarkOrderPaidCommand{orderId='" + orderId + "', mpesaTransactionId='" + mpesaTransactionId + "'}";
  }
}
