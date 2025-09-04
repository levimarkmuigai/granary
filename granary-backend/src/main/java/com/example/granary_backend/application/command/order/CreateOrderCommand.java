package com.example.granary_backend.application.command.order;

import java.util.Objects;
import java.util.regex.Pattern;

public final class CreateOrderCommand {

  private final String productId;
  private final int quantityOrdered;
  private final String customerName;
  private final String customerEmail;
  private final String customerPhone;
  private final String customerAddress;
  private final String deliveryMethod;

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

  public CreateOrderCommand(String productId, int quantityOrdered, String customerName,
                            String customerEmail, String customerPhone, String customerAddress,
                            String deliveryMethod) {

    this.productId = Objects.requireNonNull(productId, "Product ID cannot be null");
    if (quantityOrdered <= 0) {
      throw new IllegalArgumentException("Quantity ordered must be greater than zero");
    }
    this.quantityOrdered = quantityOrdered;

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
  public String getProductId() {
    return productId;
  }
  public int getQuantityOrdered() {
    return quantityOrdered;
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
        "productId='" + productId + '\'' +
        ", quantityOrdered=" + quantityOrdered +
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
}
