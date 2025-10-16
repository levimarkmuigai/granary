package com.example.granary_backend.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.granary_backend.domain.model.Order;
import com.example.granary_backend.domain.model.Order.CustomerDetails;
import com.example.granary_backend.domain.model.Order.OrderLine;
import com.example.granary_backend.domain.model.value.MpesaTransactionId;
import com.example.granary_backend.domain.model.value.OrderId;
import com.example.granary_backend.domain.model.value.ProductId;
import com.example.granary_backend.infrastructure.persistence.entity.OrderEntity;
import com.example.granary_backend.infrastructure.persistence.entity.OrderLineEntity;
import com.example.granary_backend.infrastructure.persistence.entity.ProductEntity;

@Component
public final class OrderMapper {

  public OrderEntity toEntity(
      Order order,
      List<ProductEntity> productEntities) {
    Objects.requireNonNull(order, "Order must not be null");

    OrderEntity entity = new OrderEntity();
    entity.setId(order.getId().getValue());

    entity.setDeliveryStatus(order.getDeliveryStatus());
    entity.setPaymentStatus(order.getPaymentStatus());
    entity.setOrderStatus(order.getOrderStatus());

    if (order.getMpesaTransactionId() != null) {
      entity.setMpesaTransactionId(order.getMpesaTransactionId().toString());
    }
    if (order.getMpesaCheckoutRequest() != null) {
      entity.setMpesaCheckoutRequestId(order.getMpesaCheckoutRequest());
    }

    CustomerDetails details = order.getCustomerDetails();
    entity.setCustomerName(details.getName());
    entity.setCustomerPhone(details.getPhone());
    entity.setCustomerAddress(details.getAddress());
    entity.setCustomerEmail(details.getEmail());

    order.getOrderLines().forEach(line -> {
      ProductEntity productEntity = productEntities.stream()
          .filter(p -> p.getId().equals(line.getProductId().getValue()))
          .findFirst()
          .orElseThrow(() -> new IllegalStateException(
              "ProductEntity not provided for productId " + line.getProductId()));

      OrderLineEntity lineEntity = new OrderLineEntity();
      lineEntity.setId(UUID.randomUUID());
      lineEntity.setProductEntity(productEntity);
      lineEntity.setProductName(line.getProductName());
      lineEntity.setQuantityOrdered(line.getQuantityOrdered());
      lineEntity.setUnitPriceCents(line.getUnitPriceCents());
      lineEntity.setLineTotalCents(line.getLineTotalCents());

      entity.addOrderLine(lineEntity);
    });
    return entity;
  }

  public Order toDomain(OrderEntity entity) {
    Objects.requireNonNull(entity, "OrderEntity cannot be null.");

    Order.CustomerDetails customerDetails = new Order.CustomerDetails(
        entity.getCustomerName(),
        entity.getCustomerPhone(),
        entity.getCustomerEmail(),
        entity.getCustomerAddress());

    List<OrderLine> orderLines = entity.getOrderLines() == null ? List.of()
        : entity.getOrderLines().stream()
            .map(line -> OrderLine.create(
                ProductId.from(line.getProductEntity().getId()),
                line.getProductName(),
                line.getUnitPriceCents(),
                line.getQuantityOrdered()))
            .toList();

    MpesaTransactionId mpesaId = entity.getMpesaTransactionId() != null
        ? MpesaTransactionId.from(entity.getMpesaTransactionId())
        : null;

    return Order.reconstitute(
        OrderId.from(entity.getId()),
        orderLines,
        customerDetails,
        entity.getDeliveryStatus(),
        entity.getPaymentStatus(),
        entity.getOrderStatus(),
        mpesaId,
        entity.getMpesaCheckoutRequestId(),
        entity.getCreatedAt(),
        entity.getUpdatedAt());
  }
}
