package com.example.granary_backend.infrastructure.web.admin.dto;

import java.math.BigDecimal;

public class OrderItemDTO {
    private String productName;
    private Integer quantity;
    private BigDecimal pricePerUnit;

    public OrderItemDTO() {
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }
}
