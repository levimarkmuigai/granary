package com.example.granary_backend.infrastructure.persistence.adapter;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.example.granary_backend.domain.model.Product;
import com.example.granary_backend.domain.model.value.ProductId;
import com.example.granary_backend.infrastructure.persistence.entity.ProductEntity;

@Component
public class ProductMapper {

  public Product toDomain(ProductEntity entity) {
    Objects.requireNonNull(entity, "ProductEntity must not be null");

    var productId = ProductId.from(entity.getId());

    return Product.reconstitute(
        productId,
        entity.getName(),
        entity.getSize(),
        entity.getPriceCents(),
        entity.getStockQuantity(),
        entity.getLowStockAlert(),
        entity.getImageUrl(),
        entity.isActive(),
        entity.getCreatedAt(),
        entity.getUpdatedAt());
  }

  public ProductEntity toEntity(Product domain) {
    Objects.requireNonNull(domain, "Product must not be null");

    return new ProductEntity(
        domain.getProductId().getValue(),
        domain.getName(),
        domain.getSize(),
        domain.getPriceCents(),
        domain.getStockQuantity(),
        domain.getImageUrl(),
        domain.getLowStockAlert(),
        domain.isActive(),
        domain.getCreatedAt(),
        domain.getUpdatedAt());
  }
}
