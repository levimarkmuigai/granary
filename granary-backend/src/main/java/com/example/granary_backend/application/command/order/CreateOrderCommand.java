package com.example.granary_backend.application.command.order;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import com.example.granary_backend.domain.model.Order;

public final class CreateOrderCommand {

  private final List<OrderLineCommand> orderLines;
  private final String customerName;
  private final String customerEmail;
  private final String customerPhone;
  private final String customerAddress;
  private final String deliveryMethod;

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

  public CreateOrderCommand(List<OrderLineCommand> orderLines,
    String customerName,
    String customerEmail,
    String customerPhone,
    String customerAddress,
    String deliveryMethod
  ) {

    Objects.requireNonNull(orderLines, "Order must contain at least one product line");
    this.orderLines = List.copyOf(orderLines);

    Objects.requireNonNull(customerName, "Customer name cannot be null");
    if (customerName.isBlank()) {
      throw new IllegalArgumentException("Customer name cannot be blank");
    }
    this.customerName = customerName.trim();

    this.customerEmail = validateEmail(customerEmail);

    Objects.requireNonNull(customerPhone, "Customer phone cannot be null");
    if (customerPhone.isBlank()) {
      throw new IllegalArgumentException("Customer phone cannot be blank");
    }
    this.customerPhone = customerPhone.trim();

    Objects.requireNonNull(customerAddress, "Customer address cannot be null");
    if (customerAddress.isBlank()) {
      throw new IllegalArgumentException("Customer address cannot be blank");
    }
    this.customerAddress = customerAddress.trim();

    if (deliveryMethod == null || deliveryMethod.trim().isBlank()) {
      throw new IllegalArgumentException("Delivery method cannot be null or blank");
    }
    String normalizedDeliveryMethod = deliveryMethod.trim().toLowerCase();
    if (!normalizedDeliveryMethod.equals("pickup") && !normalizedDeliveryMethod.equals("delivery")) {
      throw new IllegalArgumentException("Delivery method must be either 'pickup' or 'delivery'");
    }
    this.deliveryMethod = normalizedDeliveryMethod;
  }

  // Getters
  public List<OrderLineCommand> getOrderLines() {
    return orderLines;
  }
  public String getCustomerName() {
    return customerName;
  }
  public String getCustomerEmail() {
    return customerEmail;
  }
  public String getCustomerPhone() {
    return customerPhone;
  }
  public String getCustomerAddress() {
    return customerAddress;
  }
  public String getDeliveryMethod() {
    return deliveryMethod;
  }

  @Override
  public String toString() {
    return "CreateOrderCommand{" +
        "orderLines='" + orderLines + '\'' +
        ", customerName='" + customerName + '\'' +
        ", customerEmail='" + customerEmail + '\'' +
        ", customerPhone='" + customerPhone + '\'' +
        ", customerAddress='" + customerAddress + '\'' +
        ", deliveryMethod='" + deliveryMethod + '\'' +
        '}';
  }

  private static String validateEmail(String email) {

    if (email == null || email.trim().isEmpty()) {
      throw new IllegalArgumentException("Email cannot be null or empty");
    }

    String cleanEmail = email.trim().toLowerCase();

    if (!EMAIL_PATTERN.matcher(cleanEmail).matches()) {
      throw new IllegalArgumentException("Invalid email format" + cleanEmail);
    }

    return cleanEmail;
  }

  public final static class OrderLineCommand {
    private final String productId;
    private final int quantity;

    public OrderLineCommand(String productId, int quantity) {
      Objects.requireNonNull(productId, "productId cannot be null");
      if(productId.isBlank()) {
        throw new IllegalArgumentException("productId cannot be blank");
      }

      this.productId = productId;

      this.quantity = quantity;
    }

    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }
  }
}
