package com.example.granary_backend.infrastructure.web.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.granary_backend.domain.model.Order.OrderStatus;

import com.example.granary_backend.infrastructure.persistence.entity.OrderEntity;
import com.example.granary_backend.infrastructure.persistence.repository.OrderJpaRepository;
import com.example.granary_backend.infrastructure.persistence.repository.ProductJpaRepository;
import com.example.granary_backend.infrastructure.web.admin.dto.DashboardSummaryDTO;
import com.example.granary_backend.infrastructure.web.admin.dto.OrderDTO;
import com.example.granary_backend.infrastructure.web.admin.mapper.OrderAdminMapper;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class OrderAdminService {

    private final OrderJpaRepository orderRepository;
    private final ProductJpaRepository productRepository;
    private final OrderAdminMapper orderMapper;

    public OrderAdminService(OrderJpaRepository orderRepository, ProductJpaRepository productRepository,
            OrderAdminMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    public DashboardSummaryDTO getDashboardSummary() {

        long newOrders = orderRepository.countByOrderStatus(OrderStatus.NEW);
        long packagingOrders = orderRepository.countByOrderStatus(OrderStatus.PACKAGING);
        long readyOrders = orderRepository.countByOrderStatus(OrderStatus.READY);
        long totalProducts = productRepository.count();

        return new DashboardSummaryDTO(
                newOrders,
                packagingOrders,
                readyOrders,
                totalProducts);
    }

    public List<OrderDTO> getAllOrders() {

        List<OrderEntity> orders = orderRepository.findAll();

        return orderMapper.toDTOList(orders);
    }

    @Transactional
    public OrderDTO updateOrderStatus(UUID orderId, String newStatusString) {

        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        OrderStatus newStatus;
        try {
            newStatus = OrderStatus.valueOf(newStatusString.toUpperCase());
        } catch (IllegalArgumentException e) {

            throw new IllegalArgumentException("Invalid status provided: " + newStatusString);
        }

        order.setOrderStatus(newStatus);
        OrderEntity updatedOrder = orderRepository.save(order);

        return orderMapper.toDTO(updatedOrder);
    }
}