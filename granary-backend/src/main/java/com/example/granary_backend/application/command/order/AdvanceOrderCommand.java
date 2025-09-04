package com.example.granary_backend.application.command.order;

public class AdvanceOrderCommand {

  private final String orderId;

  public AdvanceOrderCommand(String orderId) {
    if (orderId == null || orderId.isBlank()) {
      throw new IllegalArgumentException("Order ID cannot be null or blank");
    }
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}
