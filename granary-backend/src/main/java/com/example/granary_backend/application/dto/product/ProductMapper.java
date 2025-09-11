package com.example.granary_backend.application.dto.product;

import java.util.Objects;
import java.util.Optional;

import com.example.granary_backend.application.command.product.CreateProductCommand;
import com.example.granary_backend.application.command.product.UpdateProductCommand;
import com.example.granary_backend.application.dto.product.CreateProductRequestDTO;
import com.example.granary_backend.domain.model.Product;

public class ProductMapper {

  public CreateProductCommand toCreateCommand(CreateProductRequestDTO dto) {
    Objects.requireNonNull(dto, "CreateProductRequestDTO must not be null");

    var id = dto.productId();
    var name = dto.name();
    var size = dto.size();
    var priceCents = dto.priceCents();
    var lowStockAlert = dto.lowStockAlert();
    var imageUrl = dto.imageUrl();
    var stockQuantity = dto.stockQuantity();

    return new CreateProductCommand(
      id,
      name,
      size,
      priceCents,
      stockQuantity,
      lowStockAlert,
      imageUrl
    );
  }

  public UpdateProductCommand toUpdateCommand(UpdateProductRequestDTO dto){
    Objects.requireNonNull(dto, "UpdateProductRequestDTO must not be null");

    var productId = dto.productId();

    if(productId.isBlank()){
      throw new IllegalArgumentException("ProductId must not be blank");
    }

    Optional<String> name = Optional.ofNullable(dto.name());
    Optional<Integer> priceCents = Optional.ofNullable(dto.priceCents());
    Optional<Integer> stockQuantity = Optional.ofNullable(dto.stockQuantity());
    Optional<Boolean> active = Optional.ofNullable(dto.active());
    Optional<String> size = Optional.ofNullable(dto.size());
    Optional<Integer> lowStockAlert = Optional.ofNullable(dto.lowStockAlert());
    Optional<String> imageUrl = Optional.ofNullable(dto.imageUrl());

    return new UpdateProductCommand(
      productId,
      name,
      size,
      lowStockAlert,
      imageUrl,
      priceCents,
      stockQuantity,
      active);
  }

  public ProductResponseDTO toResponseDTO(Product product){
    Objects.requireNonNull(product, "Product must not be null");

    var id = product.getId().toString();

    return new ProductResponseDTO(
      id,
      product.getName(),
      product.getSize(),
      product.getPriceCents(),
      product.getStockQuantity(),
      product.getLowStockAlert(),
      product.getImageUrl(),
      product.isActive(),
      product.getCreatedAt(),
      product.getUpdatedAt()
    );

  }
}
