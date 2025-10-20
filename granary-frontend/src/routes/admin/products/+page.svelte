<script lang="ts">
  import { onMount } from "svelte";
  import { AdminStore } from "$lib/application/AdminStore";
  import { services } from "$lib/services";
  import ProductsTable from "$lib/components/admin/ProductsTable.svelte";
  import ProductForm from "$lib/components/product/ProductForm.svelte";
  import type { Product } from "$lib/domain";

  let products: Product[] = [];
  let isLoading = false;
  let errorMessage = "";
  let editingProduct: Product | null = null;
  let showForm = false;

  const unsubscribe = AdminStore.subscribe(($s) => {
    products = $s.products ?? [];
    isLoading = $s.isLoading;
    errorMessage = $s.errorMessage;
  });

  onMount(async () => {
    AdminStore.setLoading(true);
    AdminStore.setError("");
    try {
      const fetched = await services.productService.fetchAllProducts();
      AdminStore.setProducts(fetched);
    } catch (err) {
      AdminStore.setError(
        err instanceof Error ? err.message : "Failed to load products"
      );
    } finally {
      AdminStore.setLoading(false);
    }
  });

  function onEdit(product: Product) {
    editingProduct = product;
    showForm = true;
  }

  async function onDelete(id: string) {
    if (!confirm("Delete this product?")) return;
    try {
      await services.productService.deleteProduct(id);
      AdminStore.removeProduct(id);
    } catch (err) {
      AdminStore.setError(
        err instanceof Error ? err.message : "Failed to delete product"
      );
    }
  }

  async function onSave(product: Product) {
    try {
      if (product.id) {
        const updated = await services.productService.updateProduct(
          product.id,
          product
        );
        AdminStore.updateProduct(updated);
      } else {
        const created = await services.productService.createProduct(product);
        AdminStore.addProduct(created);
      }
    } catch (err) {
      AdminStore.setError(
        err instanceof Error ? err.message : "Failed to save product"
      );
    } finally {
      showForm = false;
      editingProduct = null;
    }
  }

  function onCancel() {
    showForm = false;
    editingProduct = null;
  }
</script>

<section class="min-h-screen bg-[#0a0a0a] text-gray-200 p-10">
  <header class="flex justify-between items-center mb-10">
    <h1 class="text-4xl font-bold text-[#fdb913] tracking-tight">
      Products Management
    </h1>
    <button
      class="bg-[#fdb913] hover:bg-[#d9a212] text-black px-5 py-2 rounded-xl font-semibold transition-transform hover:scale-[1.03]"
      on:click={() => {
        editingProduct = null;
        showForm = true;
      }}
    >
      Add Product
    </button>
  </header>

  {#if isLoading}
    <div class="flex justify-center items-center h-[60vh]">
      <span class="text-[#fdb913] text-xl font-medium animate-pulse"
        >Loading products...</span
      >
    </div>
  {:else if errorMessage}
    <div class="text-center text-red-500 text-lg">{errorMessage}</div>
  {:else if products.length === 0}
    <div class="text-center text-gray-500 text-lg">No products found.</div>
  {:else}
    <ProductsTable {products} {onEdit} {onDelete} />
  {/if}

  {#if showForm}
    <div
      class="fixed inset-0 bg-black/70 flex items-center justify-center z-50"
    >
      <div
        class="bg-[#111111] rounded-2xl p-8 w-full max-w-lg shadow-[0_0_20px_#fdb91355]"
      >
        <ProductForm
          {editingProduct}
          on:save={(e) => onSave(e.detail)}
          on:cancel={onCancel}
        />
      </div>
    </div>
  {/if}
</section>
