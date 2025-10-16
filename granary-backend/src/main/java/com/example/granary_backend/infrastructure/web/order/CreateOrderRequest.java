package com.example.granary_backend.infrastructure.web.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record CreateOrderRequest(

                @NotEmpty(message = "Order must contain at least one item.") @Valid List<OrderItem> items,

                @NotNull @Valid CustomerDetail customerDetails) {
        public record OrderItem(
                        @NotBlank String productId,
                        @Min(1) int quantity) {
        }

        public record CustomerDetail(
                        @NotBlank String name,
                        @Email String email,
                        @NotBlank String phone,
                        @NotBlank String address) {
        }
}
