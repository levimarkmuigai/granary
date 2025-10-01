package com.example.granary_backend.application.command.order;

import java.util.List;
import java.util.Objects;

import com.example.granary_backend.domain.model.Order.DeliveryMethod;

public final class CreateOrderCommand {

  private final List<OrderLineCommand> orderLines;
  private final CustomerDetailsCommand customerDetails;
  private final DeliveryMethod deliveryMethod;


  public CreateOrderCommand(
    List<OrderLineCommand> orderLines,
    CustomerDetailsCommand customerDetails,
    DeliveryMethod deliveryMethod
  ) {

    Objects.requireNonNull(orderLines, "orderLines cannot be null");
    Objects.requireNonNull(customerDetails, "customerDetailsCommand cannot be null");
    Objects.requireNonNull(deliveryMethod, "deliveryMethod cannot be null");

    this.orderLines = List.copyOf(orderLines);
    this.customerDetails = customerDetails;
    this.deliveryMethod = deliveryMethod;
  }

  // Getters
  public List<OrderLineCommand> getOrderLines() {
    return orderLines;
  }

  public CustomerDetailsCommand getCustomerDetails() {
    return customerDetails;
  }
  public DeliveryMethod getDeliveryMethod() {
    return deliveryMethod;
  }

  @Override
  public String toString() {
    return "CreateOrderCommand{" +
    "orderLines='" + orderLines + '\'' +
    ", customerDetails='" + customerDetails + '\'' +
    ", deliveryMethod='" + deliveryMethod + '\'' +
    '}';
  }

  public static final class OrderLineCommand {
    private final String productId;
    private final int quantity;

    public OrderLineCommand(String productId, int quantity) {

      this.productId = productId;
      this.quantity = quantity;

    }

    public String getProductId() { return productId; }
    public int getQuantity() { return quantity; }

  }

  public static final class CustomerDetailsCommand {
    private final String name;
    private final String email;
    private final String phone;
    private final String address;

    public CustomerDetailsCommand(
      String name,
      String email,
      String phone,
      String address
    ) {

      this.name = Objects.requireNonNull(name, "Customer name cannot be null");
      this.email = Objects.requireNonNull(email, "Customer email cannot be null");
      this.phone = Objects.requireNonNull(phone, "Customer phone cannot be null");
      this.address = Objects.requireNonNull(address, "Customer address cannot be null");
    }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
      return "CustomerDetailsCommand{name='" + name + "', email='" + email + "'}";
    }

  }
}
