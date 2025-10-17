import type { EntityId } from "./Product";

export type OrderId = EntityId;

export type DeliveryStatus = 'DELIVERED' | 'PENDING';
export type PaymentStatus = 'AWAITTING_INITIATION' | 'PENDING' | 'SUCCESS' | 'FAILED' | 'CANCELLED';
export type OrderStatus = 'NEW' | 'PACKAGING' | 'READY' | 'DELIVERED';

export interface OrderLineCommand {
    productId: EntityId;
    quantity: number;
}

export interface CustomerDetails{
    name: string;
    email: string;
    phone: string;
    address: string;
}

export interface OrderLineCommand {
    productId: EntityId;
    quantity: number;
}

export interface CreateOrderCommand {
    orderLines: OrderLineCommand[];
    customerDetails: CustomerDetails;
}

export interface OrderLine {
    productId: EntityId;
    productName: string; 
    unitPriceCents: number; 
    quantityOrdered: number; 
    lineTotalCents: number; 
}

export interface Order {
    id: OrderId; 
    orderLines: OrderLine[]; 
    customerDetails: CustomerDetails; 
    
    deliveryStatus: DeliveryStatus; 
    paymentStatus: PaymentStatus; 
    orderStatus: OrderStatus; 
    
    mpesaTransactionId: string | null; 
    mpesaCheckoutRequestId: string | null;
    
    totalAmountCents: number; 
    
    createdAt: string; 
    updatedAt: string;
}