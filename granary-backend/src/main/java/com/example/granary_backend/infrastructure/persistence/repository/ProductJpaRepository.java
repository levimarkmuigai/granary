package com.example.granary_backend.infrastructure.persistence.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.granary_backend.infrastructure.persistence.entity.ProductEntity;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {

    List<ProductEntity> findByIsActiveTrue();

    List<ProductEntity> findByStockQuantityLessThan(int threshold);

    List<ProductEntity> findByNameContainingIgnoreCase(String name);
}
