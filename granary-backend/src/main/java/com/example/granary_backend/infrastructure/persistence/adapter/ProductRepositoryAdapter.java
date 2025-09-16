package com.example.granary_backend.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;

import javax.print.DocFlavor.STRING;

import org.springframework.stereotype.Component;

import com.example.granary_backend.domain.model.Product;
import com.example.granary_backend.domain.model.ProductId;
import com.example.granary_backend.domain.port.ProductRepository;
import com.example.granary_backend.infrastructure.persistence.adapter.ProductMapper;
import com.example.granary_backend.infrastructure.persistence.entity.ProductEntity;
import com.example.granary_backend.infrastructure.persistence.repository.ProductJpaRepository;

@Component
public class ProductRepositoryAdapter  implements ProductRepository{

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

  public List<Product> findActiveProducts() {
    return jpaRepository
    .findAll()
    .stream()
    .filter(ProductEntity::isActive)
    .map(mapper::toDomain)
    .toList();
  }

  public List<Product> findLowStockProducts(int threshold) {
    return jpaRepository
    .findAll()
    .stream()
    .filter(entity -> entity.getStockQuantity() < threshold)
    .map(mapper::toDomain)
    .toList();
  }

  public List<Product> searchByName(String name) {
    String searchTerm = name.toLowerCase();
    return jpaRepository
    .findAll()
    .stream()
    .filter(entity -> {
      String productName = entity.getName();
      return productName != null && productName.toLowerCase().contains(searchTerm);
    })
    .map(mapper::toDomain)
    .toList();
  }
}
