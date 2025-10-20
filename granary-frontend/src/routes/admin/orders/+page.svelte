<script lang="ts">
  import { onMount } from "svelte";
  import OrdersTable from "$lib/components/admin/OrdersTable.svelte";
  import { services } from "$lib/services";
  import { AdminStore } from "$lib/application/AdminStore";
  import type { Order, OrderStatus } from "$lib/domain";

  $: orders = $AdminStore.orders;
  $: isLoading = $AdminStore.isLoading;
  $: errorMessage = $AdminStore.errorMessage;

  onMount(fetchOrders);

  async function fetchOrders() {
    AdminStore.setLoading(true);
    AdminStore.setError("");
    try {
      const fetchedOrders: Order[] =
        await services.orderService.fetchAllOrders();
      AdminStore.setOrders(fetchedOrders);
    } catch (err: unknown) {
      console.error(err);
      AdminStore.setError(
        err instanceof Error ? err.message : "Failed to fetch orders"
      );
    } finally {
      AdminStore.setLoading(false);
    }
  }

  async function updateOrderStatus(orderId: string, status: OrderStatus) {
    try {
      await services.orderService.updateOrderStatus(orderId, status);
      AdminStore.updateOrderStatus(orderId, status);
    } catch (err: unknown) {
      console.error(err);
      AdminStore.setError(
        err instanceof Error ? err.message : "Failed to update status"
      );
    }
  }
</script>

<main class="flex-1 overflow-auto p-8">
  {#if isLoading}
    <div class="flex justify-center items-center h-full">
      <div class="text-[#fdb913] text-xl font-semibold animate-pulse">
        Loading orders...
      </div>
    </div>
  {:else if errorMessage}
    <div class="flex justify-center items-center h-full">
      <div class="text-red-500 text-lg font-medium">{errorMessage}</div>
    </div>
  {:else if orders.length === 0}
    <div class="flex justify-center items-center h-full">
      <div class="text-gray-400 text-lg">No orders found.</div>
    </div>
  {:else}
    <OrdersTable {orders} onUpdateStatus={updateOrderStatus} />
  {/if}
</main>
