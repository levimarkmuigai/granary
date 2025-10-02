package com.example.granary_backend.application.dto.order;

public record CustomerDetailsRequestDTO(
        String name,
        String email,
        String phone,
        String address) {
}
