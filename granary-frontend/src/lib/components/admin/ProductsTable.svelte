<script lang="ts">
  import type { Product } from "$lib/domain";
  import { adminPageConfig } from "$lib/config/adminPage";

  export let products: Product[] = [];
  export let onEdit: (product: Product) => void;
  export let onDelete: (id: string) => void;

  const { lowStockBadge, actionLabels } = adminPageConfig.productsTable;
</script>

<table class="min-w-full divide-y divide-gray-200">
  <thead>
    <tr>
      <th>Name</th>
      <th>Price</th>
      <th>Stock</th>
      <th>Actions</th>
    </tr>
  </thead>
  <tbody>
    {#each products as product}
      <tr class="hover:bg-gray-50">
        <td>{product.name}</td>
        <td>KES {(product.priceCents / 100).toLocaleString()}</td>
        <td>
          {product.stockQuantity}
          {#if product.stockQuantity <= product.lowStockAlert}
            <span
              class="ml-2 px-1 py-0.5 text-xs rounded"
              style="background-color: {lowStockBadge.bgColor}; color: {lowStockBadge.textColor}"
            >
              {lowStockBadge.text}
            </span>
          {/if}
        </td>
        <td class="space-x-2">
          <button
            class="px-2 py-1 bg-blue-500 text-white rounded"
            on:click={() => onEdit(product)}
          >
            {actionLabels.edit}
          </button>
          <button
            class="px-2 py-1 bg-red-500 text-white rounded"
            on:click={() => onDelete(product.id)}
          >
            {actionLabels.delete}
          </button>
        </td>
      </tr>
    {/each}
  </tbody>
</table>
