package com.example.granary_backend.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import com.example.granary_backend.domain.model.value.ProductId;
import com.example.granary_backend.domain.model.value.MpesaTransactionId;
import com.example.granary_backend.domain.model.value.OrderId;

public final class Order {

  private final OrderId id;
  private List<OrderLine> orderLines;
  private CustomerDetails customerDetails;
  private DeliveryMethod deliveryMethod;
  private PaymentStatus paymentStatus;
  private OrderStatus orderStatus;
  private MpesaTransactionId mpesaTransactionId;
  private String mpesaCheckoutRequestId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private Order(OrderId id, List<OrderLine> orderLines, CustomerDetails customerDetails, DeliveryMethod deliveryMethod,
      PaymentStatus paymentStatus, OrderStatus orderStatus, LocalDateTime createdAt, LocalDateTime updatedAt) {

    if (createdAt.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("Order createdAt cannot be in the future");
    }

    this.id = id;
    this.orderLines = orderLines;
    this.customerDetails = customerDetails;
    this.deliveryMethod = deliveryMethod;
    this.paymentStatus = paymentStatus;
    this.orderStatus = orderStatus;
    this.mpesaCheckoutRequestId = null;
    this.createdAt = Objects.requireNonNull(createdAt, "Order must have a createdAt timestamp");
    this.updatedAt = createdAt;
  }

  public static Order createFromOrderLines(OrderId id, List<OrderLine> orderLines,
      CustomerDetails customerDetails, DeliveryMethod deliveryMethod) {

    if (orderLines == null || orderLines.isEmpty()) {
      throw new IllegalArgumentException("Order must have at least one order line");
    }

    LocalDateTime now = LocalDateTime.now();

    return new Order(
        id,
        orderLines,
        customerDetails,
        deliveryMethod,
        PaymentStatus.PENDING,
        OrderStatus.NEW,
        now,
        now);
  }

  public int getTotalAmountCents() {
    return orderLines
        .stream()
        .mapToInt(OrderLine::getLineTotalCents)
        .sum();
  }

  public void markPaymentInitiated(String checkoutRequestId) {
    Objects.requireNonNull(checkoutRequestId, "Checkout Request ID cannot be null.");
    if (checkoutRequestId.isBlank()) {
      throw new IllegalArgumentException("Checkout Request ID cannot be blank.");
    }

    if (this.paymentStatus != PaymentStatus.PENDING) {
      throw new IllegalStateException("Payment initiation can only occur when status is PENDING.");
    }

    this.mpesaCheckoutRequestId = checkoutRequestId;
    this.updatedAt = LocalDateTime.now();
  }

  public void markAsPaid(MpesaTransactionId mpesaTransactionId) {
    Objects.requireNonNull(mpesaTransactionId, "MpesaTransactionId cannot be null");
    if (mpesaTransactionId.value().isBlank()) {
      throw new IllegalArgumentException("MpesaTransactionId cannot be blank");
    }
    this.mpesaTransactionId = mpesaTransactionId;

    if (this.paymentStatus != PaymentStatus.PENDING) {
      throw new IllegalStateException("Order cannot be marked as paid if it is not pending");
    }

    this.mpesaTransactionId = mpesaTransactionId;
    this.paymentStatus = PaymentStatus.SUCCESS;
    this.updatedAt = LocalDateTime.now();
  }

  public void failedPayment() {
    if (this.paymentStatus != PaymentStatus.PENDING) {
      throw new IllegalStateException("Order cannot be marked as failed if it is not pending");
    }
    this.paymentStatus = PaymentStatus.FAILED;
    this.updatedAt = LocalDateTime.now();
  }

  public void advanceStatus() {
    if (this.orderStatus == OrderStatus.DELIVERED) {
      throw new IllegalStateException("Order is already delivered and cannot be advanced further");
    }

    if (this.paymentStatus != PaymentStatus.SUCCESS) {
      throw new IllegalStateException("Order must be paid before advancing status");
    }

    this.orderStatus = switch (this.orderStatus) {
      case NEW -> OrderStatus.PACKAGING;
      case PACKAGING -> OrderStatus.READY;
      case READY -> OrderStatus.DELIVERED;
      default -> throw new IllegalStateException("Unexpected value: " + this.orderStatus);
    };
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

  public OrderId getId() {
    return id;
  }

  public List<OrderLine> getOrderLines() {
    return List.copyOf(orderLines);
  }

  public CustomerDetails getCustomerDetails() {
    return customerDetails;
  }

  public MpesaTransactionId getMpesaTransactionId() {
    return mpesaTransactionId;
  }

  public String getMpesaCheckoutRequest() {
    return mpesaCheckoutRequestId;
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

  public static final class OrderLine {

    private final ProductId productId;
    private final String productName;
    private final int unitPriceCents;
    private final int quantityOrdered;
    private final int lineTotalCents;

    private OrderLine(ProductId productId, String productName,
        int unitPriceCents, int quantityOrdered) {

      if (unitPriceCents <= 0) {
        throw new IllegalArgumentException("unitPriceCents must be more than zero");
      }

      if (quantityOrdered <= 0) {
        throw new IllegalArgumentException("quantityOrdered must be more than zero");
      }

      this.productId = productId;
      this.productName = productName;
      this.unitPriceCents = unitPriceCents;
      this.quantityOrdered = quantityOrdered;
      this.lineTotalCents = quantityOrdered * unitPriceCents;

    }

    public static OrderLine create(ProductId productId, String productName,
        int unitPriceCents, int quantityOrdered) {

      return new OrderLine(productId, productName, unitPriceCents, quantityOrdered);
    }

    public ProductId getProductId() {
      return this.productId;
    }

    public String getProductName() {
      return this.productName;
    }

    public int getUnitPriceCents() {
      return this.unitPriceCents;
    }

    public int getQuantityOrdered() {
      return this.quantityOrdered;
    }

    public int getLineTotalCents() {
      return this.lineTotalCents;
    }

  }

  public static final class CustomerDetails {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
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

    public String getName() {
      return name;
    }

    public String getPhone() {
      return phone;
    }

    public String getEmail() {
      return email;
    }

    public String getAddress() {
      return address;
    }
  }

  public enum DeliveryMethod {
    PICKUP,
    DELIVERY
  }

  public enum PaymentStatus {
    PENDING,
    SUCCESS,
    FAILED,
    CANCELLED
  }

  public enum OrderStatus {
    NEW,
    PACKAGING,
    READY,
    DELIVERED
  }
}
