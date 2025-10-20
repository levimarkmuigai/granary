<script lang="ts">
  import { page } from "$app/stores";
  import AdminHeader from "$lib/components/admin/AdminHeader.svelte";
  import AdminSidebar from "$lib/components/admin/AdminSidebar.svelte";

  $: pageTitle = $page.url.pathname.includes("dashboard")
    ? "Dashboard"
    : $page.url.pathname.includes("orders")
      ? "Orders"
      : $page.url.pathname.includes("products")
        ? "Products"
        : "Admin Panel";
</script>

<div class="min-h-screen w-full flex flex-col bg-[#0a0a0a] text-gray-100">
  <AdminHeader title={pageTitle} />

  <div class="flex flex-1 overflow-hidden">
    <AdminSidebar />

    <main
      class="flex-1 overflow-y-auto p-8 bg-[#0d0d0d] rounded-tl-2xl shadow-inner"
    >
      <slot />
    </main>
  </div>
</div>
