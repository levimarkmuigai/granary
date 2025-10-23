package com.example.granary_backend.infrastructure.web.admin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class OrderStatusUpdateDTO {

    @NotBlank(message = "New status is required")
    @Pattern(regexp = "Packaging|Ready|Delivered", message = "Invalid order status")
    private String newStatus;

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }
}
