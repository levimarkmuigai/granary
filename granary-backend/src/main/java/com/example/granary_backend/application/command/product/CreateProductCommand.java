package com.example.granary_backend.application.command.product;

import java.util.Objects;

public class CreateProductCommand {
  private final String productId;
  private final String name;
  private final String size;
  private final int priceCents;
  private final int stockQuantity;
  private final int lowStockAlert;
  private final String imageUrl;
  private final boolean active = true;

  public CreateProductCommand(String productId, String name, String size, int priceCents, int stockQuantity,
      int lowStockAlert, String imageUrl) {
    if (name.isBlank()) {
      throw new IllegalArgumentException("Product name cannot be blank");
    }
    if (priceCents <= 0) {
      throw new IllegalArgumentException("Product price must be greater than zero");
    }
    if (stockQuantity < 0) {
      throw new IllegalArgumentException("Product stock quantity cannot be negative");
    }
    if (size.isBlank()) {
      throw new IllegalArgumentException("Product size cannot be blank");
    }
    if (lowStockAlert < 0) {
      throw new IllegalArgumentException("Low stock alert cannot be negative");
    }
    if (imageUrl.isBlank()) {
      throw new IllegalArgumentException("Image URL cannot be blank");
    }


    this.productId = Objects.requireNonNull(productId, "Product ID cannot be null").trim();
    this.name = Objects.requireNonNull(name, "Product name cannot be null").trim();
    this.size = Objects.requireNonNull(size, "Size cannot be null").trim();
    this.lowStockAlert = Objects.requireNonNull(lowStockAlert, "Low stock alert cannot be null");
    this.imageUrl = Objects.requireNonNull(imageUrl, "Image URL cannot be null").trim();
    this.priceCents = priceCents;
    this.stockQuantity = stockQuantity;
  }

  public String getProductId() {
    return productId;
  }

  public String getName() {
    return name;
  }

  public String getSize() {
    return size;
  }

  public int getPriceCents() {
    return priceCents;
  }

  public int getLowStockAlert() {
    return lowStockAlert;
  }

  public String getImageUrl() {
    return imageUrl;
  }

  public int getStockQuantity() {
    return stockQuantity;
  }

  public boolean isActive() {
    return active;
  }
}
