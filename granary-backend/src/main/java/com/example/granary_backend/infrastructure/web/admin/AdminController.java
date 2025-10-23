package com.example.granary_backend.infrastructure.web.admin;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.granary_backend.infrastructure.web.admin.dto.DashboardSummaryDTO;
import com.example.granary_backend.infrastructure.web.admin.dto.OrderDTO;
import com.example.granary_backend.infrastructure.web.admin.dto.OrderStatusUpdateDTO;
import com.example.granary_backend.infrastructure.web.admin.dto.ProductDTO;
import com.example.granary_backend.infrastructure.web.admin.service.OrderAdminService;
import com.example.granary_backend.infrastructure.web.admin.service.ProductAdminService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final ProductAdminService productAdminService;
    private final OrderAdminService orderAdminService;

    public AdminController(ProductAdminService productAdminService, OrderAdminService orderAdminService) {
        this.productAdminService = productAdminService;
        this.orderAdminService = orderAdminService;
    }

    @GetMapping("/dashboard-data")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DashboardSummaryDTO> getDashboardSummary() {
        DashboardSummaryDTO summary = orderAdminService.getDashboardSummary();
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productAdminService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductDTO> saveProduct(
            @RequestPart("product") @Valid ProductDTO productDTO,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

        ProductDTO savedProduct = productAdminService.saveProduct(productDTO, imageFile);

        return ResponseEntity.status(
                productDTO.getId() == null ? HttpStatus.CREATED : HttpStatus.OK).body(savedProduct);
    }

    @DeleteMapping("/products/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable UUID productId) {
        productAdminService.deleteProduct(productId);
    }

    @GetMapping("/orders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderAdminService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @PatchMapping("/orders/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable UUID orderId,
            @RequestBody @Valid OrderStatusUpdateDTO statusUpdateDTO) {

        OrderDTO updatedOrder = orderAdminService.updateOrderStatus(orderId, statusUpdateDTO.getNewStatus());
        return ResponseEntity.ok(updatedOrder);
    }
}