<script lang="ts">
  import { goto } from "$app/navigation";

  export let title: string = "Admin Panel";

  const handleLogout = () => {
    goto("/admin/login");
  };

  const goHome = () => {
    goto("/admin/dashboard");
  };
</script>

<header
  class="bg-[#111111] border-b border-[#1f1f1f] flex justify-between items-center px-8 py-4 shadow-md"
>
  <div
    class="font-semibold text-xl tracking-tight text-[#fdb913] cursor-pointer select-none"
    on:click={goHome}
  >
    {title}
  </div>

  <div class="flex items-center gap-6">
    <span class="text-gray-300 text-sm">Admin</span>
    <button
      class="px-4 py-2 text-sm font-medium bg-[#fdb913] text-black rounded-lg hover:bg-[#e4a90d] transition-colors"
      on:click={handleLogout}
    >
      Logout
    </button>
  </div>
</header>
