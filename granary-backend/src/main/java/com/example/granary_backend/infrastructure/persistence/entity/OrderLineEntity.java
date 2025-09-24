package com.example.granary_backend.infrastructure.persistence.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "order_lines")
public class OrderLineEntity {

  @Id
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "order_id", nullable = false)
  private OrderEntity order;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "product_id", nullable = false)
  private ProductEntity productEntity;

  @Column(name = "quantity_ordered", nullable = false)
  @Min(1)
  private int quantityOrdered;

  @Column(name = "unit_price_cents", nullable = false)
  @Min(1)
  private int unitPriceCents;

  @Column(name = "line_total_cents", nullable = false)
  private int lineTotalCents;

  protected OrderLineEntity() {}

  public OrderLineEntity(UUID id, OrderEntity order,ProductEntity productEntity,
    int quantityOrdered, int unitPriceCents,int lineTotalCents) {

    this.id = id;
    this.order = order;
    this.productEntity = productEntity;
    this.quantityOrdered = quantityOrdered;
    this.unitPriceCents = unitPriceCents;
    this.lineTotalCents = lineTotalCents;
  }

  public UUID getId() { return this.id; }
  public OrderEntity getOrder() { return this.order; }
  public ProductEntity getProductEntity() { return this.productEntity; }
  public int getQuantityOrdered() { return this.quantityOrdered; }
  public int getUnitPriceCents() { return this.unitPriceCents; }
  public int getLineTotalCents() { return this.lineTotalCents; }

  void assignToOrder(OrderEntity order) {
    this.order = order;
  }
}
