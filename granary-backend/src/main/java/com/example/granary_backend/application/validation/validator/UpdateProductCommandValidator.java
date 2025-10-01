package com.example.granary_backend.application.validation.validator;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.granary_backend.application.command.product.UpdateProductCommand;
import com.example.granary_backend.application.exception.InvalidCommandException;
import com.example.granary_backend.application.validation.CommandValidator;
import com.example.granary_backend.application.validation.ValidationRules;

@Component
public class UpdateProductCommandValidator implements CommandValidator<UpdateProductCommand> {

  @Override
  public void validate(UpdateProductCommand command) {

    if(command == null) {
      throw new InvalidCommandException("UpdateProductCommand cannot be null", Map.of());
    }
    Map<String, String> errors = new HashMap<>();

    ValidationRules.checkRequired(errors, "productId", command.getProductId());


    command.getName().ifPresent(name ->
      ValidationRules.checkRequired(errors, "name", name)
    );
    command.getSize().ifPresent(size ->
      ValidationRules.checkRequired(errors, "size", size)
    );
    command.getImageUrl().ifPresent(imageUrl ->
      ValidationRules.checkRequired(errors, "imageUrl", imageUrl)
    );
    command.getPriceCents().ifPresent(priceCents ->
      ValidationRules.checkPositiveNumber(errors, "priceCents", priceCents)
    );
    command.getStockQuantity().ifPresent(stockQuantity ->
      ValidationRules.checkNonNegativeNumber(errors, "stockQuantity", stockQuantity)
    );
    command.getLowStockAlert().ifPresent(lowStockAlert ->
      ValidationRules.checkNonNegativeNumber(errors, "lowStockAlert", lowStockAlert)
    );

    if (!errors.isEmpty()) {
      throw new InvalidCommandException(
        "Product update input is invalid. Please correct the listed fields. ", errors);
    }
  }
}
