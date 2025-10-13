package com.example.granary_backend.infrastructure.web.payment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PaymentRequest(
        @NotBlank(message = "Order ID is required.") String orderId,

        @Min(value = 1, message = "Amount must be at least 1 KES.") int amount,

        @NotBlank(message = "Phone number is required.") @Pattern(regexp = "^2547\\d{8}$", message = "Phone number must be in format 2547xxxxxxxxx.") String phoneNumber) {

}
