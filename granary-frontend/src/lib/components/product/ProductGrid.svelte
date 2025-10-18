<script lang="ts">
  import ProductCard from "./ProductCard.svelte";
  import { productGridConfig } from "$lib/config/productGrid";

  export let products: Array<{
    id: string;
    name: string;
    size: string;
    priceCents: number;
    stockQuantity: number;
    lowStockAlert: number;
    imageUrl: string | null;
    active: boolean;
    createdAt: string;
    updatedAt: string;
  }>;

  // You can still override from parent if needed
  export let title: string = productGridConfig.title;
  export let subtitle: string = productGridConfig.subtitle;
</script>

<section class="py-8 sm:py-12">
  <div class="container-zing">
    <!-- Section Header -->
    {#if title || subtitle}
      <div class="text-center mb-8 sm:mb-12">
        {#if title}
          <h2 class="text-3xl sm:text-4xl font-bold text-black mb-3">
            {title}
          </h2>
        {/if}
        {#if subtitle}
          <p class="text-base sm:text-lg text-gray-600 max-w-2xl mx-auto">
            {subtitle}
          </p>
        {/if}
      </div>
    {/if}

    <!-- Product Grid -->
    {#if products.length > 0}
      <div
        class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 sm:gap-8"
      >
        {#each products as product (product.id)}
          <ProductCard {product} />
        {/each}
      </div>
    {:else}
      <!-- Empty State -->
      <div class="text-center py-16">
        <div class="text-6xl mb-4">{productGridConfig.emptyState.emoji}</div>
        <h3 class="text-xl font-semibold text-gray-900 mb-2">
          {productGridConfig.emptyState.title}
        </h3>
        <p class="text-gray-600">{productGridConfig.emptyState.message}</p>
      </div>
    {/if}
  </div>
</section>
