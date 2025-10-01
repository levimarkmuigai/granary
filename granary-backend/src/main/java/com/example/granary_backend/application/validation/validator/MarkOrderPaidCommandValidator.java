package com.example.granary_backend.application.validation.validator;

import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.example.granary_backend.application.command.order.MarkOrderPaidCommand;
import com.example.granary_backend.application.exception.InvalidCommandException;
import com.example.granary_backend.application.validation.CommandValidator;
import com.example.granary_backend.application.validation.ValidationRules;

@Component
public class MarkOrderPaidCommandValidator implements CommandValidator<MarkOrderPaidCommand> {

  @Override
  public void validate(MarkOrderPaidCommand command) {

    Objects.requireNonNull(command, "MarkOrderPaidCommand cannot be null");

    Map<String, String> errors = new java.util.HashMap<>();

    ValidationRules.checkRequired(errors, "orderId", command.getOrderId());
    ValidationRules.checkRequired(errors, "mpesaTransactionId", command.getMpesaTransactionId());

    if (!errors.isEmpty()) {
      throw new InvalidCommandException(
        "MarkOrderPaidCommand input is invalid. Please correct the listed fields. " + errors.toString());
    }

  }
}
