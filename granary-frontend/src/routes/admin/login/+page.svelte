<script lang="ts">
  import { onMount } from "svelte";
  import { AdminStore } from "$lib/application/AdminStore";
  import { services } from "$lib/services";
  import DashboardCards from "$lib/components/admin/DashboardCards.svelte";
  import type { Order, Product } from "$lib/domain";

  let orders: Order[] = [];
  let products: Product[] = [];
  let isLoading = false;
  let errorMessage = "";

  const unsubscribe = AdminStore.subscribe((state) => {
    orders = state.orders;
    products = state.products;
    isLoading = state.isLoading;
    errorMessage = state.errorMessage;
  });

  onMount(async () => {
    isLoading = true;
    errorMessage = "";
    try {
      const [fetchedOrders, fetchedProducts] = await Promise.all([
        services.orderService.fetchAllOrders(),
        services.productService.fetchAllProducts(),
      ]);

      AdminStore.setOrders(fetchedOrders);
      AdminStore.setProducts(fetchedProducts);
    } catch (err) {
      errorMessage = err instanceof Error ? err.message : "Unknown error";
    } finally {
      isLoading = false;
    }
  });

  // Metrics transformed to stats array
  $: stats = [
    {
      label: "Total Orders",
      count: orders.length,
      color: "#e0f2fe",
    },
    {
      label: "New Orders",
      count: orders.filter((o) => o.orderStatus === "NEW").length,
      color: "#dbeafe",
    },
    {
      label: "Low Stock Products",
      count: products.filter((p) => p.stockQuantity <= p.lowStockAlert).length,
      color: "#fef3c7",
    },
  ];
</script>

<section class="p-6">
  <h1 class="text-2xl font-bold mb-6">Admin Dashboard</h1>

  {#if isLoading}
    <p>Loading dashboard...</p>
  {:else if errorMessage}
    <p class="text-red-600">{errorMessage}</p>
  {:else}
    <DashboardCards {stats} />
  {/if}
</section>
