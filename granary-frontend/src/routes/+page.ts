import { services } from '$lib/services';
import type { Load } from '@sveltejs/kit'; 
import type { Product } from '$lib/domain';

export const load: Load = async () => {
    try {
        const products: Product[] = await services.productService.fetchAllProducts();

        return {
            products: products, 
        };
    } catch (error) {
        console.error('Failed to load products:', error);
        return {
            products: [] as Product[],
            error: true,
        };
    }
};