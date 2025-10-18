<script lang="ts">
  export let title: string = "Breakfast Just Got Healthier";
  export let subtitle: string =
    "Low Calorie, Homemade Granola with No Preservatives";
  export let ctaText: string = "See How It Works";
  export let ctaHref: string = "#how-it-works";
  export let imageUrl: string = "";
</script>

<section
  class="relative bg-gradient-to-b from-gray-50 to-white py-12 sm:py-16 lg:py-20"
>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
    <div class="grid grid-cols-1 lg:grid-cols-2 gap-8 lg:gap-12 items-center">
      <!-- Text Content -->
      <div
        class="text-center lg:text-left order-2 lg:order-1 flex flex-col justify-center"
      >
        <!-- Delivery Badge -->
        <div
          class="inline-flex items-center gap-2 bg-[#fdb913]/10 border border-[#fdb913]/30 px-4 py-2 rounded-full mb-6 mx-auto lg:mx-0"
        >
          <span class="text-xl">🚚</span>
          <span class="text-sm font-semibold text-black"
            >Delivery Across Nairobi</span
          >
        </div>

        <!-- Heading & Subtitle -->
        <h1
          class="text-4xl sm:text-5xl lg:text-6xl font-bold text-black mb-4 leading-tight"
        >
          {title}
        </h1>
        <p
          class="text-lg sm:text-xl text-gray-600 mb-8 max-w-xl mx-auto lg:mx-0"
        >
          {subtitle}
        </p>

        <!-- CTA Button -->
        <div
          class="flex flex-col sm:flex-row gap-4 justify-center lg:justify-start"
        >
          <a
            href={ctaHref}
            class="btn-primary text-base sm:text-lg px-6 sm:px-8 py-3 sm:py-4 inline-flex items-center justify-center gap-2"
          >
            {ctaText}
            <svg
              class="w-5 h-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
              stroke-width="2"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                d="M19 9l-7 7-7-7"
              />
            </svg>
          </a>
        </div>

        <!-- Trust Indicators with emojis -->
        <div
          class="mt-10 sm:mt-12 grid grid-cols-3 gap-6 max-w-md mx-auto lg:mx-0"
        >
          <!-- Natural -->
          <div class="flex flex-col items-center lg:items-start group relative">
            <div class="text-5xl mb-2">🌿</div>
            <div
              class="text-sm font-semibold text-black text-center lg:text-left group-hover:scale-110 transition-transform duration-300"
            >
              100% Natural
            </div>
          </div>

          <!-- Fresh (Granola emoji) -->
          <div class="flex flex-col items-center lg:items-start group relative">
            <div class="text-5xl mb-2">🥣</div>
            <div
              class="text-sm font-semibold text-black text-center lg:text-left group-hover:scale-110 transition-transform duration-300"
            >
              Fresh Daily
            </div>
          </div>

          <!-- Local (Kenyan flag emoji) -->
          <div class="flex flex-col items-center lg:items-start group relative">
            <div class="text-5xl mb-2">🇰🇪</div>
            <div
              class="text-sm font-semibold text-black text-center lg:text-left group-hover:scale-110 transition-transform duration-300"
            >
              Local Made
            </div>
          </div>
        </div>
      </div>

      <!-- Hero Image -->
      <div class="order-1 lg:order-2 flex justify-center lg:justify-end">
        {#if imageUrl}
          <img
            src={imageUrl}
            alt="Zing Healthy Treats Granola"
            class="w-full max-w-lg rounded-2xl shadow-xl"
            loading="eager"
          />
        {:else}
          <div
            class="w-full max-w-md aspect-[4/3] bg-gradient-to-br from-[#fdb913]/10 via-gray-50 to-gray-100 rounded-2xl flex flex-col items-center justify-center shadow-xl border border-gray-200"
          >
            <div class="text-center">
              <div class="text-7xl sm:text-8xl mb-4">🥣</div>
              <p class="text-lg sm:text-xl font-semibold text-gray-700">
                Delicious Granola
              </p>
            </div>
          </div>
        {/if}
      </div>
    </div>
  </div>
</section>

<style>
  h1,
  p,
  a {
    animation: gentleFadeIn 0.8s ease-out backwards;
  }
  h1 {
    animation-delay: 0.1s;
  }
  p {
    animation-delay: 0.2s;
  }
  a {
    animation-delay: 0.3s;
  }

  @keyframes gentleFadeIn {
    from {
      opacity: 0;
      transform: translateY(10px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  a[href^="#"] {
    scroll-behavior: smooth;
  }
</style>
