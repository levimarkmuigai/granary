import type { EntityId, Product } from "$lib/domain";

export interface ProductServicePort {
   
    fetchAllProducts(): Promise<Product[]>;
    fetchProductById(productId: EntityId): Promise<Product>;

    
    createProduct(product: Product): Promise<Product>;  
    updateProduct(productId: EntityId, updates: Partial<Product>): Promise<Product>; 
    deleteProduct(productId: EntityId): Promise<void>; 
}
