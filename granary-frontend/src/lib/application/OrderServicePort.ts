import type { Order, CreateOrderCommand, OrderId } from "$lib/domain";

export interface OrderServicePort {
    createOrder(command: CreateOrderCommand): Promise<OrderId>;

    fetchOrderStatus(orderId: OrderId): Promise<Order>;
}