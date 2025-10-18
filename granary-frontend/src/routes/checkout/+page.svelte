<script lang="ts">
  import CheckoutForm from "$lib/components/CheckoutForm.svelte";
  import { services } from "$lib/services";
  import {
    OrderCreationStore,
    resetOrderCreationState,
  } from "$lib/application/StateStore";
  import type { CreateOrderCommand, OrderLineCommand } from "$lib/domain";
  import { onDestroy } from "svelte";

  export let orderLines: OrderLineCommand[] = [];

  // Reactive store values
  $: status = $OrderCreationStore.status;
  $: orderId = $OrderCreationStore.orderId;
  $: errorMessage = $OrderCreationStore.errorMessage;

  const handlePlaceOrder = async (customerDetails: {
    name: string;
    email: string;
    phone: string;
    address: string;
  }) => {
    if (!orderLines.length) {
      console.warn("No order lines provided");
      return;
    }

    const command: CreateOrderCommand = { orderLines, customerDetails };

    try {
      await services.orderService.createOrder(command);
    } catch (err) {
      console.error("Unhandled error during checkout:", err);
    }
  };

  onDestroy(resetOrderCreationState);
</script>
