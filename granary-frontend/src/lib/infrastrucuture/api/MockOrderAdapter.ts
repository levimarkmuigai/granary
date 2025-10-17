// src/lib/infrastructure/api/MockOrderAdapter.ts

import type { OrderServicePort } from '$lib/application/OrderServicePort';
import type { Order, CreateOrderCommand, OrderId, OrderLine, CustomerDetails } from '$lib/domain';
import { OrderCreationStore } from '$lib/application/StateStore';
import { get } from 'svelte/store';

// Utility to simulate network delay
const sleep = (ms: number) => new Promise(resolve => setTimeout(resolve, ms));

/**
 * Concrete Adapter for development and testing. It simulates API calls.
 */
export class MockOrderAdapter implements OrderServicePort {
    private readonly DELAY_MS = 1000;

    /**
     * Implements the createOrder method, simulating a successful API call.
     */
    async createOrder(command: CreateOrderCommand): Promise<OrderId> {
        OrderCreationStore.update(state => ({ 
            ...state, 
            status: 'loading', 
            errorMessage: null 
        }));

        await sleep(this.DELAY_MS); 

        try {
            // Simulate a simple validation error (checking the nested customerDetails)
            if (!command.customerDetails.email.includes('@')) {
                const message = "Mock validation failure: Email is invalid.";
                OrderCreationStore.set({
                    status: 'error',
                    orderId: null,
                    errorMessage: message,
                });
                throw new Error(message);
            }

            const mockOrderId: OrderId = "MOCK-ORD-0012345";

            OrderCreationStore.set({
                status: 'success',
                orderId: mockOrderId,
                errorMessage: null,
            });

            return mockOrderId;

        } catch (error) {
            console.error('Mock Error during order creation:', error);
            if (get(OrderCreationStore).status !== 'error') {
                 OrderCreationStore.set({
                    status: 'error',
                    orderId: null,
                    errorMessage: 'Network error or unhandled exception.',
                });
            }
            throw error;
        }
    }

    /**
     * Implements the fetchOrderStatus method, returning a dummy Order object.
     */
    async fetchOrderStatus(orderId: OrderId): Promise<Order> {
        await sleep(this.DELAY_MS);
        
        // --- MOCK DATA GENERATION ---
        
        const mockCustomer: CustomerDetails = {
            name: "L. Voyager",
            phone: "0712345678",
            email: "levi.v@granary.com",
            address: "123 Svelte Lane, Nairobi",
        };

        const mockLines: OrderLine[] = [
            {
                productId: 'P-201',
                productName: 'Premium Granary Flour',
                unitPriceCents: 1500,
                quantityOrdered: 2,
                lineTotalCents: 3000,
            },
            {
                productId: 'P-404',
                productName: 'Local Honey Jar',
                unitPriceCents: 990,
                quantityOrdered: 1,
                lineTotalCents: 990,
            },
        ];
        
        const totalCents = mockLines.reduce((sum, line) => sum + line.lineTotalCents, 0);

        
        return {
            id: orderId,
            orderLines: mockLines,
            customerDetails: mockCustomer,
            
            totalAmountCents: totalCents,
            
            deliveryStatus: 'PENDING',
            paymentStatus: 'SUCCESS', 
            orderStatus: 'PACKAGING',
            
            mpesaTransactionId: 'MPESA-XYZ789',
            mpesaCheckoutRequestId: 'ws_CO_123456',
            
            createdAt: new Date().toISOString(),
            updatedAt: new Date().toISOString(),
        };
    }
}