package com.example.granary_backend.application.service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.example.granary_backend.application.command.order.AdvanceOrderCommand;
import com.example.granary_backend.application.command.order.CreateOrderCommand;
import com.example.granary_backend.application.service.base.BaseApplicationService;
import com.example.granary_backend.application.util.BatchFetcher;
import com.example.granary_backend.application.util.CommandUtils;
import com.example.granary_backend.application.util.DomainMapper;
import com.example.granary_backend.application.validation.CommandValidator;
import com.example.granary_backend.domain.model.Order;
import com.example.granary_backend.domain.model.Product;
import com.example.granary_backend.domain.model.value.OrderId;
import com.example.granary_backend.domain.model.value.ProductId;
import com.example.granary_backend.domain.port.OrderRepository;
import com.example.granary_backend.domain.port.ProductRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
public class OrderService extends BaseApplicationService {

        private final OrderRepository orderRepository;
        private final ProductRepository productRepository;

        private final CommandValidator<CreateOrderCommand> createOrderValidator;
        private final CommandValidator<AdvanceOrderCommand> advanceOrderValidator;

        public OrderService(
                        OrderRepository orderRepository,
                        ProductRepository productRepository,
                        CommandValidator<CreateOrderCommand> createOrderValidator,
                        CommandValidator<AdvanceOrderCommand> advanceOrderValidator) {

                super();

                this.orderRepository = Objects.requireNonNull(orderRepository, "OrderRepository cannot be null");
                this.productRepository = Objects.requireNonNull(productRepository, "ProductRepository cannot be null");
                this.createOrderValidator = Objects.requireNonNull(createOrderValidator,
                                "CreateOrderValidator cannot be null");
                this.advanceOrderValidator = Objects.requireNonNull(advanceOrderValidator,
                                "AdvanceOrderValidator cannot be null");
        }

        public OrderId createOrder(CreateOrderCommand command) {

                createOrderValidator.validate(command);

                Set<String> rawProductIds = CommandUtils.extractUniqueIds(
                                command.getOrderLines(),
                                CreateOrderCommand.OrderLineCommand::getProductId);

                List<ProductId> productIds = rawProductIds
                                .stream()
                                .map(ProductId::fromString)
                                .toList();

                Map<ProductId, Product> productMap = BatchFetcher.<ProductId, Product>fetch(
                                productIds,
                                productRepository::findAllById,
                                Product::getProductId);

                List<Order.OrderLine> orderLines = command.getOrderLines().stream().map(lineCmd -> {
                        ProductId productId = ProductId.fromString(lineCmd.getProductId());
                        Product product = productMap.get(productId);

                        if (product == null) {
                                throw new EntityNotFoundException("Product with ID " + productId + " not found.");
                        }

                        if (product.getStockQuantity() < lineCmd.getQuantity()) {
                                throw new IllegalStateException("Insufficient stock for product: " + product.getName());
                        }

                        return Order.OrderLine.create(
                                        product.getProductId(), product.getName(), product.getPriceCents(),
                                        lineCmd.getQuantity());
                }).toList();

                Order.CustomerDetails customerDetails = DomainMapper.mapCustomerDetails(
                                command.getCustomerDetails());

                Order order = Order.createFromOrderLines(
                                OrderId.createNew(),
                                orderLines,
                                customerDetails);

                orderRepository.save(order);

                return order.getId();

        }

        public OrderId advanceOrderStatus(AdvanceOrderCommand command) {
                advanceOrderValidator.validate(command);

                var orderId = OrderId.fromString(command.getOrderId());

                Order order = orderRepository.findById(orderId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Order with id: " + orderId + "not found."));

                order.advanceStatus();

                orderRepository.save(order);

                return order.getId();
        }
}
