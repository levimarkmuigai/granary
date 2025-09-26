package com.example.granary_backend.infrastructure.persistence.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.granary_backend.domain.model.Order;
import com.example.granary_backend.domain.model.value.OrderId;
import com.example.granary_backend.domain.model.value.ProductId;
import com.example.granary_backend.domain.model.Order.OrderLine;
import com.example.granary_backend.infrastructure.persistence.entity.OrderEntity;
import com.example.granary_backend.infrastructure.persistence.entity.OrderLineEntity;
import com.example.granary_backend.infrastructure.persistence.entity.ProductEntity;

@Component
public final class OrderMapper {

  public OrderEntity toEntity(
    Order order,
    List<ProductEntity> productEntities
  ){
    Objects.requireNonNull(order, "Order must not be null");

    String mpesaTransactionId = order.getMpesaTransactionId() != null
      ? order.getMpesaTransactionId().toString()
      : null;

    OrderEntity entity = new OrderEntity(
      order.getId().getValue(),
      new ArrayList<>(),
      mpesaTransactionId,
      order.getCreatedAt(),
      order.getUpdatedAt(),
      order.getDeliveryMethod(),
      order.getPaymentStatus(),
      order.getOrderStatus(),
      order.getCustomerDetails().getName(),
      order.getCustomerDetails().getPhone(),
      order.getCustomerDetails().getAddress(),
      order.getCustomerDetails().getEmail()
    );

    var lineEntities = order.getOrderLines().stream()
        .map(line -> {
          ProductEntity productEntity = productEntities.stream()
              .filter(p -> p.getId().equals(line.getProductId().getValue()))
              .findFirst()
              .orElseThrow(() -> new IllegalStateException(
                  "ProductEntity not provided for productId " + line.getProductId()
              ));

          return new OrderLineEntity(
              UUID.randomUUID(),
              entity,
              productEntity,
              line.getQuantityOrdered(),
              line.getUnitPriceCents(),
              line.getLineTotalCents()
          );
        })
        .toList();

    entity.getOrderLines().addAll(lineEntities);

    return entity;
  }
  public Order toDomain(OrderEntity entity) {
    Objects.requireNonNull(entity, "OrderEntity cannot be null.");
    Order.CustomerDetails customerDetails = new Order.CustomerDetails(
      entity.getCustomerName(),
      entity.getCustomerPhone(),
      entity.getCustomerEmail(),
      entity.getCustomerAddress()
    );

    List<OrderLine> orderLines = entity.getOrderLines() == null ? List.of() :
    entity.getOrderLines().stream()
    .map(line -> OrderLine.create(
      ProductId.from(line.getProductEntity().getId()),
      line.getProductEntity().getName(),
      line.getUnitPriceCents(),
      line.getQuantityOrdered()
    ))
    .toList();

    return Order.createFromOrderLines(
      OrderId.from(entity.getId()),
      orderLines,
      customerDetails,
      entity.getDeliveryMethod()
    );
  }
}

