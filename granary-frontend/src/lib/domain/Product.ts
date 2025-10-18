export type EntityId = string;

export interface Product {
    
    id: EntityId; 
    
    
    name: string;
    
    
    size: string;
    
   
    priceCents: number;
    
   
    stockQuantity: number;
    
    
    lowStockAlert: number; 
    
    imageUrl: string | null; 
    active: boolean; 
    
    
    createdAt: string; 
    updatedAt: string;
}