package com.example.granary_backend.application.util;

import java.util.List;
import java.util.Objects;

import com.example.granary_backend.application.command.order.CreateOrderCommand.CustomerDetailsCommand;
import com.example.granary_backend.application.command.order.CreateOrderCommand.OrderLineCommand;
import com.example.granary_backend.domain.model.Order;
import com.example.granary_backend.domain.model.value.ProductId;

public final class DomainMapper {

  private DomainMapper() {}

  public static Order.CustomerDetails mapCustomerDetails(CustomerDetailsCommand commandDetails) {

    Objects.requireNonNull(commandDetails, "commandDetails must not be null");

    return new Order.CustomerDetails(
      commandDetails.getName(),
      commandDetails.getEmail(),
      commandDetails.getPhone(),
      commandDetails.getAddress()
    );
  }

  public static List<ProductId> mapToProductIds(List<OrderLineCommand> lineCommands) {
    Objects.requireNonNull(lineCommands, "lineCommands must not be null");

    return lineCommands.stream()
    .map(line -> ProductId.fromString(line.getProductId()))
    .toList();
  }
}
