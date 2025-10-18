<script lang="ts">
  export let count: number = 0;
  export let size: "sm" | "md" | "lg" = "md";

  const iconSizes = {
    sm: "h-5 w-5",
    md: "h-6 w-6",
    lg: "h-8 w-8",
  };

  $: showBadge = count > 0;
  $: displayCount = count > 99 ? "99+" : count.toString();
</script>

<a
  href="/cart"
  class="relative inline-flex items-center justify-center p-2
         text-black hover:text-gray-600 transition-colors duration-150
         focus:outline-none focus:ring-2 focus:ring-zing-yellow focus:ring-offset-2 rounded-md
         min-w-[44px] min-h-[44px]"
  aria-label="Shopping cart with {count} {count === 1 ? 'item' : 'items'}"
>
  <svg
    class={iconSizes[size]}
    fill="none"
    viewBox="0 0 24 24"
    stroke="currentColor"
    stroke-width="2"
    aria-hidden="true"
  >
    <path
      stroke-linecap="round"
      stroke-linejoin="round"
      d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z"
    />
  </svg>

  {#if showBadge}
    <span
      class="absolute -top-0.5 -right-0.5
             bg-zing-yellow text-black
             text-xs font-bold
             rounded-full
             min-w-[20px] h-5
             flex items-center justify-center
             px-1.5
             shadow-sm
             border border-white"
      aria-hidden="true"
    >
      {displayCount}
    </span>
  {/if}
</a>

<style>
  a {
    -webkit-tap-highlight-color: transparent;
  }
  span {
    animation: badge-pop 0.3s cubic-bezier(0.68, -0.55, 0.265, 1.55);
  }

  @keyframes badge-pop {
    0% {
      transform: scale(0);
    }
    50% {
      transform: scale(1.1);
    }
    100% {
      transform: scale(1);
    }
  }
</style>
