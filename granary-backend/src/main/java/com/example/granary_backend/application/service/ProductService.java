package com.example.granary_backend.application.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.granary_backend.application.command.product.CreateProductCommand;
import com.example.granary_backend.application.command.product.UpdateProductCommand;
import com.example.granary_backend.application.exception.InvalidCommandException;
import com.example.granary_backend.application.service.base.BaseApplicationService;
import com.example.granary_backend.application.validation.CommandValidator;
import com.example.granary_backend.domain.model.Product;
import com.example.granary_backend.domain.model.value.ProductId;
import com.example.granary_backend.domain.port.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class ProductService extends BaseApplicationService {

  private final ProductRepository productRepository;
  private final CommandValidator<CreateProductCommand> createValidator;
  private final CommandValidator<UpdateProductCommand> updateValidator;

  public ProductService(
      ProductRepository productRepository,
      CommandValidator<CreateProductCommand> createValidator,
      CommandValidator<UpdateProductCommand> updateValidator) {

    super();

    this.productRepository = Objects.requireNonNull(productRepository, "ProductRepository must not be null");
    this.createValidator = Objects.requireNonNull(createValidator, "createValidator must not be null");
    this.updateValidator = Objects.requireNonNull(updateValidator, "updateValidator must not be null");
  }

  public ProductId createProduct(CreateProductCommand command) {

    createValidator.validate(command);

    var productId = ProductId.createNew();

    var product = Product.create(
        productId,
        command.getName(),
        command.getSize(),
        command.getPriceCents(),
        command.getStockQuantity(),
        command.getLowStockAlert(),
        command.getImageUrl());

    productRepository.save(product);

    return product.getProductId();
  }

  public ProductId updateProduct(UpdateProductCommand command) {

    updateValidator.validate(command);

    ProductId productIdToUpdate = safelyParseProductId(command.getProductId());

    Product product = productRepository
        .findById(productIdToUpdate)
        .orElseThrow(() -> new EntityNotFoundException("Product with ID " + productIdToUpdate + "not found"));

    product.updateDetails(
        command.getName(),
        command.getSize(),
        command.getPriceCents(),
        command.getStockQuantity(),
        command.getLowStockAlert(),
        command.getImageUrl(),
        command.getActiveOptional());

    productRepository.save(product);
    return product.getProductId();
  }

  @Transactional(readOnly = true)
  public List<Product> getAvailableProducts() {
    return productRepository.findActiveProducts();
  }

  private ProductId safelyParseProductId(String rawId) {

    try {
      return ProductId.fromString(rawId);
    } catch (IllegalArgumentException e) {
      Map<String, String> errors = Map.of(
          "productId", "The provided product ID format is invalid (must be a valid UUID)");

      throw new InvalidCommandException(
          "The command contains an invalid ID format.",
          errors);
    }
  }
}
