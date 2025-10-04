package com.example.granary_backend.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.example.granary_backend.domain.model.Product;
import com.example.granary_backend.domain.model.value.ProductId;
import com.example.granary_backend.domain.port.ProductRepository;
import com.example.granary_backend.infrastructure.persistence.repository.ProductJpaRepository;

@Component
public class ProductRepositoryAdapter implements ProductRepository {

  private final ProductJpaRepository jpaRepository;
  private final ProductMapper mapper;

  public ProductRepositoryAdapter(ProductJpaRepository jpaRepository,
      ProductMapper mapper) {

    this.jpaRepository = jpaRepository;
    this.mapper = mapper;
  }

  @Override
  public Optional<Product> findById(ProductId id) {
    return jpaRepository.findById(id.getValue())
        .map(mapper::toDomain);
  }

  @Override
  public List<Product> findAll() {
    return jpaRepository
        .findAll()
        .stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public void save(Product product) {
    var entity = mapper.toEntity(product);
    jpaRepository.save(entity);
  }

  @Override
  public void delete(ProductId id) {
    jpaRepository.deleteById(id.getValue());
  }

  public List<Product> findAllById(Set<ProductId> ids) {
    var rawIds = ids
        .stream()
        .map(ProductId::getValue)
        .toList();

    return jpaRepository
        .findAllById(rawIds)
        .stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public List<Product> findActiveProducts() {
    return jpaRepository
        .findByIsActiveTrue()
        .stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public List<Product> findLowStockProducts(int threshold) {
    return jpaRepository
        .findByStockQuantityLessThan(threshold)
        .stream()
        .map(mapper::toDomain)
        .toList();
  }

  @Override
  public List<Product> searchByName(String name) {

    return jpaRepository
        .findByNameContainingIgnoreCase(name)
        .stream()
        .map(mapper::toDomain)
        .toList();
  }
}
