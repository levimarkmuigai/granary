<script lang="ts">
  import { goto } from "$app/navigation";
  import {
    cartStore,
    cartTotalStore,
    addToCart,
    removeFromCart,
    clearCart,
    type CartItem,
  } from "$lib/application/CartStore";
  import type { Product } from "$lib/domain";
  import { cartPageConfig } from "$lib/config/cartPage";

  $: items = $cartStore;
  $: total = $cartTotalStore;
  $: isCartEmpty = items.length === 0;
  let isSubmitting = false;
  let errorMessage = "";

  const formatCurrency = (cents: number): string =>
    (cents / 100).toLocaleString(cartPageConfig.currency.locale, {
      style: "currency",
      currency: cartPageConfig.currency.code,
    });

  const handleIncrease = async (item: CartItem) => {
    if (isSubmitting) return;
    isSubmitting = true;
    errorMessage = "";
    try {
      const res = await fetch(`/api/products/${item.productId}`);
      if (!res.ok) throw new Error("Failed to fetch product details");
      const product: Product = await res.json();
      addToCart(product, 1);
    } catch (err) {
      console.error(err);
      errorMessage = err instanceof Error ? err.message : "Unknown error";
    } finally {
      isSubmitting = false;
    }
  };

  const handleRemove = async (item: CartItem) => {
    if (!confirm(`Remove ${item.name} from cart?`)) return;
    try {
      const res = await fetch(`/api/cart/remove`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ productId: item.productId }),
      });
      if (!res.ok) throw new Error("Failed to remove item");
      removeFromCart(item.productId);
    } catch (err) {
      console.error(err);
      errorMessage = err instanceof Error ? err.message : "Unknown error";
    }
  };

  const handleClearCart = async () => {
    if (!confirm("Are you sure you want to clear your cart?")) return;
    try {
      const res = await fetch(`/api/cart/clear`, { method: "POST" });
      if (!res.ok) throw new Error("Failed to clear cart");
      clearCart();
    } catch (err) {
      console.error(err);
      errorMessage = err instanceof Error ? err.message : "Unknown error";
    }
  };

  const handleCheckout = async () => {
    if (isCartEmpty || isSubmitting) return;
    isSubmitting = true;
    try {
      await goto("/checkout");
    } catch (err) {
      console.error("Failed to navigate to checkout:", err);
    } finally {
      isSubmitting = false;
    }
  };
</script>

<svelte:head>
  <title>{cartPageConfig.title} ({items.length}) | Granary</title>
</svelte:head>

<div class="max-w-5xl mx-auto py-8 px-4 sm:px-6 lg:px-8">
  <h1 class="text-4xl font-extrabold text-black mb-8">
    {cartPageConfig.title}
  </h1>

  {#if isCartEmpty}
    <div
      class="text-center p-10 rounded-xl bg-[#fdf6e3] border border-gray-200"
    >
      <p class="text-xl text-gray-700 mb-4">{cartPageConfig.empty.message}</p>
      <a
        href={cartPageConfig.empty.ctaHref}
        class="text-[#fdb913] font-semibold text-lg hover:text-black transition-colors duration-200"
      >
        {cartPageConfig.empty.ctaText}
      </a>
    </div>
  {:else}
    {#if errorMessage}
      <p class="text-red-600 mb-4">{errorMessage}</p>
    {/if}

    <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
      <section class="md:col-span-2 space-y-4">
        {#each items as item (item.productId)}
          <div
            class="flex items-center bg-white p-4 rounded-xl shadow hover:shadow-lg border border-gray-100 transition duration-200"
          >
            <div class="flex-grow">
              <h2 class="text-lg font-semibold text-black">{item.name}</h2>
              <p class="text-sm text-gray-500">
                {item.size} • {formatCurrency(item.priceCents)}
              </p>
              <p class="text-lg font-bold text-gray-800 mt-1">
                {formatCurrency(item.priceCents * item.quantity)}
              </p>
            </div>
            <div class="flex items-center space-x-2 ml-4">
              <button
                type="button"
                on:click={() => handleRemove(item)}
                class="p-2 rounded-full w-8 h-8 flex items-center justify-center bg-red-50 text-red-600 hover:bg-red-200 font-bold text-lg transition duration-150"
                aria-label={`Remove ${item.name}`}
              >
                ✕
              </button>
              <span class="text-xl font-medium w-8 text-center"
                >{item.quantity}</span
              >
              <button
                type="button"
                on:click={() => handleIncrease(item)}
                class="p-2 rounded-full w-8 h-8 flex items-center justify-center bg-[#fdb913]/20 text-[#fdb913] hover:bg-[#fdb913]/40 font-bold text-lg transition duration-150"
                aria-label={`Increase quantity of ${item.name}`}
                disabled={isSubmitting}
              >
                +
              </button>
            </div>
          </div>
        {/each}
        <button
          type="button"
          on:click={handleClearCart}
          class="mt-4 text-red-500 hover:text-red-700 font-medium transition duration-150"
        >
          {cartPageConfig.summary.clearCartButton}
        </button>
      </section>

      <section
        class="md:col-span-1 p-6 bg-white rounded-xl shadow-lg border border-gray-100 h-fit flex flex-col justify-between"
      >
        <div>
          <h3 class="text-2xl font-semibold text-black mb-4">
            {cartPageConfig.summary.title}
          </h3>
          <div class="flex justify-between text-lg text-gray-700 mb-4">
            <span>{cartPageConfig.summary.itemsLabel} ({items.length})</span>
            <span class="font-medium">{formatCurrency(total)}</span>
          </div>
          <div
            class="flex justify-between font-bold text-xl pt-4 border-t border-gray-200"
          >
            <span>{cartPageConfig.summary.grandTotalLabel}</span>
            <span class="text-[#fdb913]">{formatCurrency(total)}</span>
          </div>
        </div>
        <button
          type="button"
          on:click={handleCheckout}
          class="mt-6 w-full py-3 bg-[#fdb913] text-black font-extrabold rounded-xl hover:bg-[#fdb913]/90 transition duration-150 disabled:opacity-50"
          disabled={isCartEmpty || isSubmitting}
        >
          {isSubmitting
            ? "Processing..."
            : cartPageConfig.summary.checkoutButton}
        </button>
      </section>
    </div>
  {/if}
</div>

<style>
  button,
  .flex.items-center.bg-white {
    transition: all 0.2s ease-in-out;
  }
  .disabled\:opacity-50:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
</style>
