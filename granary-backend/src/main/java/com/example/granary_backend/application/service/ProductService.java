package com.example.granary_backend.application.service;

import java.util.Objects;

import com.example.granary_backend.application.command.product.UpdateProductCommand;
import com.example.granary_backend.application.command.product.CreateProductCommand;
import com.example.granary_backend.domain.model.Product;
import com.example.granary_backend.domain.model.ProductId;
import com.example.granary_backend.domain.port.ProductRepository;

public class ProductService {

  private final ProductRepository productRepository;

  public ProductService(ProductRepository productRepository) {
    this.productRepository = Objects.requireNonNull(productRepository, "ProductRepository must not be null");
  }

  public ProductId createProduct(CreateProductCommand command) {
    Objects.requireNonNull(command, "CreateProductCommand must not be null");

    var productId = ProductId.fromString(command.getProductId());

    var product = Product.create(
      productId,
      command.getName(),
      command.getSize(),
      command.getPriceCents(),
      command.getStockQuantity(),
      command.getLowStockAlert(),
      command.getImageUrl(),
      command.isActive()
    );

    productRepository.save(product);
    return product.getId();
  }

  public ProductId updateProduct(UpdateProductCommand command) {
    Objects.requireNonNull(command, "UpdateProductCommand cannot be null");

    Product product = productRepository.findById(ProductId.fromString(command.getProductId()))
        .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + command.getProductId()));


    product.updateDetails(
      command.getName(),
      command.getSize(),
      command.getPriceCents(),
      command.getStockQuantity(),
      command.getLowStockAlert(),
      command.getImageUrl(),
      command.getActiveOptional()
    );

    productRepository.save(product);

    return product.getId();

  }
}
