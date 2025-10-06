package com.example.granary_backend.application.command.product;

import java.util.Optional;

public class UpdateProductCommand {

  private final String productId;
  private final Optional<String> name;
  private final Optional<String> size;
  private final Optional<Integer> priceCents;
  private final Optional<Integer> stockQuantity;
  private final Optional<Integer> lowStockAlert;
  private final Optional<String> imageUrl;
  private final Optional<Boolean> active;

  public UpdateProductCommand(
      String productId,
      Optional<String> name,
      Optional<String> size,
      Optional<Integer> lowStockAlert,
      Optional<String> imageUrl,
      Optional<Integer> priceCents,
      Optional<Integer> stockQuantity,
      Optional<Boolean> active) {

    if (productId == null || productId.isBlank()) {
      throw new IllegalArgumentException("Product ID cannot be null or blank");
    }
    this.productId = productId.trim();

    this.name = name.map(String::trim);
    this.size = size.map(String::trim);
    this.imageUrl = imageUrl.map(String::trim);

    this.priceCents = priceCents;
    this.stockQuantity = stockQuantity;
    this.lowStockAlert = lowStockAlert;
    this.active = active;

  }

  // Getters
  public String getProductId() {
    return productId;
  }

  public Optional<String> getName() {
    return name;
  }

  public Optional<Integer> getPriceCents() {
    return priceCents;
  }

  public Optional<Integer> getStockQuantity() {
    return stockQuantity;
  }

  public Optional<Boolean> getActiveOptional() {
    return active;
  }

  public Optional<String> getSize() {
    return size;
  }

  public Optional<Integer> getLowStockAlert() {
    return lowStockAlert;
  }

  public Optional<String> getImageUrl() {
    return imageUrl;
  }

}
