package com.example.granary_backend.application.validation.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.example.granary_backend.application.command.order.AdvanceOrderCommand;
import com.example.granary_backend.application.exception.InvalidCommandException;
import com.example.granary_backend.application.validation.CommandValidator;
import com.example.granary_backend.application.validation.ValidationRules;

@Component
public class AdvanceOrderCommandValidator implements CommandValidator<AdvanceOrderCommand> {

  @Override
  public void validate(AdvanceOrderCommand command) {

    Objects.requireNonNull(command, "AdvanceOrderCommand cannot be null");

    Map<String, String> errors = new HashMap<>();

    ValidationRules.checkRequired(errors, "orderId", command.getOrderId());

    if (!errors.isEmpty()) {
      throw new InvalidCommandException(
          "AdvanceOrderCommand input is invalid. Please correct the listed fields. " + errors.toString());

    }
  }
}
