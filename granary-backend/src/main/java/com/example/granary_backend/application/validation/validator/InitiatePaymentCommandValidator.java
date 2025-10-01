package com.example.granary_backend.application.validation.validator;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.example.granary_backend.application.command.payment.InitiatePaymentCommand;
import com.example.granary_backend.application.exception.InvalidCommandException;
import com.example.granary_backend.application.validation.CommandValidator;
import com.example.granary_backend.application.validation.ValidationRules;
import org.springframework.stereotype.Component;

@Component
public class InitiatePaymentCommandValidator implements CommandValidator<InitiatePaymentCommand> {

  @Override
  public void validate(InitiatePaymentCommand command) {

    Objects.requireNonNull(command, "Command must not be null");

    Map<String, String> errors = new HashMap<>();

    ValidationRules.checkRequired(errors, "orderId", command.getOrderId());

    if(!errors.isEmpty()) {
      throw new InvalidCommandException(
        "InitiatePaymentCommand input is invalid, Please correct the listed fields",
        errors
      );
    }
  }
}
