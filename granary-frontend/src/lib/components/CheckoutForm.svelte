<script lang="ts">
  import type { CartItem } from "$lib/application/CartStore";
  import { writable } from "svelte/store";

  // Props from parent
  export let items: CartItem[] = [];
  export let total: number = 0;
  export let handlePlaceOrder:
    | ((customer: {
        name: string;
        email: string;
        phone: string;
        address: string;
        city: string;
      }) => void)
    | undefined;

  // Customer form state
  let customer = {
    name: "",
    email: "",
    phone: "",
    address: "",
    city: "",
  };

  const customerKeys: (keyof typeof customer)[] = [
    "name",
    "email",
    "phone",
    "address",
    "city",
  ];

  let paymentMethod = "mpesa";

  function submitForm(e: Event) {
    e.preventDefault();
    if (!items.length) {
      alert("Cart is empty.");
      return;
    }
    handlePlaceOrder?.({ ...customer });
  }

  function formatCurrency(cents: number) {
    return (cents / 100).toLocaleString("en-KE", {
      style: "currency",
      currency: "KES",
    });
  }
</script>

<section class="min-h-screen bg-gray-50 py-12">
  <div class="max-w-6xl mx-auto px-4 md:px-8 grid md:grid-cols-2 gap-10">
    <!-- Checkout Form -->
    <form
      class="bg-white rounded-2xl shadow-lg p-8 space-y-6 border border-gray-100"
      on:submit={submitForm}
    >
      <h1 class="text-2xl font-bold text-gray-900">Checkout</h1>
      <p class="text-sm text-gray-600">Complete your order below.</p>

      {#each customerKeys as key}
        <div>
          <label for={key} class="block text-sm font-medium text-gray-700 mb-1">
            {key.charAt(0).toUpperCase() + key.slice(1)}
          </label>
          <input
            id={key}
            type={key === "email" ? "email" : "text"}
            bind:value={customer[key]}
            required
            class="w-full rounded-xl border-gray-300 focus:ring-zing focus:border-zing"
          />
        </div>
      {/each}

      <!-- Payment Method -->
      <div class="pt-6 border-t border-gray-200">
        <h2 class="text-lg font-semibold mb-3 text-gray-900">Payment Method</h2>
        <div class="space-y-3">
          <label class="flex items-center gap-2 cursor-pointer">
            <input
              type="radio"
              name="payment"
              value="mpesa"
              bind:group={paymentMethod}
            />
            <span class="text-gray-700">M-Pesa</span>
          </label>
          <label class="flex items-center gap-2 cursor-pointer">
            <input
              type="radio"
              name="payment"
              value="card"
              bind:group={paymentMethod}
            />
            <span class="text-gray-700">Credit / Debit Card</span>
          </label>
        </div>
      </div>

      <button
        type="submit"
        class="w-full bg-zing hover:bg-zing-dark text-white py-3 rounded-xl font-semibold transition-all duration-150 shadow-sm"
      >
        Place Order
      </button>
    </form>

    <!-- Dynamic Order Summary -->
    <div
      class="bg-white rounded-2xl shadow-lg p-8 h-fit border border-gray-100"
    >
      <h2 class="text-xl font-semibold text-gray-900 mb-4">Order Summary</h2>
      {#if items.length}
        <ul class="space-y-3 text-sm text-gray-700">
          {#each items as item}
            <li class="flex justify-between">
              <span>{item.name} ({item.quantity})</span>
              <span>{formatCurrency(item.priceCents * item.quantity)}</span>
            </li>
          {/each}
          <li
            class="border-t border-gray-200 pt-3 flex justify-between font-semibold text-gray-900"
          >
            <span>Total</span>
            <span>{formatCurrency(total)}</span>
          </li>
        </ul>
        <p class="mt-4 text-xs text-gray-500">All prices include VAT.</p>
      {:else}
        <p class="text-gray-500">Your cart is empty.</p>
      {/if}
    </div>
  </div>
</section>
