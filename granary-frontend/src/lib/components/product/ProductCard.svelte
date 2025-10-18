<script lang="ts">
  /**
   * ProductCard Component
   * Location: src/lib/components/ProductCard.svelte
   *
   * Individual product display card with add-to-cart functionality.
   * Production-ready: typed props, accessibility, async-safe, and animation polish.
   */

  import { formatKES } from "$lib/utils/currency";
  import { addToCart } from "$lib/application/CartStore";
  import type { Product } from "$lib/domain";

  // === Props ===
  export let product: Product;

  // === Local state ===
  let isAdding = false;
  let justAdded = false;
  let errorMsg: string | null = null;

  // === Derived state ===
  $: inStock = product.active && product.stockQuantity > 0;
  $: lowStock = product.stockQuantity > 0 && product.stockQuantity <= 10;

  // === Actions ===
  async function handleAddToCart() {
    if (!inStock || isAdding) return;

    isAdding = true;
    errorMsg = null;

    try {
      await addToCart(product, 1); // async safe (for localStorage or backend sync)
      justAdded = true;
      setTimeout(() => (justAdded = false), 1200);
    } catch (err) {
      console.error("Add to cart failed:", err);
      errorMsg = "Something went wrong. Please try again.";
    } finally {
      isAdding = false;
    }
  }
</script>

<article
  class="group relative bg-white border border-gray-200 rounded-lg overflow-hidden
         hover:shadow-lg transition-all duration-200 flex flex-col h-full"
>
  <!-- Image Section -->
  <div class="relative aspect-square bg-gray-50 overflow-hidden">
    {#if product.imageUrl}
      <img
        src={product.imageUrl}
        alt={`${product.name} ${product.size}`}
        class="w-full h-full object-cover group-hover:scale-105 transition-transform duration-300"
        loading="lazy"
      />
    {:else}
      <div
        class="w-full h-full flex items-center justify-center bg-gradient-to-br from-gray-100 to-gray-200"
      >
        <span class="text-6xl">🥣</span>
      </div>
    {/if}

    {#if !inStock}
      <div
        class="absolute top-2 right-2 bg-black text-white text-xs font-bold px-3 py-1 rounded-full"
      >
        Out of Stock
      </div>
    {:else if lowStock}
      <div
        class="absolute top-2 right-2 bg-zing-yellow text-black text-xs font-bold px-3 py-1 rounded-full"
      >
        Low Stock
      </div>
    {/if}
  </div>

  <!-- Product Info -->
  <div class="p-4 flex-grow flex flex-col">
    <h3 class="text-lg font-bold text-black mb-1">{product.name}</h3>
    <p class="text-sm text-gray-600 mb-3">{product.size}</p>

    <!-- Price + Button -->
    <div class="mt-auto flex items-center justify-between gap-3">
      <div class="flex-shrink-0">
        <span class="text-xl font-bold text-black">
          {formatKES(product.priceCents)}
        </span>
      </div>

      <button
        on:click={handleAddToCart}
        disabled={!inStock || isAdding}
        class="flex-1 btn-primary disabled:opacity-50 disabled:cursor-not-allowed
               text-sm sm:text-base py-2 sm:py-3 min-h-[44px]"
        aria-label="Add {product.name} {product.size} to cart"
        aria-live="polite"
      >
        {#if justAdded}
          <span class="inline-flex items-center gap-1">
            <svg
              class="w-4 h-4"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M5 13l4 4L19 7"
              />
            </svg>
            Added!
          </span>
        {:else if isAdding}
          Adding...
        {:else if !inStock}
          Unavailable
        {:else}
          Add to Cart
        {/if}
      </button>
    </div>

    <!-- Error message -->
    {#if errorMsg}
      <p class="text-red-600 text-sm mt-2" aria-live="assertive">{errorMsg}</p>
    {/if}
  </div>
</article>

<style>
  article {
    min-height: 320px;
  }

  img {
    will-change: transform;
  }

  /* Checkmark animation */
  button svg {
    animation: checkmark 0.3s ease-in-out;
  }

  @keyframes checkmark {
    0% {
      transform: scale(0);
    }
    50% {
      transform: scale(1.2);
    }
    100% {
      transform: scale(1);
    }
  }

  /* Fade transition for Added! message */
  [aria-live="polite"] span {
    transition: opacity 0.2s ease-in-out;
  }
</style>
