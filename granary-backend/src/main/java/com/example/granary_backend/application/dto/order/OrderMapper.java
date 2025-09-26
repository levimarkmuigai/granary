package com.example.granary_backend.application.dto.order;

import java.util.List;
import java.util.Objects;

import com.example.granary_backend.application.command.order.AdvanceOrderCommand;
import com.example.granary_backend.application.command.order.CreateOrderCommand;
import com.example.granary_backend.application.command.order.MarkOrderPaidCommand;
import com.example.granary_backend.application.dto.order.CreateOrderRequestDTO;
import com.example.granary_backend.domain.model.Order;

public final class OrderMapper {

  private OrderMapper() {}

  public CreateOrderCommand toCreateCommand(CreateOrderRequestDTO dto) {
    Objects.requireNonNull(dto, "CreateOrderRequestDTO must not be null");

    Objects.requireNonNull(dto.orderLines(), "OrderLine must not be null");
    if(dto.orderLines().isEmpty()){
      throw new IllegalArgumentException("Order must contain at least one order line");
    }

    var orderLineCommands = dto.orderLines().stream()
    .map(line -> new CreateOrderCommand.OrderLineCommand(
      Objects.requireNonNull(line.productId(), "productId cannot be null"),
      line.quantityOrdered()
    ))
    .toList();

    return new CreateOrderCommand(
      orderLineCommands,
      dto.customerName(),
      dto.customerEmail(),
      dto.customerPhone(),
      dto.customerAddress(),
      dto.deliveryMethod()
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

    List<OrderLineResponseDTO> orderLineDTOs = order.getOrderLines().stream()
    .map(line -> new OrderLineResponseDTO(
      line.getProductId().toString(),
      line.getProductName(),
      line.getUnitPriceCents(),
      line.getQuantityOrdered(),
      line.getLineTotalCents()
    ))
    .toList();

    Order.CustomerDetails customer = order.getCustomerDetails();

    String mpesaTransactionId = order.getMpesaTransactionId() != null
      ? order.getMpesaTransactionId().toString()
      : null;

    return new OrderResponseDTO(
      order.getId().toString(),
      orderLineDTOs,
      customer.getName(),
      customer.getEmail(),
      customer.getPhone(),
      customer.getAddress(),
      order.getDeliveryMethod().name().toLowerCase(),
      order.getPaymentStatus().name(),
      order.getOrderStatus().name(),
      mpesaTransactionId,
      order.getCreatedAt(),
      order.getUpdatedAt()
    );
  }
}
