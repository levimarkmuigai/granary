<script lang="ts">
  import { whyChooseContent } from "$lib/config/whyChoose";
</script>

<section class="bg-gray-50 py-12 sm:py-16">
  <div class="container-zing">
    <div class="text-center mb-12">
      <h2 class="text-3xl sm:text-4xl font-bold text-black mb-3">
        {whyChooseContent.title}
      </h2>
      <p class="text-gray-600 max-w-2xl mx-auto">
        {whyChooseContent.subtitle}
      </p>
    </div>

    <div class="grid grid-cols-1 md:grid-cols-3 gap-8">
      {#each whyChooseContent.reasons as reason}
        <div class="text-center p-6 group">
          <div
            class="text-5xl mb-4 group-hover:scale-110 transition-transform duration-200 inline-block"
          >
            {reason.emoji}
          </div>
          <h3 class="text-xl font-bold text-black mb-2">{reason.title}</h3>
          <p class="text-gray-600">{reason.description}</p>
        </div>
      {/each}
    </div>
  </div>
</section>
