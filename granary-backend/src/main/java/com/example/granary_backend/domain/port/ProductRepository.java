 package com.example.granary_backend.domain.port;

import java.util.List;
import java.util.Optional;

import com.example.granary_backend.domain.model.Product;
import com.example.granary_backend.domain.model.ProductId;

 public interface ProductRepository {
   Optional<Product> findById(ProductId id);
   List<Product> findAll();
   void save(Product product);
   void delete(ProductId id);
   List<Product> findActiveProducts();
   List<Product> findLowStockProducts(int threshold);
   List<Product> searchByName(String name);
 }
