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

  private DeliveryStatus deliveryStatus;
  private PaymentStatus paymentStatus;
  private OrderStatus orderStatus;
  private MpesaTransactionId mpesaTransactionId;
  private String mpesaCheckoutRequestId;
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  private Order(OrderId id, List<OrderLine> orderLines, CustomerDetails customerDetails, DeliveryStatus deliveryStatus,
      PaymentStatus paymentStatus, OrderStatus orderStatus, MpesaTransactionId mpesaTransactionId,
      String mpesaCheckoutRequestId, LocalDateTime createdAt, LocalDateTime updatedAt) {

    if (createdAt.isAfter(LocalDateTime.now())) {
      throw new IllegalArgumentException("Order createdAt cannot be in the future");
    }

    this.id = id;
    this.orderLines = List.copyOf(orderLines);
    this.customerDetails = Objects.requireNonNull(customerDetails, "Customer details must not be null");

    this.deliveryStatus = deliveryStatus;
    this.paymentStatus = paymentStatus;
    this.orderStatus = orderStatus;

    this.mpesaTransactionId = mpesaTransactionId;
    this.mpesaCheckoutRequestId = mpesaCheckoutRequestId;

    this.createdAt = Objects.requireNonNull(createdAt, "Order must have a createdAt timestamp");
    this.updatedAt = updatedAt;
  }

  public static Order createFromOrderLines(OrderId id, List<OrderLine> orderLines,
      CustomerDetails customerDetails) {

    if (orderLines == null || orderLines.isEmpty()) {
      throw new IllegalArgumentException("Order must have at least one order line");
    }

    LocalDateTime now = LocalDateTime.now();

    return new Order(
        id,
        orderLines,
        customerDetails,
        DeliveryStatus.PENDING,
        PaymentStatus.AWAITTING_INITIATION,
        OrderStatus.NEW,
        null,
        null,
        now,
        now);
  }

  public static Order reconstitute(
      OrderId id, List<OrderLine> orderLines, CustomerDetails customerDetails,
      DeliveryStatus deliveryStatus, PaymentStatus paymentStatus, OrderStatus orderStatus,
      MpesaTransactionId mpesaTransactionId, String mpesaCheckoutRequestId,
      LocalDateTime createdAt, LocalDateTime updatedAt) {

    return new Order(
        id, orderLines, customerDetails, deliveryStatus, paymentStatus, orderStatus,
        mpesaTransactionId, mpesaCheckoutRequestId, createdAt, updatedAt);
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

    if (this.paymentStatus != PaymentStatus.AWAITTING_INITIATION) {
      throw new IllegalStateException("Payment initiation can only occur when status is AWAITTING_INITIATION.");
    }

    this.mpesaCheckoutRequestId = checkoutRequestId;
    this.paymentStatus = PaymentStatus.PENDING;
    this.updatedAt = LocalDateTime.now();
  }

  public void markAsPaid(MpesaTransactionId mpesaTransactionId, int amountPaid) {
    Objects.requireNonNull(mpesaTransactionId, "MpesaTransactionId cannot be null");

    if (this.getTotalAmountCents() != amountPaid) {
      throw new IllegalStateException(
          "Payment amount mismatch during markAsPaid. Expected: "
              + this.getTotalAmountCents() + ", Received: " + amountPaid);
    }

    if (this.paymentStatus != PaymentStatus.PENDING &&
        this.paymentStatus != PaymentStatus.AWAITTING_INITIATION) {

      throw new IllegalStateException(
          "Order cannot be marked as paid from status: " + this.paymentStatus);
    }

    if (this.paymentStatus == PaymentStatus.SUCCESS) {
      return;
    }

    this.mpesaTransactionId = mpesaTransactionId;
    this.paymentStatus = PaymentStatus.SUCCESS;
    this.updatedAt = LocalDateTime.now();
  }

  public void failedPayment() {
    if (this.paymentStatus != PaymentStatus.PENDING &&
        this.paymentStatus != PaymentStatus.AWAITTING_INITIATION) {

      throw new IllegalStateException(
          "Payment cannot be marked as failed from status: " + this.paymentStatus);
    }

    if (paymentStatus == PaymentStatus.SUCCESS) {
      return;
    }

    this.paymentStatus = PaymentStatus.FAILED;
    this.updatedAt = LocalDateTime.now();
  }

  public void cancelPayment() {
    if (this.paymentStatus == PaymentStatus.SUCCESS) {
      throw new IllegalStateException("Cannot cancel payment; it has already been processed successfully.");
    }

    if (this.paymentStatus != PaymentStatus.AWAITTING_INITIATION &&
        this.paymentStatus != PaymentStatus.PENDING &&
        this.paymentStatus != PaymentStatus.FAILED) {

      if (this.paymentStatus == PaymentStatus.CANCELLED) {
        return;
      }

      throw new IllegalStateException(
          "Payment cannot be cancelled from current status: " + this.paymentStatus);
    }

    this.paymentStatus = PaymentStatus.CANCELLED;
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
    if (this.orderStatus != OrderStatus.NEW && this.orderStatus != OrderStatus.PACKAGING) {
      throw new IllegalStateException("Customer details can only be updated for NEW or PACKAGING orders.");
    }
    this.customerDetails = Objects.requireNonNull(newDetails, "New customer details cannot be null");
    this.updatedAt = LocalDateTime.now();
  }

  public void updateDeliveryStatus(DeliveryStatus newMethod) {
    if (this.orderStatus != OrderStatus.NEW && this.orderStatus != OrderStatus.PACKAGING) {
      throw new IllegalStateException("Delivery method can only be updated for NEW or PACKAGING orders.");
    }
    this.deliveryStatus = Objects.requireNonNull(newMethod, "New delivery method cannot be null");
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

  public DeliveryStatus getDeliveryStatus() {
    return deliveryStatus;
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
    private final String name;
    private final String phone;
    private final String email;
    private final String address;

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

  public enum DeliveryStatus {
    DELIVERED,
    PENDING
  }

  public enum PaymentStatus {
    AWAITTING_INITIATION,
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