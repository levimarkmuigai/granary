<script lang="ts">
  import { howItWorksContent } from "$lib/config/howItWorks";
</script>

<section
  id="how-it-works"
  class="py-12 sm:py-16 bg-gradient-to-b from-gray-50 to-white scroll-mt-16"
>
  <div class="container-zing">
    <!-- Section Header -->
    <div class="text-center mb-12">
      <h2
        class="text-3xl sm:text-4xl font-bold text-black mb-3 animate-fade-in-up"
      >
        {howItWorksContent.title}
      </h2>
      <p class="text-gray-600 max-w-2xl mx-auto animate-fade-in-up delay-100">
        {howItWorksContent.subtitle}
      </p>
    </div>

    <!-- Steps Grid -->
    <div class="grid grid-cols-1 md:grid-cols-3 gap-8 max-w-5xl mx-auto">
      {#each howItWorksContent.steps as step, i}
        <div
          class="flex flex-col items-center text-center p-4 rounded-xl bg-white shadow-md
                 hover:shadow-xl transition-shadow duration-300 group animate-fade-in-up"
          style="animation-delay: {i * 100}ms"
        >
          <div
            class="w-16 h-16 md:w-20 md:h-20 bg-zing-yellow rounded-full flex items-center justify-center
                   text-2xl md:text-3xl font-bold text-black mb-4 shadow-lg transform transition-transform
                   duration-300 group-hover:scale-110 group-hover:rotate-3"
          >
            {step.number}
          </div>
          <h3
            class="text-lg md:text-xl font-semibold text-black mb-2 group-hover:text-zing"
          >
            {step.title}
          </h3>
          <p class="text-gray-600 text-sm md:text-base">{step.description}</p>
        </div>
      {/each}
    </div>

    <!-- Call to Action -->
    <div class="text-center mt-12 animate-fade-in-up delay-300">
      <a
        href={howItWorksContent.cta.href}
        class="inline-flex items-center gap-2 text-sm md:text-base font-medium
               text-gray-600 hover:text-black transition-colors transform hover:scale-105"
      >
        {howItWorksContent.cta.text}
        <svg
          class="w-4 h-4 md:w-5 md:h-5"
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
  </div>
</section>

<style>
  /* Subtle fade-in with upward motion */
  @keyframes fadeInUp {
    from {
      opacity: 0;
      transform: translateY(10px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  .animate-fade-in-up {
    animation: fadeInUp 0.8s ease-out forwards;
  }

  .delay-100 {
    animation-delay: 0.1s;
  }

  .delay-300 {
    animation-delay: 0.3s;
  }

  /* Accent color variable for hover effects */
  :root {
    --zing: #f6c259;
  }

  .group-hover\:text-zing:hover {
    color: var(--zing);
  }
</style>
