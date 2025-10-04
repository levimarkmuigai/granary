package com.example.granary_backend.application.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.granary_backend.application.command.payment.InitiatePaymentCommand;
import com.example.granary_backend.application.service.base.BaseApplicationService;
import com.example.granary_backend.application.validation.CommandValidator;
import com.example.granary_backend.domain.model.Order;
import com.example.granary_backend.domain.model.Order.PaymentStatus;
import com.example.granary_backend.domain.model.value.OrderId;
import com.example.granary_backend.domain.port.OrderRepository;
import com.example.granary_backend.domain.port.PaymentGateway;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class CheckoutService extends BaseApplicationService {
    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;
    private final CommandValidator<InitiatePaymentCommand> initiatePaymentValidator;

    public CheckoutService(
            OrderRepository orderRepository,
            PaymentGateway paymentGateway,
            CommandValidator<InitiatePaymentCommand> initiatePaymentValidator) {
        super();

        this.orderRepository = Objects.requireNonNull(orderRepository, "OrderRepository must not be null");
        this.paymentGateway = Objects.requireNonNull(paymentGateway, "PaymentGateway must not be null");
        this.initiatePaymentValidator = Objects.requireNonNull(initiatePaymentValidator,
                "InitiatePaymentValidator must not be null");
    }

    public String initiatePayment(InitiatePaymentCommand command) {
        initiatePaymentValidator.validate(command);

        OrderId orderId = OrderId.fromString(command.getOrderId());
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        if (order.getPaymentStatus() != PaymentStatus.AWAITTING_INITIATION) {
            throw new IllegalStateException("Payment can only be initiated for orders in AWAITING_INITIATION status.");
        }

        String checkOutRequestId = paymentGateway.intiateStkPush(
                order.getId(),
                order.getTotalAmountCents(),
                order.getCustomerDetails().getPhone());

        order.markPaymentInitiated(checkOutRequestId);
        orderRepository.save(order);

        return checkOutRequestId;
    }

    public void handlePaymentFaliure(String orderIdString) {
        OrderId orderId = OrderId.fromString(orderIdString);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        order.failedPayment();

        // TODO: Post Failure orchestration (notify customer)

        orderRepository.save(order);
    }

    public void handlePaymentCancellation(String orderIdString) {
        OrderId orderId = OrderId.fromString(orderIdString);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderId));

        order.cancelPayment();

        orderRepository.save(order);
    }

}
