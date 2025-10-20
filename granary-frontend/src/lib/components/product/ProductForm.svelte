<script lang="ts">
  import { createEventDispatcher } from "svelte";
  import type { Product } from "$lib/domain";

  export let editingProduct: Product | null = null;

  const dispatch = createEventDispatcher<{
    save: Product;
    cancel: void;
  }>();

  let product: Product = editingProduct
    ? { ...editingProduct }
    : {
        id: "",
        name: "",
        size: "",
        priceCents: 0,
        stockQuantity: 0,
        lowStockAlert: 5,
        imageUrl: "",
        active: true,
        createdAt: new Date().toISOString(),
        updatedAt: new Date().toISOString(),
      };

  let imagePreview: string | null = product.imageUrl || null;

  function handleSubmit(e: Event) {
    e.preventDefault();
    product.updatedAt = new Date().toISOString();
    dispatch("save", product);
  }

  function handleCancel() {
    dispatch("cancel");
  }

  function formatCurrencyInput(e: Event) {
    const input = e.target as HTMLInputElement;
    const numericValue = parseFloat(input.value);
    if (!isNaN(numericValue))
      product.priceCents = Math.round(numericValue * 100);
  }

  function handleFileChange(e: Event) {
    const input = e.target as HTMLInputElement;
    if (!input.files || input.files.length === 0) return;
    const file = input.files[0];
    const reader = new FileReader();
    reader.onload = () => {
      imagePreview = reader.result as string;
      product.imageUrl = imagePreview; // store as base64 or uploaded URL after backend save
    };
    reader.readAsDataURL(file);
  }
</script>

<form
  class="bg-white p-6 rounded-xl shadow-lg space-y-4 border border-gray-100"
  on:submit|preventDefault={handleSubmit}
>
  <h2 class="text-xl font-semibold text-gray-900">
    {editingProduct ? "Edit Product" : "Add Product"}
  </h2>

  <div class="space-y-2">
    <label for="name" class="block text-sm font-medium text-gray-700"
      >Name</label
    >
    <input
      id="name"
      type="text"
      bind:value={product.name}
      required
      class="w-full rounded-xl border-gray-300 focus:ring-zing focus:border-zing"
    />
  </div>

  <div class="space-y-2">
    <label for="size" class="block text-sm font-medium text-gray-700"
      >Size</label
    >
    <input
      id="size"
      type="text"
      bind:value={product.size}
      required
      class="w-full rounded-xl border-gray-300 focus:ring-zing focus:border-zing"
    />
  </div>

  <div class="space-y-2">
    <label for="price" class="block text-sm font-medium text-gray-700"
      >Price (KES)</label
    >
    <input
      id="price"
      type="number"
      step="0.01"
      value={product.priceCents / 100}
      on:input={formatCurrencyInput}
      required
      class="w-full rounded-xl border-gray-300 focus:ring-zing focus:border-zing"
    />
  </div>

  <div class="space-y-2">
    <label for="stockQuantity" class="block text-sm font-medium text-gray-700"
      >Stock Quantity</label
    >
    <input
      id="stockQuantity"
      type="number"
      bind:value={product.stockQuantity}
      min="0"
      required
      class="w-full rounded-xl border-gray-300 focus:ring-zing focus:border-zing"
    />
  </div>

  <div class="space-y-2">
    <label for="lowStockAlert" class="block text-sm font-medium text-gray-700"
      >Low Stock Alert</label
    >
    <input
      id="lowStockAlert"
      type="number"
      bind:value={product.lowStockAlert}
      min="0"
      required
      class="w-full rounded-xl border-gray-300 focus:ring-zing focus:border-zing"
    />
  </div>

  <div class="space-y-2">
    <label for="imageFile" class="block text-sm font-medium text-gray-700"
      >Product Image</label
    >
    <input
      id="imageFile"
      type="file"
      accept="image/*"
      on:change={handleFileChange}
      class="w-full"
    />
    {#if imagePreview}
      <img
        src={imagePreview}
        alt="Preview"
        class="mt-2 w-32 h-32 object-cover rounded"
      />
    {/if}
  </div>

  <div class="flex justify-end space-x-2 mt-4">
    <button
      type="button"
      on:click={handleCancel}
      class="px-4 py-2 rounded-xl border border-gray-300 hover:bg-gray-50 text-gray-700"
    >
      Cancel
    </button>
    <button
      type="submit"
      class="px-4 py-2 rounded-xl bg-zing hover:bg-zing-dark text-white font-semibold"
    >
      {editingProduct ? "Update Product" : "Add Product"}
    </button>
  </div>
</form>
