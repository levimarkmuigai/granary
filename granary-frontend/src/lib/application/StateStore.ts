import { writable, type Writable } from "svelte/store";
import type { Order } from "$lib/domain";

export interface OrderCreationStore {
    status: 'idle' | 'loading' | 'success' | 'error';
    orderId: string | null;
    errorMessage: String | null;
}

const initialOrderCreationState: OrderCreationStore = {
    status: 'idle',
    orderId: null,
    errorMessage: null,
}

export const OrderCreationStore: Writable<OrderCreationStore> = writable(initialOrderCreationState);


export function restOrderCreationStore() {
    OrderCreationStore.set(initialOrderCreationState);
}