export type EntityId =string;

export interface Product {
    id: EntityId;
    name: string;
    priceCents: number;
    stockQuantity: number;
    imageUrl?: string;
}