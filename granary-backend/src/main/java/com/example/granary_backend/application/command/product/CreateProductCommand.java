package com.example.granary_backend.application.command.product;

import java.util.Objects;

public class CreateProductCommand {

  private final String name;
  private final String size;
  private final int priceCents;
  private final int stockQuantity;
  private final int lowStockAlert;
  private final String imageUrl;

  public CreateProductCommand(String name, String size, int priceCents, int stockQuantity,
      int lowStockAlert, String imageUrl) {

    this.name = Objects.requireNonNull(name, "Product name cannot be null").trim();
    this.size = Objects.requireNonNull(size, "Size cannot be null").trim();
    this.lowStockAlert = Objects.requireNonNull(lowStockAlert, "Low stock alert cannot be null");
    this.imageUrl = Objects.requireNonNull(imageUrl, "Image URL cannot be null").trim();
    this.priceCents = priceCents;
    this.stockQuantity = stockQuantity;
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
}
