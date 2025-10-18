<script lang="ts">
  import "../app.css";
  import { page } from "$app/stores";
  import { cartTotalStore } from "$lib/application/CartStore";

  import Header from "$lib/components/Header.svelte";
  import Footer from "$lib/components/Footer.svelte";
  import ProductGrid from "$lib/components/product/ProductGrid.svelte";
  import HeroSection from "$lib/components/HeroSection.svelte";
  import HowItWorksSection from "$lib/components/HowItWorksSection.svelte";
  import WhyChooseZingSection from "$lib/components/WhyChooseZingSection.svelte";

  import type { LayoutData } from "./$types";

  export let data: LayoutData;

  const { products, error } = data;

  // Reactive store values
  $: cartTotal = $cartTotalStore; // unwrap the store for type number
  $: isHomepage = $page.url.pathname === "/";
</script>

<svelte:head>
  {#if isHomepage}
    <title>Zing Healthy Treats - Premium Granola in Nairobi</title>
    <meta
      name="description"
      content="Low calorie, homemade granola with no preservatives. Delivered fresh in Nairobi."
    />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  {/if}
</svelte:head>

<div class="min-h-screen flex flex-col bg-white">
  <Header {cartTotal} />

  <main
    class="flex-grow w-full max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 flex flex-col gap-24 md:gap-16"
  >
    {#if isHomepage}
      <HeroSection
        title="Breakfast Just Got Healthier"
        subtitle="Low Calorie, Homemade Granola with No Preservatives"
        ctaText="See How It Works"
        ctaHref="#how-it-works"
      />

      <HowItWorksSection />

      <section id="products" class="scroll-mt-16 mt-12">
        {#if products?.length}
          <ProductGrid
            {products}
            title="Our Granola Collection"
            subtitle="Handcrafted with love in Nairobi. Choose your favorite flavor and size."
          />
        {:else}
          <p class="text-center text-red-500 mt-8 text-lg">
            ⚠️ {error ?? "Could not load products. Please try again later."}
          </p>
        {/if}
      </section>

      <WhyChooseZingSection />
    {:else}
      <slot />
    {/if}
  </main>

  <Footer />
</div>
