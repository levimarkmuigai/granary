<script lang="ts">
  import type { Product } from "$lib/domain";
  import { addToCart, cartTotalStore } from "$lib/application/CartStore";
  import { onMount } from "svelte";

  interface PageData {
    products: Product[];
    error?: boolean;
  }

  export let data: PageData;

  // Safe fallback
  const products: Product[] = data.products ?? [];

  // Unwrap store for number
  $: cartTotal = $cartTotalStore;

  function formatCurrency(cents: number): string {
    return (cents / 100).toLocaleString("en-KE", {
      style: "currency",
      currency: "KSH",
    });
  }

  function handleAddToCart(product: Product) {
    if (product.stockQuantity > 0) {
      addToCart(product, 1);
    }
  }
</script>

<svelte:head>
  <title>Granary Storefront</title>
  <meta
    name="description"
    content="Browse our handcrafted granola collection. Freshly made in Nairobi with no preservatives."
  />
</svelte:head>

<div class="max-w-7xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
  <div
    class="flex flex-col sm:flex-row justify-between items-start sm:items-center mb-10 gap-4"
  >
    <h1 class="text-4xl font-extrabold text-gray-900">Our Featured Products</h1>
    <div class="text-xl font-semibold text-gray-700">
      Cart Total: <span class="text-blue-600">{formatCurrency(cartTotal)}</span>
    </div>
  </div>

  {#if data.error}
    <p class="text-center text-red-500 mb-6">
      ⚠️ Could not load products. Please try again later.
    </p>
  {/if}

  {#if products.length > 0}
    <div
      class="grid grid-cols-1 gap-y-10 gap-x-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 xl:gap-x-8"
    >
      {#each products as product (product.id)}
        <div
          class="group relative bg-white border border-gray-200 rounded-xl shadow-lg hover:shadow-2xl transition duration-300 overflow-hidden flex flex-col"
        >
          <div
            class="w-full min-h-60 bg-gray-200 aspect-w-1 aspect-h-1 overflow-hidden lg:h-60"
          >
            <img
              src={product.imageUrl}
              alt={product.name}
              class="w-full h-full object-cover object-center group-hover:opacity-75 transition duration-300"
              loading="lazy"
            />
          </div>

          <div class="p-4 flex-1 flex flex-col">
            <h3 class="mt-1 text-lg font-semibold text-gray-900">
              <a
                href={`/products/${product.id}`}
                class="hover:text-blue-600 transition duration-150"
              >
                <span aria-hidden="true" class="absolute inset-0"></span>
                {product.name}
              </a>
            </h3>
            <p class="mt-1 text-sm text-gray-500">{product.size}</p>
            <p class="mt-3 text-2xl font-bold text-blue-600">
              {formatCurrency(product.priceCents)}
            </p>

            <div
              class="mt-4 flex justify-between items-center text-sm text-gray-600"
            >
              <span class="font-medium">Stock: {product.stockQuantity}</span>
              {#if product.stockQuantity <= product.lowStockAlert && product.stockQuantity > 0}
                <span class="text-red-500 font-semibold">Low Stock!</span>
              {/if}
            </div>
          </div>

          <button
            class="mt-auto w-full bg-blue-500 text-white py-3 font-semibold hover:bg-blue-600 transition duration-150 rounded-b-xl disabled:opacity-50 disabled:cursor-not-allowed"
            on:click={() => handleAddToCart(product)}
            disabled={product.stockQuantity === 0}
            aria-disabled={product.stockQuantity === 0}
          >
            {product.stockQuantity === 0 ? "Out of Stock" : "Add to Cart"}
          </button>
        </div>
      {/each}
    </div>
  {:else}
    <p class="text-gray-500 text-lg text-center mt-8">
      No products are currently available.
    </p>
  {/if}
</div>
