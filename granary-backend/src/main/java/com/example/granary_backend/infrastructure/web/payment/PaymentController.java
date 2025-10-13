package com.example.granary_backend.infrastructure.web.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.granary_backend.application.service.OrderService;
import com.example.granary_backend.domain.model.value.OrderId;
import com.example.granary_backend.domain.port.PaymentGateway;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentGateway paymentGateway;
    private final OrderService orderService;

    public PaymentController(PaymentGateway paymentGateway,
            OrderService orderService) {
        this.paymentGateway = paymentGateway;
        this.orderService = orderService;
    }

    @PostMapping("/stk-push")
    @Transactional(readOnly = true)
    public ResponseEntity<String> initiateStkPush(
            @Valid @RequestBody PaymentRequest request) {
        OrderId orderId = OrderId.fromString(request.orderId());

        orderService.verifyOrderForPayment(orderId, request.amount());

        String checkoutRequestId = paymentGateway.initiateStkPush(
                orderId,
                request.amount(),
                request.phoneNumber());

        String message = String.format(
                "STK Push initiated. Check phone %s. Tracking ID: %s",
                request.phoneNumber(),
                checkoutRequestId);

        return ResponseEntity.accepted().body(message);
    }
}
