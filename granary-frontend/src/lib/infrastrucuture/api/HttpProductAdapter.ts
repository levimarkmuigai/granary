import type { ProductServicePort } from '$lib/application/ProductServicePort';
import type { Product, EntityId } from '$lib/domain';
import { PUBLIC_BACKEND_URL } from '$env/static/public';

export class HttpProductAdapter implements ProductServicePort {
    private readonly API_BASE = PUBLIC_BACKEND_URL || 'http://localhost:8080/api/v1';

    async fetchAllProducts(): Promise<Product[]> {
        const url = `${this.API_BASE}/products`;
        
        const response = await fetch(url);

        if (!response.ok) {
            console.error(`Failed to fetch products. Status: ${response.status}`);
            throw new Error('Could not fetch product catalog from API.');
        }

        const products = await response.json() as Product[];
        return products;
    }

    async fetchProductById(productId: EntityId): Promise<Product> {
        const url = `${this.API_BASE}/products/${productId}`;

        const response = await fetch(url);
        
        if (!response.ok) {
            if (response.status === 404) {
                throw new Error(`Product not found with ID: ${productId}`);
            }
            throw new Error('Failed to fetch product details from API.');
        }

        const product = await response.json() as Product;
        return product;
    }

    async createProduct(product: Product): Promise<Product> {
        const url = `${this.API_BASE}/products`;
        
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(product)
        });
        
        if (!response.ok) {
            const errorBody = await response.json();
            throw new Error(errorBody.message || `Failed to create product. Status: ${response.status}`);
        }
        
        return await response.json() as Product;
    }

    async updateProduct(productId: EntityId, updates: Partial<Product>): Promise<Product> {
        const url = `${this.API_BASE}/products/${productId}`;
        
        const response = await fetch(url, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updates)
        });
        
        if (!response.ok) {
            const errorBody = await response.json();
            throw new Error(errorBody.message || `Failed to update product. Status: ${response.status}`);
        }
        
        return await response.json() as Product;
    }

    async deleteProduct(productId: EntityId): Promise<void> {
        const url = `${this.API_BASE}/products/${productId}`;
        
        const response = await fetch(url, {
            method: 'DELETE'
        });
        
        if (!response.ok) {
            throw new Error(`Failed to delete product. Status: ${response.status}`);
        }
    }
}