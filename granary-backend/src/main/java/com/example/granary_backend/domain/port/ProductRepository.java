package com.example.granary_backend.domain.port;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.granary_backend.domain.model.Product;
import com.example.granary_backend.domain.model.value.ProductId;

public interface ProductRepository {
  Optional<Product> findById(ProductId id);

  List<Product> findAll();

  void save(Product product);

  void delete(ProductId id);

  List<Product> findActiveProducts();

  List<Product> findLowStockProducts(int threshold);

  List<Product> searchByName(String name);

  List<Product> findAllById(Set<ProductId> ids);
}
