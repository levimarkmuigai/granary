<script lang="ts">
  import { footerLinks, contactInfo, socialLinks } from "$lib/config/footer";
  const currentYear = new Date().getFullYear();

  // Filter out FAQ, Delivery, and Contact links
  const filteredLinks = footerLinks.filter(
    (link) => !["FAQ", "Delivery", "Contact"].includes(link.label)
  );
</script>

<footer class="bg-white border-t border-gray-200 mt-20">
  <div
    class="max-w-7xl mx-auto py-14 px-6 lg:px-8 grid gap-8 lg:grid-cols-3 items-start text-center lg:text-left"
  >
    <!-- Brand -->
    <div class="flex flex-col gap-2 items-center lg:items-start">
      <div class="flex items-center gap-2 mb-2">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="w-6 h-6 text-zing-yellow"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
          stroke-width="2"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M13 2L3 14h9l-1 8L21 10h-9l1-8z"
          />
        </svg>
        <span class="text-2xl font-bold text-black tracking-tight">Zing</span>
      </div>
      <p class="text-sm text-gray-600">Low Calorie, Homemade Granola</p>
    </div>

    <!-- Navigation Links -->
    <nav
      class="flex flex-wrap justify-center lg:justify-start gap-6 items-center"
    >
      {#each filteredLinks as link}
        <a
          href={link.href}
          class="text-gray-700 hover:text-zing-yellow font-medium transition-colors"
          aria-label={link.ariaLabel}
        >
          {link.label}
        </a>
      {/each}
    </nav>

    <!-- Contact & Social -->
    <div class="flex flex-col items-center lg:items-end gap-3">
      <p class="text-sm text-gray-700 flex items-center gap-2">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="w-4 h-4 text-gray-700"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
          stroke-width="2"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            d="M12 21s-6-5.686-6-10a6 6 0 1112 0c0 4.314-6 10-6 10z"
          />
          <circle cx="12" cy="11" r="2.5" fill="currentColor" />
        </svg>
        {contactInfo.location}
      </p>
      <a
        href={contactInfo.whatsappLink}
        target="_blank"
        rel="noopener noreferrer"
        class="text-sm text-black hover:text-zing-yellow transition-colors flex items-center gap-2"
        aria-label="Contact us on WhatsApp"
      >
        <svg
          xmlns="http://www.w3.org/2000/svg"
          class="w-4 h-4 text-green-600"
          fill="currentColor"
          viewBox="0 0 24 24"
        >
          <path
            d="M16.2 13.3c-.3-.1-1.6-.8-1.8-.9-.3-.1-.5-.1-.7.1-.2.3-.8.9-.9 1.1-.2.1-.3.2-.6.1s-1.3-.5-2.5-1.5c-.9-.8-1.5-1.7-1.7-2-.2-.3 0-.5.1-.6.1-.1.3-.3.4-.5.1-.1.1-.3.2-.5.1-.1 0-.4-.1-.5-.1-.1-.7-1.7-.9-2.3-.3-.5-.5-.5-.7-.5h-.6c-.2 0-.5.1-.7.3-.3.3-1 1-1 2.4s1 2.8 1.1 3c.1.2 2 3.1 4.8 4.4 1.8.8 2.5.9 3 .8.5 0 1.5-.6 1.7-1.1.2-.5.2-1 .1-1.1-.1-.2-.3-.3-.6-.4z"
          />
          <path
            d="M20 3.9C18.2 2.1 15.7 1 13 1 7.5 1 3 5.5 3 11c0 1.9.5 3.7 1.5 5.3L3 23l6.8-1.8c1.5.8 3.2 1.2 4.9 1.2 5.5 0 10-4.5 10-10 .1-2.7-1-5.2-2.7-7zM13 20c-1.5 0-3-.4-4.3-1.1l-.3-.1-4.1 1.1 1.1-4-.2-.3C4.5 14.2 4 12.6 4 11c0-4.9 4-9 9-9 2.4 0 4.6.9 6.3 2.6 1.7 1.7 2.6 3.9 2.6 6.3 0 4.9-4.1 9.1-9 9.1z"
          />
        </svg>
        WhatsApp: {contactInfo.whatsapp}
      </a>

      <!-- Social Icons -->
      <div class="flex justify-center lg:justify-end gap-4 pt-2">
        {#each socialLinks as social}
          <a
            href={social.href}
            target="_blank"
            rel="noopener noreferrer"
            class="text-gray-700 hover:text-zing-yellow transition-colors"
            aria-label={"Visit our " + social.name}
          >
            {#if social.icon === "instagram"}
              <svg
                xmlns="http://www.w3.org/2000/svg"
                class="w-5 h-5"
                fill="none"
                viewBox="0 0 24 24"
                stroke="currentColor"
                stroke-width="1.6"
              >
                <rect width="18" height="18" x="3" y="3" rx="4" ry="4"></rect>
                <path d="M16 11.37A4 4 0 1112.63 8 4 4 0 0116 11.37z"></path>
                <circle cx="17.5" cy="6.5" r="1.25" fill="currentColor"
                ></circle>
              </svg>
            {/if}
          </a>
        {/each}
      </div>
    </div>
  </div>

  <div
    class="mt-12 text-center text-xs text-gray-500 space-y-1 border-t border-gray-100 pt-6"
  >
    <p>&copy; {currentYear} Zing Healthy Treats. All rights reserved.</p>
    <p class="text-gray-400 flex items-center justify-center gap-1">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="w-3.5 h-3.5 text-red-500"
        fill="currentColor"
        viewBox="0 0 24 24"
      >
        <path
          d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09A6.254 6.254 0 0116.5 3C19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"
        />
      </svg>
      Made in Nairobi
    </p>
  </div>
</footer>

<style>
  a {
    -webkit-tap-highlight-color: transparent;
    transition: color 150ms ease-in-out;
  }
</style>
