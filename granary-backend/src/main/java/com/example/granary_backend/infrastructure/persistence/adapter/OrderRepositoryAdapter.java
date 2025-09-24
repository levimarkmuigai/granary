package com.example.granary_backend.infrastructure.persistence.adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.granary_backend.domain.model.Order;
import com.example.granary_backend.domain.model.OrderId;
import com.example.granary_backend.domain.port.OrderRepository;
import com.example.granary_backend.infrastructure.persistence.entity.OrderEntity;
import com.example.granary_backend.infrastructure.persistence.entity.ProductEntity;
import com.example.granary_backend.infrastructure.persistence.repository.OrderJpaRepository;

import jakarta.persistence.EntityManager;

@Component
public class OrderRepositoryAdapter implements OrderRepository{

  private final OrderJpaRepository jpaRepository;
  private final OrderMapper mapper;
  private final EntityManager em;

  public OrderRepositoryAdapter(OrderJpaRepository jpaRepository,
    OrderMapper mapper, EntityManager em) {

    this.jpaRepository = jpaRepository;
    this.mapper = mapper;
    this.em = em;
  }

  @Override
  public Optional<Order> findById(OrderId id) {
    Objects.requireNonNull(id, "OrderId must not be null");

    return jpaRepository.findById(id.getValue())
      .map(mapper::toDomain);
  }

  @Override
  public List<Order> findAll() {
    List<OrderEntity> entities = jpaRepository.findAll();

    return entities
    .stream()
    .map(mapper::toDomain)
    .toList();

  }

  @Override
  public List<Order> findByStatus(Order.OrderStatus status) {
    Objects.requireNonNull(status, "Order status must not be null");

    return jpaRepository
      .findByOrderStatus(status)
      .stream()
      .map(mapper::toDomain)
      .toList();
  }

  @Override
  public List<Order> findByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
    Objects.requireNonNull(startDate, "Start date must not be null");
    Objects.requireNonNull(endDate, "End date must not be null");

    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Start date cannot be after end date");
    }

    return jpaRepository
      .findByCreatedAtBetween(startDate, endDate)
      .stream()
      .map(mapper::toDomain)
      .toList();
  }

  @Override
  public Order save(Order order) {
    Objects.requireNonNull(order, "Order must not be null");

    List<ProductEntity> productEntities = order.getOrderLines()
      .stream()
      .map(orderLine -> em.getReference(ProductEntity.class, orderLine.getProductId().getValue()))
      .toList();

    OrderEntity entity = mapper.toEntity(order, productEntities);

    OrderEntity savedEntity = jpaRepository.save(entity);

    return mapper.toDomain(savedEntity);
  }

  @Override
  public boolean delete(OrderId id) {
    Objects.requireNonNull(id, "OrderId must not be null");

    if (jpaRepository.existsById(id.getValue())) {
      jpaRepository.deleteById(id.getValue());
      return true;
    }
    return false;
  }

}
