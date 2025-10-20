<script lang="ts">
  import { onMount } from "svelte";
  import { derived } from "svelte/store";
  import { AdminStore } from "$lib/application/AdminStore";
  import { services } from "$lib/services";

  const metrics = derived(AdminStore, ($s) => ({
    newOrders: $s.orders.filter((o) => o.orderStatus === "NEW").length,
    packagingOrders: $s.orders.filter((o) => o.orderStatus === "PACKAGING")
      .length,
    readyOrders: $s.orders.filter((o) => o.orderStatus === "READY").length,
    totalProducts: $s.products.length,
  }));

  onMount(async () => {
    AdminStore.setLoading(true);
    try {
      const [orders, products] = await Promise.all([
        services.orderService.fetchAllOrders(),
        services.productService.fetchAllProducts(),
      ]);
      AdminStore.setOrders(orders);
      AdminStore.setProducts(products);
    } catch (err) {
      console.error("Dashboard data load failed:", err);
    } finally {
      AdminStore.setLoading(false);
    }
  });
</script>

<section
  class="min-h-screen bg-[#0a0a0a] text-gray-200 pt-4 px-10 pb-10 flex flex-col"
>
  <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-2 gap-10 flex-1">
    <!-- New Orders -->
    <article
      class="bg-[#111111] aspect-square rounded-2xl p-8 shadow-[0_0_10px_#fdb91333]
             hover:shadow-[0_0_20px_#fdb91355] hover:scale-[1.03]
             transition-transform duration-300 flex flex-col justify-between"
    >
      <div>
        <h2 class="text-sm uppercase tracking-wide text-gray-400">
          New Orders
        </h2>
        <div class="text-6xl font-extrabold text-[#fdb913] mt-6">
          {$metrics.newOrders}
        </div>
      </div>
      <div class="mt-8 w-full bg-gray-800 h-3 rounded-full">
        <div
          class="h-3 rounded-full bg-[#fdb913] transition-all duration-700"
          style="width: {Math.min($metrics.newOrders * 10, 100)}%"
        ></div>
      </div>
    </article>

    <!-- Packaging Orders -->
    <article
      class="bg-[#111111] aspect-square rounded-2xl p-8 shadow-[0_0_10px_#fdb91333]
             hover:shadow-[0_0_20px_#fdb91355] hover:scale-[1.03]
             transition-transform duration-300 flex flex-col justify-between"
    >
      <div>
        <h2 class="text-sm uppercase tracking-wide text-gray-400">Packaging</h2>
        <div class="text-6xl font-extrabold text-[#fdb913] mt-6">
          {$metrics.packagingOrders}
        </div>
      </div>
      <div class="mt-8 w-full bg-gray-800 h-3 rounded-full">
        <div
          class="h-3 rounded-full bg-[#fdb913] transition-all duration-700"
          style="width: {Math.min($metrics.packagingOrders * 10, 100)}%"
        ></div>
      </div>
    </article>

    <!-- Ready Orders -->
    <article
      class="bg-[#111111] aspect-square rounded-2xl p-8 shadow-[0_0_10px_#fdb91333]
             hover:shadow-[0_0_20px_#fdb91355] hover:scale-[1.03]
             transition-transform duration-300 flex flex-col justify-between"
    >
      <div>
        <h2 class="text-sm uppercase tracking-wide text-gray-400">
          Ready Orders
        </h2>
        <div class="text-6xl font-extrabold text-[#fdb913] mt-6">
          {$metrics.readyOrders}
        </div>
      </div>
      <div class="mt-8 w-full bg-gray-800 h-3 rounded-full">
        <div
          class="h-3 rounded-full bg-[#fdb913] transition-all duration-700"
          style="width: {Math.min($metrics.readyOrders * 10, 100)}%"
        ></div>
      </div>
    </article>

    <!-- Total Products -->
    <article
      class="bg-[#111111] aspect-square rounded-2xl p-8 shadow-[0_0_10px_#fdb91333]
             hover:shadow-[0_0_20px_#fdb91355] hover:scale-[1.03]
             transition-transform duration-300 flex flex-col justify-center items-start"
    >
      <h2 class="text-sm uppercase tracking-wide text-gray-400">
        Total Products
      </h2>
      <div class="text-6xl font-extrabold text-[#fdb913] mt-6">
        {$metrics.totalProducts}
      </div>
    </article>
  </div>
</section>
