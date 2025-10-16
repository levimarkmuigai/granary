package com.example.granary_backend.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.granary_backend.domain.model.Order.DeliveryStatus;
import com.example.granary_backend.domain.model.Order.OrderStatus;
import com.example.granary_backend.domain.model.Order.PaymentStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "orders")
public class OrderEntity {

  @Id
  private UUID id;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<OrderLineEntity> orderLines = new ArrayList<>();

  @Column(name = "mpesa_checkout_request_id")
  private String mpesaCheckoutRequestId;

  @Column(name = "mpesa_transaction_id")
  private String mpesaTransactionId;

  @Column(name = "created_at", nullable = false, updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "delivery_status", nullable = false)
  private DeliveryStatus deliveryStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_status", nullable = false)
  private PaymentStatus paymentStatus;

  @Enumerated(EnumType.STRING)
  @Column(name = "order_status", nullable = false)
  private OrderStatus orderStatus;

  @Column(name = "customer_name", nullable = false)
  private String customerName;

  @Column(name = "customer_phone", nullable = false)
  private String customerPhone;

  @Column(name = "customer_address", nullable = false)
  private String customerAddress;

  @Column(name = "customer_email", nullable = false)
  private String customerEmail;

  public OrderEntity() {
  }

  public OrderEntity(UUID id, List<OrderLineEntity> orderLines,
      String mpesaTransactionId, LocalDateTime createdAt, LocalDateTime updatedAt,
      DeliveryStatus deliveryStatus, PaymentStatus paymentStatus, OrderStatus orderStatus,
      String customerName, String customerPhone, String customerAddress, String customerEmail) {

    this.id = id;
    this.orderLines = orderLines;
    for (OrderLineEntity line : orderLines) {
      line.assignToOrder(this);
    }

    this.mpesaTransactionId = mpesaTransactionId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.deliveryStatus = deliveryStatus;
    this.paymentStatus = paymentStatus;
    this.orderStatus = orderStatus;
    this.customerName = customerName;
    this.customerAddress = customerAddress;
    this.customerPhone = customerPhone;
    this.customerEmail = customerEmail;
  }

  public UUID getId() {
    return this.id;
  }

  public List<OrderLineEntity> getOrderLines() {
    return this.orderLines;
  }

  public String getMpesaCheckoutRequestId() {
    return this.mpesaCheckoutRequestId;
  }

  public String getMpesaTransactionId() {
    return this.mpesaTransactionId;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public DeliveryStatus getDeliveryStatus() {
    return this.deliveryStatus;
  }

  public PaymentStatus getPaymentStatus() {
    return this.paymentStatus;
  }

  public OrderStatus getOrderStatus() {
    return this.orderStatus;
  }

  public String getCustomerName() {
    return this.customerName;
  }

  public String getCustomerEmail() {
    return this.customerEmail;
  }

  public String getCustomerPhone() {
    return this.customerPhone;
  }

  public String getCustomerAddress() {
    return this.customerAddress;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setMpesaCheckoutRequestId(String mpesaCheckoutRequestId) {
    this.mpesaCheckoutRequestId = mpesaCheckoutRequestId;
  }

  public void setMpesaTransactionId(String mpesaTransactionId) {
    this.mpesaTransactionId = mpesaTransactionId;
  }

  public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
    this.deliveryStatus = deliveryStatus;
  }

  public void setPaymentStatus(PaymentStatus paymentStatus) {
    this.paymentStatus = paymentStatus;
  }

  public void setOrderStatus(OrderStatus orderStatus) {
    this.orderStatus = orderStatus;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public void setCustomerAddress(String customerAddress) {
    this.customerAddress = customerAddress;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public void addOrderLine(OrderLineEntity line) {

    this.orderLines.add(line);
    line.setOrder(this);

  }
}
