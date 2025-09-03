package com.example.granary_backend.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Domain model for a product in the inventory system.
 * Provides validation, stock management, and status update methods.
 */
public class Product {

    private final ProductId id;
    private String name;
    private String size;
    private int priceCents;
    private int stockQuantity;
    private int lowStockAlert;
    private String imageUrl;
    private boolean isActive;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Creates a new Product instance.
     */
    private Product(ProductId id, String name, String size, int priceCents,
                    int stockQuantity, int lowStockAlert, String imageUrl,
                    boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = Objects.requireNonNull(id, "A product must have an id");

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("A product must have a name");
        }
        this.name = name;

        if (size == null || size.trim().isEmpty()) {
            throw new IllegalArgumentException("A product must have a size");
        }
        this.size = size;

        if (priceCents <= 0) {
            throw new IllegalArgumentException("A product priceCents should be more than zero");
        }
        this.priceCents = priceCents;

        if (stockQuantity < 0) {
            throw new IllegalArgumentException("A product stockQuantity should be positive");
        }
        this.stockQuantity = stockQuantity;
        if (lowStockAlert < 0) {
            throw new IllegalArgumentException("A product lowStockAlert should be positive");
        }
        this.lowStockAlert = lowStockAlert;

        this.imageUrl = imageUrl;

        this.isActive = isActive;

        if (createdAt.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("A product createdAt cannot be in the future");
        }
        this.createdAt = Objects.requireNonNull(createdAt, "A product must have a createdAt date");

        if (updatedAt.isBefore(createdAt)) {
            throw new IllegalArgumentException("A product updatedAt cannot be before createdAt");
        }
        this.updatedAt = createdAt;
    }

    /**
     * Factory method to create a new Product.
     */
    public static Product create(ProductId id, String name, String size, int priceCents,
                                 int stockQuantity, int lowStockAlert, String imageUrl, boolean isActive) {
        LocalDateTime now = LocalDateTime.now();

        return new Product(id, name, size, priceCents, stockQuantity,
                lowStockAlert, imageUrl, isActive, now, now);
    }

    /**
     * Increases the stock quantity by the given amount.
     */
    public void increaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Increase amount must be positive");
        }
        this.stockQuantity += amount;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Decreases the stock quantity by the given amount.
     */
    public void decreaseStock(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Decrease amount must be positive");
        }
        if (this.stockQuantity - amount < 0) {
            throw new IllegalArgumentException("Insufficient stock to decrease by " + amount);
        }
        this.stockQuantity -= amount;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Sets the product as active or inactive.
     */
    public void activate() {
        this.isActive = true;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Sets the product as inactive.
     */
    public void inactive() {
        this.isActive = false;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates the product price.
     */
    public void updatePrice(int newPriceCents) {
        if (newPriceCents <= 0) {
            throw new IllegalArgumentException("A product priceCents should be more than zero");
        }
        this.priceCents = newPriceCents;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates product details.
     */
    public void updateDetails(String newName, String newSize, String newImageUrl, int newLowStockAlert) {
        if (!this.isActive) {
            throw new IllegalStateException("Cannot update details of an inactive product");
        }

        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("A product must have a name");
        }
        this.name = newName;
        if (newSize == null || newSize.trim().isEmpty()) {
            throw new IllegalArgumentException("A product must have a size");
        }
        this.size = newSize;

        this.imageUrl = newImageUrl;

        if (newLowStockAlert < 0) {
            throw new IllegalArgumentException("A product lowStockAlert should be positive");
        }
        this.lowStockAlert = newLowStockAlert;

        this.updatedAt = LocalDateTime.now();
    }

    // Getters

    public ProductId getId() {
        return id;
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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public int getLowStockAlert() {
        return lowStockAlert;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt; }
}
