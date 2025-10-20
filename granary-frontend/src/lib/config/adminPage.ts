export const adminPageConfig = {
  sidebar: {
    dashboard: "Dashboard",
    orders: "Orders",
    products: "Products",
  },

  dashboard: {
    cards: [
      { label: "New Orders", key: "newOrders", color: "#3B82F6" }, 
      { label: "Packaging Orders", key: "packagingOrders", color: "#FACC15" },
      { label: "Ready Orders", key: "readyOrders", color: "#22C55E" }, 
      { label: "Total Products", key: "totalProducts", color: "#6B7280" }, 
    ],
    visualEnhancements: {
      cardShadow: "lg",
      cardBorderRadius: "xl",
      hoverScale: 1.03,
      transition: "transform 150ms ease-in-out",
    },
  },

  ordersTable: {
    statusColors: {
      New: "#3B82F6",
      Packaging: "#FACC15",
      Ready: "#22C55E",
      Delivered: "#6B7280",
    },
    actionLabels: {
      markPackaging: "Mark Packaging",
      markReady: "Mark Ready",
      markDelivered: "Mark Delivered",
    },
    visualEnhancements: {
      rowHoverColor: "#F3F4F6", 
      statusBadgeRadius: "md",
    },
  },

  productsTable: {
    lowStockBadge: {
      text: "Low Stock",
      bgColor: "#EF4444", 
      textColor: "#FFFFFF",
    },
    actionLabels: {
      edit: "Edit",
      delete: "Delete",
    },
    visualEnhancements: {
      rowHoverColor: "#F9FAFB",
      badgeShadow: "sm",
    },
  },

  productForm: {
    fields: [
      { name: "name", label: "Product Name", type: "text" },
      { name: "priceCents", label: "Price (KES)", type: "number" },
      { name: "stockQuantity", label: "Stock Quantity", type: "number" },
      {
        name: "imageFile",
        label: "Product Image",
        type: "file",
        accept: "image/*",
        preview: true, 
      },
      { name: "lowStockAlert", label: "Low Stock Threshold", type: "number" },
    ],
    submitLabel: "Save Product",
    visualEnhancements: {
      cardShadow: "md",
      cardBorderRadius: "xl",
      fieldFocusColor: "#3B82F6",
      previewBorder: "2px dashed #9CA3AF",
      hoverScale: 1.02,
      transition: "all 150ms ease-in-out",
    },
  },

  loginPage: {
    title: "Admin Login",
    usernameLabel: "Username",
    passwordLabel: "Password",
    submitLabel: "Login",
    errorMessage: "Invalid credentials",
    visualEnhancements: {
      cardShadow: "lg",
      cardBorderRadius: "xl",
      focusColor: "#3B82F6",
      subtleAnimation: "scale 1.01 on hover",
    },
  },
};
