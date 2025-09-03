package com.example.granary_backend.port;

import java.util.Optional;
import java.util.List;
import java.time.LocalDate;

import com.example.granary_backend.domain.model.Order;
import com.example.granary_backend.domain.model.OrderId;

public interface OrderRepository {
  Optional<Order> findById(OrderId id);
  List<Order> findAll();
  List<Order> findByStatus(Order.OrderStatus status);
  List<Order> findByDateRange(LocalDate startDate, LocalDate endDate);
  void save(Order order);
  void delete(OrderId id);
}

