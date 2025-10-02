package com.example.granary_backend.application.dto.order;

import java.util.List;

import com.example.granary_backend.domain.model.Order.DeliveryMethod;

public record CreateOrderRequestDTO(

    List<OrderLineRequestDTO> orderLines,
    CustomerDetailsRequestDTO customerDetails,
    DeliveryMethod deliveryMethod) {
}
