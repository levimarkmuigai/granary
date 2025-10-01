package com.example.granary_backend.application.validation.validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.example.granary_backend.application.command.order.CreateOrderCommand;
import com.example.granary_backend.application.command.order.CreateOrderCommand.CustomerDetailsCommand;
import com.example.granary_backend.application.command.order.CreateOrderCommand.OrderLineCommand;
import com.example.granary_backend.application.exception.InvalidCommandException;
import com.example.granary_backend.application.validation.CommandValidator;
import com.example.granary_backend.application.validation.ValidationRules;

@Component
public class CreateOrderCommandValidator implements CommandValidator<CreateOrderCommand> {

  @Override
  public void validate(CreateOrderCommand command) {

    Objects.requireNonNull(command, "CreateOrderCommand cannot be null");
    Map<String, String> errors = new HashMap<>();

    List<OrderLineCommand> orderLines = command.getOrderLines();
    if (orderLines == null || orderLines.isEmpty()) {
      errors.put("orderLines", "Order must contain at least one order line.");
    } else {
      validateOrderLines(errors, orderLines);
    }

    CustomerDetailsCommand customerDetails = command.getCustomerDetails();
    if (customerDetails == null) {
      errors.put("customerDetails", "Customer details object is required.");
    } else {
      validateCustomerDetails(errors, customerDetails);
    }
    if (command.getDeliveryMethod() == null) {
      errors.put("deliveryMethod", "Delivery method is required.");
    }

    if (!errors.isEmpty()) {
      throw new InvalidCommandException(
        "Order creation input is invalid. Please correct the listed fields. " + errors.toString());
    }
  }
  private void validateCustomerDetails(Map<String, String> errors, CustomerDetailsCommand details) {
    ValidationRules.checkRequired(errors, "customerDetails.name", details.getName());
    ValidationRules.checkRequired(errors, "customerDetails.email", details.getEmail());
    ValidationRules.checkRequired(errors, "customerDetails.phone", details.getPhone());
    ValidationRules.checkRequired(errors, "customerDetails.address", details.getAddress());

    if (details.getEmail() != null && !details.getEmail().isBlank()) {
      ValidationRules.checkEmailFormat(errors, "customerDetails.email", details.getEmail());
    }
    if (details.getPhone() != null && !details.getPhone().isBlank()) {
    ValidationRules.checkPhoneFormat(errors, "customerDetails.phone", details.getPhone());
    }
  }

  private void validateOrderLines(Map<String, String> errors, List<OrderLineCommand> lines) {

    final AtomicInteger index = new AtomicInteger(0);

    lines.forEach(line  ->{
      int i = index.getAndIncrement();
      String prefix = "orderLines[" + i + "].";

      String productId = line.getProductId();
      if(productId == null || productId.trim().isBlank()) {
        errors.put(prefix + "productId", "Product ID is required.");
      }

      int quantity = line.getQuantity();
      if(quantity <= 0) {
        errors.put(prefix + "quantity", "Quantity must be a positive integer.");
      }

    });
  }
}
