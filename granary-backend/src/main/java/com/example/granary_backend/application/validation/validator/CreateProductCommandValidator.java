package com.example.granary_backend.application.validation.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.granary_backend.application.command.product.CreateProductCommand;
import com.example.granary_backend.application.exception.InvalidCommandException;
import com.example.granary_backend.application.validation.CommandValidator;
import com.example.granary_backend.application.validation.ValidationRules;

@Component
public class CreateProductCommandValidator implements CommandValidator<CreateProductCommand>
{

  @Override
  public void validate(CreateProductCommand command) throws InvalidCommandException {

    if (command == null) {
      throw new InvalidCommandException("CreateProductCommand cannot be null");
    }

    Map<String, String> errors = new HashMap<>();

    ValidationRules.checkRequired(errors, "name", command.getName());

    ValidationRules.checkRequired(errors, "size", command.getSize());

    ValidationRules.checkRequired(errors, "imageUrl", command.getImageUrl());

    ValidationRules.checkPositiveNumber(errors, "priceCents", command.getPriceCents());

    ValidationRules.checkNonNegativeNumber(errors, "stockQuantity", command.getStockQuantity());

    ValidationRules.checkNonNegativeNumber(errors, "lowStockAlert", command.getLowStockAlert());

    if (!errors.isEmpty()) {
      throw new InvalidCommandException(
        "Product creation input is invalid. Please correct the listed fields. ", errors);
    }

  }
}
