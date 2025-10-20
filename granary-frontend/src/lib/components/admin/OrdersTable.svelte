<script lang="ts">
  import type { Order, OrderStatus } from "$lib/domain";
  import { adminPageConfig } from "$lib/config/adminPage";

  export let orders: Order[] = [];
  export let onUpdateStatus: (id: string, status: OrderStatus) => void;

  const { statusColors, actionLabels } = adminPageConfig.ordersTable;

  function getStatusColor(status: OrderStatus): string {
    const map: Record<OrderStatus, string> = {
      NEW: statusColors.New,
      PACKAGING: statusColors.Packaging,
      READY: statusColors.Ready,
      DELIVERED: statusColors.Delivered,
    };
    return map[status] || "#6B7280";
  }
</script>

<div
  class="overflow-x-auto bg-[#0a0a0a] text-gray-200 rounded-2xl shadow-[0_0_20px_rgba(253,185,19,0.15)] p-8"
>
  <table class="w-full text-left border-separate border-spacing-y-3">
    <thead
      class="text-sm uppercase text-gray-400 tracking-wider border-b border-[#1a1a1a]"
    >
      <tr>
        <th class="px-5 py-3">Order ID</th>
        <th class="px-5 py-3">Customer</th>
        <th class="px-5 py-3">Total</th>
        <th class="px-5 py-3">Status</th>
        <th class="px-5 py-3 text-center">Actions</th>
      </tr>
    </thead>

    <tbody>
      {#each orders as order (order.id)}
        <tr
          class="bg-[#111111] hover:bg-[#151515] rounded-xl transition-all duration-200"
        >
          <td class="px-5 py-4 text-[#fdb913] font-semibold">{order.id}</td>
          <td class="px-5 py-4">{order.customerDetails?.name || "N/A"}</td>
          <td class="px-5 py-4"
            >KES {(order.totalAmountCents / 100).toLocaleString()}</td
          >
          <td class="px-5 py-4">
            <span
              class="px-3 py-1.5 rounded-full text-sm font-semibold text-white"
              style="background-color: {getStatusColor(order.orderStatus)}"
            >
              {order.orderStatus}
            </span>
          </td>

          <td class="px-5 py-4 flex gap-3 justify-center">
            {#if order.orderStatus === "NEW"}
              <button
                class="px-4 py-2 rounded-lg bg-[#fdb913] text-black text-sm font-semibold hover:bg-[#fecd36] transition"
                on:click={() => onUpdateStatus(order.id, "PACKAGING")}
              >
                {actionLabels.markPackaging}
              </button>
            {:else if order.orderStatus === "PACKAGING"}
              <button
                class="px-4 py-2 rounded-lg bg-green-500 text-white text-sm font-semibold hover:bg-green-600 transition"
                on:click={() => onUpdateStatus(order.id, "READY")}
              >
                {actionLabels.markReady}
              </button>
            {/if}

            {#if order.orderStatus !== "DELIVERED"}
              <button
                class="px-4 py-2 rounded-lg bg-gray-700 text-white text-sm font-semibold hover:bg-gray-600 transition"
                on:click={() => onUpdateStatus(order.id, "DELIVERED")}
              >
                {actionLabels.markDelivered}
              </button>
            {/if}
          </td>
        </tr>
      {/each}
    </tbody>
  </table>

  {#if orders.length === 0}
    <div class="text-center text-gray-500 py-12 text-sm">
      No orders available.
    </div>
  {/if}
</div>
