package com.example.granary_backend.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class ProductEntity {

  @Id
  private UUID
  id;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Column(name = "size", nullable = false, length = 20)
  private String size;

  @Column(name = "price_cents", nullable = false)
  @Min(1)
  private int priceCents;

  @Column(name = "stock_quantity", nullable = false)
  @Min(0)
  private int stockQuantity;

  @Column(name = "low_stock_alert", nullable = false)
  @Min(0)
  private int lowStockAlert;

  @Column(name = "image_url")
  @Size(max = 500)
  private String imageUrl;

  @Column(name = "is_active", nullable = false)
  private boolean isActive;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false, nullable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  protected ProductEntity() {}

  public ProductEntity(UUID id, String name, String size, int priceCents, int stockQuantity, String imageUrl,
    int lowStockAlert, boolean isActive, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.name = name;
    this.size = size;
    this.priceCents = priceCents;
    this.stockQuantity = stockQuantity;
    this.lowStockAlert = lowStockAlert;
    this.imageUrl = imageUrl;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public UUID getId() { return this.id; }
  public String getName() { return this.name; }
  public String getSize() { return this.size; }
  public int getPriceCents() { return this.priceCents; }
  public int getStockQuantity() { return this.stockQuantity; }
  public int getLowStockAlert() { return this.lowStockAlert; }
  public String getImageUrl() { return this.imageUrl; }
  public boolean isActive() { return this.isActive; }
  public LocalDateTime getCreatedAt() { return this.createdAt; }
  public LocalDateTime getUpdatedAt() { return this.updatedAt; }
}
