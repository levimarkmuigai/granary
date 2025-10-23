package com.example.granary_backend.infrastructure.web.admin.service;

import com.example.granary_backend.application.exception.EntityNotFoundException;
import com.example.granary_backend.application.service.FileStorageService;
import com.example.granary_backend.infrastructure.persistence.entity.ProductEntity;
import com.example.granary_backend.infrastructure.persistence.repository.ProductJpaRepository;
import com.example.granary_backend.infrastructure.web.admin.dto.ProductDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ProductAdminService {

    private final ProductJpaRepository productRepository;
    private final FileStorageService fileStorageService;

    public ProductAdminService(ProductJpaRepository productRepository, FileStorageService fileStorageService) {
        this.productRepository = productRepository;
        this.fileStorageService = fileStorageService;
    }

    private ProductDTO toDTO(ProductEntity entity) {
        ProductDTO dto = new ProductDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setPriceCents(entity.getPriceCents());
        dto.setStockQuantity(entity.getStockQuantity());
        dto.setLowStockAlert(entity.getLowStockAlert());
        dto.setImageUrl(entity.getImageUrl());
        return dto;
    }

    private void mapDtoToEntity(ProductDTO dto, ProductEntity entity) {
        if (dto.getName() != null)
            entity.setName(dto.getName());
        if (dto.getPriceCents() != null)
            entity.setPriceCents(dto.getPriceCents());
        if (dto.getStockQuantity() != null)
            entity.setStockQuantity(dto.getStockQuantity());
        if (dto.getLowStockAlert() != null)
            entity.setLowStockAlert(dto.getLowStockAlert());

    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findByIsActiveTrue().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO, MultipartFile imageFile) {
        ProductEntity entity;
        boolean isNewProduct = productDTO.getId() == null;

        if (!isNewProduct) {
            entity = productRepository.findById(productDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productDTO.getId()));

        } else {
            entity = new ProductEntity(UUID.randomUUID());
            entity.setActive(true);
        }

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                if (!isNewProduct && entity.getImageUrl() != null) {
                    fileStorageService.deleteFile(entity.getImageUrl());
                }

                String newImageUrl = fileStorageService.uploadFile(imageFile);
                entity.setImageUrl(newImageUrl);

            } catch (IOException e) {
                throw new IllegalStateException("Failed to process product image: " + e.getMessage());
            }
        }

        mapDtoToEntity(productDTO, entity);

        ProductEntity savedEntity = productRepository.save(entity);
        return toDTO(savedEntity);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        ProductEntity entity = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));

        if (entity.getImageUrl() != null) {
            boolean deleted = fileStorageService.deleteFile(entity.getImageUrl());
            if (!deleted) {
                System.err.println("Warning: Could not delete old file for product ID: " + productId);
            }
        }

        productRepository.delete(entity);
    }
}