package com.example.granary_backend.infrastructure.web.payment;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.granary_backend.application.command.payment.InitiatePaymentCommand;
import com.example.granary_backend.application.service.CheckoutService;
import com.example.granary_backend.domain.port.PaymentGateway;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final CheckoutService checkoutService;

    public PaymentController(PaymentGateway paymentGateway,
            CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping("/stk-push")
    @Transactional(readOnly = true)
    public ResponseEntity<String> initiateStkPush(
            @Valid @RequestBody PaymentRequest request) {
        InitiatePaymentCommand command = new InitiatePaymentCommand(
                request.orderId());

        String checkoutRequestId = checkoutService.initiatePayment(command);

        String message = String.format(
                "STK Push initiated. Check your phone. Tracking ID: %s",
                checkoutRequestId);

        return ResponseEntity.accepted().body(message);
    }
}
