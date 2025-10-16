package com.example.granary_backend.application.dto.order;

import java.util.List;

public record CreateOrderRequestDTO(

        List<OrderLineRequestDTO> orderLines,
        CustomerDetailsRequestDTO customerDetails) {
}
