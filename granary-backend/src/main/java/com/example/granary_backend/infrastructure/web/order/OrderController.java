package com.example.granary_backend.infrastructure.web.order;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.granary_backend.application.command.order.CreateOrderCommand;
import com.example.granary_backend.application.service.OrderService;
import com.example.granary_backend.domain.model.value.OrderId;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {

        CreateOrderCommand command = mapToCreateOrderCommand(request);

        OrderId newOrderId = orderService.createOrder(command);

        OrderResponse response = new OrderResponse(
                newOrderId.getValue().toString(),
                request.deliveryMethod().equals("DELIVERY") ? 0 : 500,
                request.customerDetails().name(),
                "CREATED",
                true);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private CreateOrderCommand mapToCreateOrderCommand(CreateOrderRequest request) {

        List<CreateOrderCommand.OrderLineCommand> lineCommands = request.items().stream()
                .map(item -> new CreateOrderCommand.OrderLineCommand(
                        item.productId(),
                        item.quantity()))
                .toList();

        CreateOrderCommand.CustomerDetailsCommand customerDetailsCommand = new CreateOrderCommand.CustomerDetailsCommand(
                request.customerDetails().name(),
                request.customerDetails().email(),
                request.customerDetails().phone(),
                request.customerDetails().address());

        return new CreateOrderCommand(
                lineCommands,
                customerDetailsCommand,
                request.deliveryMethod());
    }
}