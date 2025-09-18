package com.example.granary_backend.application.dto.order;

import java.util.List;

public record CreateOrderRequestDTO (

  List<OrderLineRequestDTO> orderLines,
  String customerName,
  String customerEmail,
  String customerPhone,
  String customerAddress,
  String deliveryMethod
) {}
