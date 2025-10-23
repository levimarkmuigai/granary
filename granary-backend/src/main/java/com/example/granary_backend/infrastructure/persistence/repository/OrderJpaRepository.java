package com.example.granary_backend.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.granary_backend.domain.model.Order;
import com.example.granary_backend.infrastructure.persistence.entity.OrderEntity;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {

  List<OrderEntity> findByOrderStatus(Order.OrderStatus status);

  List<OrderEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

  long countByOrderStatus(Order.OrderStatus status);
}
