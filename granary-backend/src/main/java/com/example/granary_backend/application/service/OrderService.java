package com.example.granary_backend.application.service;

import java.util.Objects;

import com.example.granary_backend.application.command.order.AdvanceOrderCommand;
import com.example.granary_backend.application.command.order.CreateOrderCommand;
import com.example.granary_backend.application.command.order.MarkOrderPaidCommand;
import com.example.granary_backend.domain.model.Order;
import com.example.granary_backend.domain.model.OrderId;
import com.example.granary_backend.domain.model.ProductId;
import com.example.granary_backend.domain.model.Product;
import com.example.granary_backend.domain.port.ProductRepository;
import com.example.granary_backend.domain.port.OrderRepository;

public class OrderService{

  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {

    this.orderRepository = Objects.requireNonNull(orderRepository, "OrderRepository cannot be null");
    this.productRepository = Objects.requireNonNull(productRepository, "ProductRepository cannot be null");

  }

  public OrderId createOrder(CreateOrderCommand command){
    Objects.requireNonNull(command, "CreateOrderCommand cannot be null");

    var productId = ProductId.fromString(command.getProductId());

    var product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + command.getProductId()));
    if (!product.isActive()) {
      throw new IllegalArgumentException("Product is not active for ID: " + command.getProductId());
    }

    if (product.isActive() && product.getStockQuantity() < command.getQuantityOrdered()) {
      throw new IllegalArgumentException("Insufficient stock for product ID: " + command.getProductId());
    }

    Order.CustomerDetails customerDetails = new Order.CustomerDetails(
      command.getCustomerName(),
      command.getCustomerPhone(),
      command.getCustomerEmail(),
      command.getCustomerAddress()
    );

    Order.DeliveryMethod deliveryMethod = switch(command.getDeliveryMethod()) {
      case "pickup" -> Order.DeliveryMethod.PICKUP;
      case "delivery" -> Order.DeliveryMethod.DELIVERY;
      default -> throw new IllegalArgumentException("Invalid delivery method: " + command.getDeliveryMethod());
    };

    Order order = Order.create(
      OrderId.createNew(),
      product,
      command.getQuantityOrdered(),
      customerDetails,
      deliveryMethod
    );
    orderRepository.save(order);

    return order.getId();

  }


  public OrderId markOrderAsPaid(MarkOrderPaidCommand command){
    Objects.requireNonNull(command, "MarkOrderPaidCommand cannot be null");

    var orderId = OrderId.fromString(command.getOrderId());

    var order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + command.getOrderId()));

    if(order.getPaymentStatus() == Order.PaymentStatus.PAID) {
      throw new IllegalArgumentException("Order is already marked as paid");
    }

    if(order.getOrderStatus() == Order.OrderStatus.DELIVERED) {
      throw new IllegalArgumentException("Order is already delivered and cannot be marked as paid with ID: " + command.getOrderId());
    }

    order.markAsPaid(command.getMpesaTransactionId());
    orderRepository.save(order);

    return order.getId();
  }

  public OrderId advanceOrderStatus(AdvanceOrderCommand command) {
    Objects.requireNonNull(command, "AdvanceOrderCommand cannot be null");

    var orderId = OrderId.fromString(command.getOrderId());

    var order = orderRepository.findById(orderId)
        .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + command.getOrderId()));

    order.advanceStatus();
    orderRepository.save(order);
    return order.getId();
  }
}
