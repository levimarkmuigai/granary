import type { Order, CreateOrderCommand, OrderId, OrderStatus } from "$lib/domain";

export interface OrderServicePort {
   
    createOrder(command: CreateOrderCommand): Promise<OrderId>;
    fetchOrderStatus(orderId: OrderId): Promise<Order>;

    fetchAllOrders(): Promise<Order[]>;               
    updateOrderStatus(orderId: OrderId, status: OrderStatus): Promise<Order>; 
}
