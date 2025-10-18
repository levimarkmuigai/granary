export const checkoutPageConfig = {
  title: "Checkout",
  meta: {
    title: "Checkout | Granary",
  },
  orderSummary: {
    title: "Order Summary",
    totalLabel: "Total",
    totalPlaceholder: "Price determined by backend",
  },
  deliveryDetails: {
    title: "Delivery Details",
    fields: {
      name: "Name",
      email: "Email",
      phone: "Phone",
      address: "Address",
    },
    submitButton: {
      idle: "Place Order & Pay",
      loading: "Processing...",
    },
  },
  success: {
    title: "✅ Order Placed Successfully!",
    message: "Your order number is:",
    subtext: "We're preparing your goods for delivery.",
    button: "Start a New Order",
  },
  error: {
    title: "🛑 Checkout Failed",
    message: "We ran into a problem while placing your order.",
    detailPrefix: "Error Detail:",
    fallback: "An unknown error occurred.",
    button: "Try Again",
  },
  alerts: {
    missingFields: "Please fill in all customer details.",
  },
};
