package com.example.granary_backend.infrastructure.web.admin.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.example.granary_backend.infrastructure.persistence.entity.OrderEntity;
import com.example.granary_backend.infrastructure.persistence.entity.OrderLineEntity;
import com.example.granary_backend.infrastructure.web.admin.dto.OrderDTO;
import com.example.granary_backend.infrastructure.web.admin.dto.OrderItemDTO;

import org.springframework.stereotype.Component;

@Component
public class OrderAdminMapper {

    private static final BigDecimal CENTS_TO_UNIT_FACTOR = new BigDecimal("100");

    public OrderDTO toDTO(OrderEntity entity) {
        if (entity == null) {
            return null;
        }

        OrderDTO dto = new OrderDTO();
        dto.setId(entity.getId());
        dto.setCustomerName(entity.getCustomerName());
        dto.setOrderDate(entity.getCreatedAt());
        dto.setStatus(entity.getOrderStatus().name());

        BigDecimal totalAmount = entity.getOrderLines().stream()
                .map(OrderLineEntity::getLineTotalCents)
                .map(cents -> new BigDecimal(cents).divide(CENTS_TO_UNIT_FACTOR))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setTotalAmount(totalAmount);

        dto.setItems(
                entity.getOrderLines().stream()
                        .map(this::toOrderItemDTO)
                        .collect(Collectors.toList()));

        return dto;
    }

    public List<OrderDTO> toDTOList(List<OrderEntity> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private OrderItemDTO toOrderItemDTO(OrderLineEntity lineEntity) {
        OrderItemDTO itemDTO = new OrderItemDTO();
        itemDTO.setProductName(lineEntity.getProductName());
        itemDTO.setQuantity(lineEntity.getQuantityOrdered());

        BigDecimal unitPrice = new BigDecimal(lineEntity.getUnitPriceCents())
                .divide(CENTS_TO_UNIT_FACTOR);
        itemDTO.setPricePerUnit(unitPrice);

        return itemDTO;
    }
}