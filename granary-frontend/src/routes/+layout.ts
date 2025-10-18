import type { Load } from '@sveltejs/kit';
import type { Product } from '$lib/domain/Product';
import { services } from '$lib/services';

export const load: Load = async () => {
  try {
    
    const products: Product[] = await services.productService.fetchAllProducts();

    const activeProducts = products.filter(p => p.active);

    const validatedProducts = activeProducts.map(p => ({
      id: p.id,
      name: p.name,
      size: p.size,
      priceCents: p.priceCents,
      stockQuantity: p.stockQuantity ?? 0,
      lowStockAlert: p.lowStockAlert ?? 10,
      imageUrl: p.imageUrl ?? '/placeholder-product.png', 
      active: p.active,
      createdAt: p.createdAt ?? new Date().toISOString(),
      updatedAt: p.updatedAt ?? new Date().toISOString(),
    }));

    return {
      products: validatedProducts,
    };
  } catch (error) {
    console.error('Failed to load products:', error);

    return {
      products: [] as Product[],
      error: true,
    };
  }
};
