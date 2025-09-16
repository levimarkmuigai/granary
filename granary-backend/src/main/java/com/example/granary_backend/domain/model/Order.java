package com.example.granary_backend.domain.model;

import java.util.Objects;
import java.util.regex.Pattern;
import java.time.LocalDateTime;

public class Order{
  private final OrderId id;
  private final Product product;
  private int quantityOrdered;
  private int totalAmountCents;
  private CustomerDetails customerDetails;
  private DeliveryMethod deliveryMethod;
  private PaymentStatus paymentStatus;
  private OrderStatus orderStatus;
  private String mpesaTransactionId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private Order(OrderId id, Product product, int quantityOrdered, int totalAmountCents,
    CustomerDetails customerDetails, DeliveryMethod deliveryMethod,
    PaymentStatus paymentStatus,OrderStatus orderStatus,LocalDateTime createdAt, LocalDateTime updatedAt) {

    this.id = id;
    this.product = product;
    this.quantityOrdered = quantityOrdered;
    this.totalAmountCents = totalAmountCents;
    if(this.totalAmountCents <= 0) {
      throw new IllegalArgumentException("Order totalAmountCents must be greater than zero");
    }
    this.customerDetails = customerDetails;
    this.deliveryMethod = deliveryMethod;
    this.paymentStatus = paymentStatus;
    this.orderStatus = orderStatus;
    if (createdAt.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("Order createdAt cannot be in the future");
    }
    this.createdAt = Objects.requireNonNull(createdAt, "Order must have a createdAt timestamp");
    this.updatedAt = createdAt;
  }

  public static Order create(OrderId id, Product product,int quantityOrdered,
    CustomerDetails customerDetails,DeliveryMethod deliveryMethod) {
    if (id == null) {
      throw new IllegalArgumentException("Order must have an id");
    }
    if (quantityOrdered <= 0) {
      throw new IllegalArgumentException("Order quantityOrdered must be greater than zero");
    }
    if (customerDetails == null) {
      throw new IllegalArgumentException("Order must have customer details");
    }
    if (deliveryMethod == null) {
      throw new IllegalArgumentException("Order must have a delivery method");
    }
    LocalDateTime now = LocalDateTime.now();

    return new Order(
      id,
      product,
      quantityOrdered,
      product.getPriceCents() * quantityOrdered,
      customerDetails,
      deliveryMethod,
      PaymentStatus.PENDING,
      OrderStatus.NEW,
      now,
      now
    );
  }

  public void markAsPaid(String mpesaTransactionId) {
    if(mpesaTransactionId == null || mpesaTransactionId.trim().isEmpty()) {
      throw new IllegalArgumentException("Mpesa transaction ID cannot be null or empty");
    }
    this.mpesaTransactionId = mpesaTransactionId;

    if(this.paymentStatus != PaymentStatus.PENDING) {
      throw new IllegalStateException("Order cannot be marked as paid if it is not pending");
    }
    this.paymentStatus = PaymentStatus.PAID;
    this.updatedAt = LocalDateTime.now();
  }

  public void failedPayment() {
    if(this.paymentStatus != PaymentStatus.PENDING) {
      throw new IllegalStateException("Order cannot be marked as failed if it is not pending");
    }
    this.paymentStatus = PaymentStatus.FAILED;
    this.updatedAt = LocalDateTime.now();
  }

  public void advanceStatus() {
    if (this.orderStatus == OrderStatus.DELIVERED) {
      throw new IllegalStateException("Order is already delivered and cannot be advanced further");
    }

    if (this.paymentStatus != PaymentStatus.PAID) {
      throw new IllegalStateException("Order must be paid before advancing status");
    }

    switch (this.orderStatus) {
      case NEW: this.orderStatus = OrderStatus.PACKAGING;
      break;
      case PACKAGING: this.orderStatus = OrderStatus.READY;
      break;
      case READY: this.orderStatus = OrderStatus.DELIVERED;
      break;
      default: throw new IllegalStateException("Cannot advance order status from " + this.orderStatus + " to next status");
    }
    this.updatedAt = LocalDateTime.now();
  }

  public void updateCustomerDetails(CustomerDetails newDetails) {
    if (newDetails == null) {
      throw new IllegalArgumentException("New customer details cannot be null");
    }
    this.customerDetails = newDetails;
    this.updatedAt = LocalDateTime.now();
  }

  public void updateDeliveryMethod(DeliveryMethod newMethod) {
    if (newMethod == null) {
      throw new IllegalArgumentException("New delivery method cannot be null");
    }
    this.deliveryMethod = newMethod;
    this.updatedAt = LocalDateTime.now();
  }

  public void recalculateTotalAmount() {
    if (this.product == null) {
      throw new IllegalStateException("Cannot calculate total amount without a product");
    }
    this.totalAmountCents = this.product.getPriceCents() * this.quantityOrdered;
    this.updatedAt = LocalDateTime.now();
  }

  public OrderId getId() {
    return id;
  }

  public Product getProduct() {
    return product;
  }

  public int getQuantityOrdered() {
    return quantityOrdered;
  }

  public int getTotalAmountCents() {
    return totalAmountCents;
  }

  public CustomerDetails getCustomerDetails() {
    return customerDetails;
  }

  public String getMpesaTransactionId() {
    return mpesaTransactionId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public DeliveryMethod getDeliveryMethod() {
    return deliveryMethod;
  }

  public PaymentStatus getPaymentStatus() {
    return paymentStatus;
  }

  public OrderStatus getOrderStatus() {
    return orderStatus;
  }

  public static final class CustomerDetails {
    private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    private String name;
    private String phone;
    private String email;
    private String address;

    public CustomerDetails(String name, String phone,
      String email, String address) {

      this.name = Objects.requireNonNull(name, "Customer name cannot be null");
      if (name.trim().isEmpty()) {
        throw new IllegalArgumentException("Customer name cannot be empty");
      }

      this.phone = Objects.requireNonNull(phone, "Customer phone cannot be null");
      if (phone.trim().isEmpty()) {
        throw new IllegalArgumentException("Customer phone cannot be empty");
      }

      this.email = validateAndCleanEmail(email);

      this.address = Objects.requireNonNull(address, "Customer address cannot be null");
      if (address.trim().isEmpty()) {
        throw new IllegalArgumentException("Customer address cannot be empty");
      }
    }

    public static CustomerDetails create(String name, String phone,
      String email, String address) {
      return new CustomerDetails(name, phone, email, address);
    }

    private String validateAndCleanEmail(String email) {
      if (email == null || email.trim().isEmpty()) {
        throw new IllegalArgumentException("Email cannot be null or empty");
      }
      if (!EMAIL_PATTERN.matcher(email).matches()) {
        throw new IllegalArgumentException("Invalid email format");
      }
      return email.trim().toLowerCase();
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
  }

  public enum DeliveryMethod {
    PICKUP,
    DELIVERY
  }

 public enum PaymentStatus {
    PENDING,
    PAID,
    FAILED,
  }

 public enum OrderStatus {
    NEW,
    PACKAGING,
    READY,
    DELIVERED,
  }
}

