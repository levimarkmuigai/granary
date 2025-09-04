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

    this.name = name.map(n-> {
      if (n.isBlank()) {
        throw new IllegalArgumentException("Product name cannot be blank");
      }
      return n.trim();
    });

    this.size = size.map(sz -> {
      if (sz.isBlank()) {
        throw new IllegalArgumentException("Product size cannot be blank");
      }
      return sz.trim();
    });

    this.priceCents = priceCents.map(p -> {
      if (p <= 0) {
        throw new IllegalArgumentException("Product price must be greater than zero");
      }
      return p;
    });

    this.stockQuantity = stockQuantity.map(sq -> {
      if (sq < 0) {
        throw new IllegalArgumentException("Product stock quantity cannot be negative");
      }
      return sq;
    });

    this.lowStockAlert = lowStockAlert.map(lsa -> {
      if (lsa < 0) {
        throw new IllegalArgumentException("Low stock alert cannot be negative");
      }
      return lsa;
    });

    this.imageUrl = imageUrl.map(url -> {
      if (url.isBlank()) {
        throw new IllegalArgumentException("Image URL cannot be blank");
      }
      return url.trim();
    });

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

