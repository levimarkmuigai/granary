package com.example.granary_backend.application.command.payment;

import java.util.Objects;

public final class InitiatePaymentCommand {

  private final String orderId;

  public InitiatePaymentCommand(String orderId) {
    this.orderId = Objects.requireNonNull(orderId, "orderId must not be null");
  }

  public String getOrderId() {
    return orderId;
  }

}
