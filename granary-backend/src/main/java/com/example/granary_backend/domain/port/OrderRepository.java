package com.example.granary_backend.domain.port;

import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;

import com.example.granary_backend.domain.model.Order;
import com.example.granary_backend.domain.model.value.OrderId;

public interface OrderRepository {
  Optional<Order> findById(OrderId id);
  List<Order> findAll();
  List<Order> findByStatus(Order.OrderStatus status);
  List<Order> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
  Order save(Order order);
  boolean delete(OrderId id);
}

