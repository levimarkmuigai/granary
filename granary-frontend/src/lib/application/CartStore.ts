import { writable, type Writable, get } from 'svelte/store'; 
import type { EntityId, Product } from '$lib/domain';



export interface CartItem {
    productId: EntityId;
    name: string;
    size: string;
    priceCents: number;
    quantity: number;
}


export type CartStore = CartItem[];

const initialCart: CartStore = [];

export const cartStore: Writable<CartStore> = writable(initialCart);


export function addToCart(product: Product, amount: number = 1): void {
    if (amount <= 0) return;

    cartStore.update((cart: CartStore) => { 
        const existingItem = cart.find(item => item.productId === product.id);

        if (existingItem) {
            
            return cart.map(item => 
                item.productId === product.id
                    ? { ...item, quantity: item.quantity + amount }
                    : item
            );
        } else {
            
            const newItem: CartItem = {
                productId: product.id,
                name: product.name,
                size: product.size,
                priceCents: product.priceCents,
                quantity: amount,
            };
            return [...cart, newItem]; 
        }
    });
}

export function removeFromCart(productId: EntityId): void {
    
    cartStore.update((cart: CartStore) => {
        const index = cart.findIndex(item => item.productId === productId);

        if (index > -1) {
            const item = cart[index];
            if (item.quantity > 1) {
               
                return cart.map(i => 
                    i.productId === productId
                        ? { ...i, quantity: i.quantity - 1 }
                        : i
                );
            } else {
                
                return cart.filter(i => i.productId !== productId);
            }
        }
        return cart; 
    });
}

export function clearCart(): void {
    cartStore.set(initialCart); 
}

export function calculateCartTotal(cart: CartStore): number {
    return cart.reduce((total, item) => total + (item.priceCents * item.quantity), 0);
}

import { derived } from 'svelte/store';

export const cartTotalStore = derived(
    cartStore,
    $cart => calculateCartTotal($cart)
);