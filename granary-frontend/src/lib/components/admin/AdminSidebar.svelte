<script lang="ts">
  import { goto } from "$app/navigation";
  import { adminPageConfig } from "$lib/config/adminPage";

  export let active: string = "dashboard";

  const { sidebar } = adminPageConfig;

  interface NavItem {
    label: string;
    path: string;
    key: string;
  }

  const navItems: NavItem[] = [
    { label: sidebar.dashboard, path: "/admin/dashboard", key: "dashboard" },
    { label: sidebar.orders, path: "/admin/orders", key: "orders" },
    { label: sidebar.products, path: "/admin/products", key: "products" },
  ];

  function navigate(item: NavItem) {
    if (item.key !== active) goto(item.path);
  }
</script>

<nav
  class="bg-[#0a0a0a] text-gray-300 w-64 min-h-screen flex flex-col border-r border-[#1a1a1a] shadow-[4px_0_12px_rgba(0,0,0,0.3)]"
>
  <div class="px-6 py-8 border-b border-[#1a1a1a]">
    <h1 class="text-2xl font-semibold text-[#fdb913] tracking-tight">
      Admin Panel
    </h1>
  </div>

  <div class="flex-1 p-4 flex flex-col gap-2">
    {#each navItems as item}
      <button
        class="text-left w-full px-4 py-3 rounded-lg transition-all duration-300 font-medium
               {active === item.key
          ? 'bg-[#111111] text-[#fdb913] shadow-[0_0_12px_#fdb91333]'
          : 'hover:bg-[#111111] hover:text-[#fdb913]'}"
        on:click={() => navigate(item)}
      >
        {item.label}
      </button>
    {/each}
  </div>

  <div class="p-4 border-t border-[#1a1a1a] text-xs text-gray-500">
    © {new Date().getFullYear()} Zing Admin
  </div>
</nav>
