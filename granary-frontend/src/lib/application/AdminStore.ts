import { writable } from "svelte/store";
import type { Order, OrderStatus, Product } from "$lib/domain";

interface AdminState {
  orders: Order[];
  products: Product[];
  isLoading: boolean;
  errorMessage: string;
}

function createAdminStore() {
  const { subscribe, update, set } = writable<AdminState>({
    orders: [],
    products: [],
    isLoading: false,
    errorMessage: "",
  });

  return {
    subscribe,

    // Orders
    setOrders: (orders: Order[]) =>
      update(state => ({ ...state, orders })),
    updateOrderStatus: (orderId: string, status: OrderStatus) =>
      update(state => ({
        ...state,
        orders: state.orders.map(o => (o.id === orderId ? { ...o, status } : o)),
      })),

    // Products
    setProducts: (products: Product[]) =>
      update(state => ({ ...state, products })),
    addProduct: (product: Product) =>
      update(state => ({ ...state, products: [...state.products, product] })),
    updateProduct: (updated: Product) =>
      update(state => ({
        ...state,
        products: state.products.map(p => (p.id === updated.id ? updated : p)),
      })),
    removeProduct: (id: string) =>
      update(state => ({
        ...state,
        products: state.products.filter(p => p.id !== id),
      })),

    // Global
    setLoading: (isLoading: boolean) =>
      update(state => ({ ...state, isLoading })),
    setError: (errorMessage: string) =>
      update(state => ({ ...state, errorMessage })),
    reset: () =>
      set({
        orders: [],
        products: [],
        isLoading: false,
        errorMessage: "",
      }),
  };
}

export const AdminStore = createAdminStore();
