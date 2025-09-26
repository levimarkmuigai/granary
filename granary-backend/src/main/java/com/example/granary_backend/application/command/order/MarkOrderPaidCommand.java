package com.example.granary_backend.application.command.order;

import java.util.Objects;

import com.example.granary_backend.domain.model.value.MpesaTransactionId;

public class MarkOrderPaidCommand {

  private final String orderId;
  private final MpesaTransactionId mpesaTransactionId;

  public MarkOrderPaidCommand(String orderId, String mpesaTransactionId) {
    this.orderId = Objects.requireNonNull(orderId, "Order ID cannot be null");
    this.mpesaTransactionId = new MpesaTransactionId(
      Objects.requireNonNull(mpesaTransactionId, "Mpesa Transaction ID cannot be null")
    );
  }

  public String getOrderId() {
    return orderId;
  }

  public MpesaTransactionId getMpesaTransactionId() {
    return mpesaTransactionId;
  }

  public String toString() {
    return "MarkOrderPaidCommand{orderId='" + orderId + "', mpesaTransactionId='" + mpesaTransactionId + "'}";
  }
}
