import type { OrderServicePort } from "$lib/application/OrderServicePort";
import { OrderCreationStore } from "$lib/application/StateStore";
import type { CreateOrderCommand, Order, OrderId } from "$lib/domain";
import { PUBLIC_BACKEND_URL } from '$env/static/public';

import { get } from "svelte/store";

export class HttpOrderAdapter implements OrderServicePort {

    private readonly API_BASE = PUBLIC_BACKEND_URL || 'http://localhost:8080/api';

    async createOrder(command: CreateOrderCommand): Promise<OrderId> {
        OrderCreationStore.update(state => ({
            ...state,
            status: 'loading',
            errorMessage: null
        }));

        try {
            const url = `${this.API_BASE}/orders`;

            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(command)
            });

            if (!response.ok) {
                const errorBody = await response.json();
                const message = errorBody.message || `Server returned error status ${response.status}`;

                OrderCreationStore.set({
                    status: 'error',
                    orderId: null,
                    errorMessage: message
                });

                throw new Error(message);
            }

            const orderResponse =await response.json();
            const newOrderId: OrderId = orderResponse.orderId;

            OrderCreationStore.set({
                status: 'success',
                orderId: newOrderId,
                errorMessage: null,
            });

            return newOrderId
        } catch(error) {
            console.error('API Error during order creation', error);

            if(((get(OrderCreationStore).status as string)) !== 'error'){
                OrderCreationStore.set({
                    status: 'error',
                    orderId: null,
                    errorMessage: 'Network error or unhandled exception.'
                })
            }

            throw error
        }
    }

    async fetchOrderStatus(orderId: OrderId): Promise<Order> {
        // TODO
        throw new Error('fetchOrdreStatus not yet implemeted')
    }
}