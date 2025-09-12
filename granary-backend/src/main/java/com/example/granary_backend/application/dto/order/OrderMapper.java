package com.example.granary_backend.application.dto.order;

import java.util.Objects;

import com.example.granary_backend.application.command.order.AdvanceOrderCommand;
import com.example.granary_backend.application.command.order.CreateOrderCommand;
import com.example.granary_backend.application.command.order.MarkOrderPaidCommand;
import com.example.granary_backend.application.dto.order.CreateOrderRequestDTO;
import com.example.granary_backend.domain.model.Order;

public class OrderMapper {

  public CreateOrderCommand toCreateCommand(CreateOrderRequestDTO dto) {
    Objects.requireNonNull(dto, "CreateOrderRequestDTO must not be null");

    var productId = dto.productId();

    var quantityOrdered = dto.quantityOrdered();
    var customerName = dto.customerName();
    var customerEmail = dto.customerEmail();
    var customerPhone = dto.customerPhone();
    var customerAddress = dto.customerAddress();
    var deliveryMethod = dto.deliveryMethod();

    return new CreateOrderCommand(
      productId,
      quantityOrdered,
      customerName,
      customerEmail,
      customerPhone,
      customerAddress,
      deliveryMethod
    );
  }

  public MarkOrderPaidCommand toMarkOrderPaidCommand(MarkOrderPaidRequestDTO dto) {
    Objects.requireNonNull(dto, "MarkOrderPaidRequestDTO must not be null");

    var orderId = dto.orderId();
    var mpesaTransactionId = dto.mpesaTransactionId();

    return new MarkOrderPaidCommand(orderId, mpesaTransactionId);
  }

  public AdvanceOrderCommand toAdvanceCommand(AdvanceOrderRequestDTO dto){
    Objects.requireNonNull(dto, "AdvanceOrderRequestDTO must not be null");

    var orderId = dto.orderId();
    return new AdvanceOrderCommand(orderId);
  }

  public OrderResponseDTO toResponseDTO(Order order) {
    Objects.requireNonNull(order, "Order must not be null");

    var orderId = order.getId().toString();

    var productId = order.getProduct().getId().toString();

    var productName = order.getProduct().getName();

    var quantityOrdered = order.getQuantityOrdered();

    var totalAmountCents = order.getTotalAmountCents();

    var customerName = order.getCustomerDetails().getName();

    var customerEmail = order.getCustomerDetails().getEmail();

    var customerPhone = order.getCustomerDetails().getPhone();

    var customerAddress = order.getCustomerDetails().getAddress();

    var deliveryMethod = order.getDeliveryMethod().name();

    var paymentStatus = order.getPaymentStatus().name();

    var orderStatus = order.getOrderStatus().name();

    var mpesaTransactionId = order.getMpesaTransactionId();

    var createdAt = order.getCreatedAt();

    var updatedAt = order.getUpdatedAt();

    return new OrderResponseDTO(
      orderId,
      productId,
      productName,
      quantityOrdered,
      totalAmountCents,
      customerName,
      customerEmail,
      customerPhone,
      customerAddress,
      deliveryMethod,
      paymentStatus,
      orderStatus,
      mpesaTransactionId,
      createdAt,
      updatedAt
    );
  }
}
