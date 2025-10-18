import type { EntityId, Product } from "$lib/domain";

export interface ProductServicePort {

    fetchAllProducts(): Promise<Product[]>;

    fetchProductById(productId: EntityId): Promise<Product>;
}