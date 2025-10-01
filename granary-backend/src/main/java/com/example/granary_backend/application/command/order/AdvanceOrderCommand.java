package com.example.granary_backend.application.command.order;

public class AdvanceOrderCommand {

  private final String orderId;

  public AdvanceOrderCommand(String orderId) {
    this.orderId = orderId;
  }

  public String getOrderId() {
    return orderId;
  }
}
