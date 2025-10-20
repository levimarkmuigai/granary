<script lang="ts">
  export let stats: { label: string; count: number; color: string }[] = [];
</script>

<div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
  {#each stats as stat}
    <div
      class="p-8 rounded-2xl flex flex-col items-center justify-center aspect-square transition-transform duration-300 hover:scale-[1.03] shadow-[0_0_10px_rgba(0,0,0,0.2)]"
      style="background-color: {stat.color};"
    >
      <div class="text-5xl font-extrabold text-white drop-shadow-sm">
        {stat.count}
      </div>
      <div class="mt-3 text-gray-200 text-sm uppercase tracking-wider">
        {stat.label}
      </div>
    </div>
  {/each}
</div>
