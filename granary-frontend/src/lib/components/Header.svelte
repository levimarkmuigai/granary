<script lang="ts">
  /**
   * Header Component
   * Location: src/lib/components/Header.svelte
   *
   * Main navigation header with cart and branding
   * Improved desktop spacing and removed non-existent links
   */

  import { page } from "$app/stores";
  import { cartStore } from "$lib/application/CartStore";
  import { formatKES } from "$lib/utils/currency";
  import CartIcon from "./CartIcon.svelte";

  // Props
  export let cartTotal: number;

  // Reactive values
  $: cartItemCount = $cartStore.length;
  $: currentPath = $page.url.pathname;

  // Navigation links (removed About page)
  const navLinks = [
    { href: "/#products", label: "Shop", scroll: true },
    { href: "/terms", label: "Terms", scroll: false },
  ];

  // Check if a route is active
  function isActive(href: string): boolean {
    if (href === "/" || href === "/#products") {
      return currentPath === "/";
    }
    return currentPath === href;
  }
</script>

<header
  class="sticky top-0 z-50 bg-white/95 backdrop-blur-md border-b border-gray-200 shadow-sm"
>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
    <div class="flex justify-between items-center h-16">
      <!-- Logo/Brand -->
      <a
        href="/"
        class="flex items-center gap-2 group focus:outline-none focus:ring-2 focus:ring-zing-yellow rounded-lg px-3 py-2 -ml-3"
        aria-label="Zing Healthy Treats - Go to homepage"
      >
        <span
          class="text-2xl sm:text-3xl font-bold text-black group-hover:text-gray-700 transition-colors"
        >
          Zing
        </span>
        <span
          class="text-xl sm:text-2xl group-hover:scale-110 transition-transform origin-center"
        >
          ⚡
        </span>
      </a>

      <!-- Desktop Navigation - Better spacing -->
      <nav
        class="hidden sm:flex items-center gap-2"
        aria-label="Main navigation"
      >
        {#each navLinks as link}
          <a
            href={link.href}
            class="px-4 py-2 text-sm font-medium text-gray-700 hover:text-black
                   hover:bg-gray-50 rounded-lg transition-all duration-150"
            aria-current={isActive(link.href) ? "page" : undefined}
          >
            {link.label}
          </a>
        {/each}
      </nav>

      <!-- Cart & Total - Better alignment -->
      <div class="flex items-center gap-3 sm:gap-4">
        <!-- Cart Icon -->
        <CartIcon count={cartItemCount} size="md" />

        <!-- Cart Total (Desktop only) - Better styling -->
        <div class="hidden sm:flex items-center gap-2">
          <span class="text-sm font-semibold text-black">
            {formatKES(cartTotal)}
          </span>
        </div>
      </div>
    </div>

    <!-- Mobile Navigation (cleaner) -->
    <nav
      class="sm:hidden border-t border-gray-100 -mx-4"
      aria-label="Mobile navigation"
    >
      <div class="flex">
        {#each navLinks as link}
          <a
            href={link.href}
            class="flex-1 text-center py-3 text-sm font-medium transition-colors
                   {isActive(link.href)
              ? 'text-black border-b-2 border-zing-yellow bg-zing-yellow/5'
              : 'text-gray-600 hover:text-black hover:bg-gray-50'}"
            aria-current={isActive(link.href) ? "page" : undefined}
          >
            {link.label}
          </a>
        {/each}
      </div>
    </nav>
  </div>
</header>

<style>
  /* Smooth backdrop blur */
  header {
    -webkit-backdrop-filter: blur(12px);
  }

  /* Remove tap highlight on mobile */
  a {
    -webkit-tap-highlight-color: transparent;
  }

  /* Smooth transitions */
  a,
  span {
    transition-duration: 150ms;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
  }

  /* Ensure minimum touch target size */
  @media (max-width: 640px) {
    nav a {
      min-height: 44px;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
</style>
