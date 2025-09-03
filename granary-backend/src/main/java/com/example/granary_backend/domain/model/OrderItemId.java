package com.example.granary_backend.domain.model;

import java.util.UUID;

/**
 * Value object representing an order item's
 * unique  identifier
 * Immutable and guarantees a valid UUID
 **/
public final class OrderItemId {
    private final UUID value;

    // Constructor that ensures every order item has an ID
    private OrderItemId(UUID value){
        if(value == null) {
            throw new IllegalArgumentException("OrderItemId was expected but was null!");
        }

        this.value = value;
    }

    /**
     * Generates  a new unique OrderItemId
     * */
    public static OrderItemId createNew() {
        return new OrderItemId( UUID.randomUUID() );
    }

    /**
     * Rebuilds an existing OrderItemId from its string
     * representation
     * */
    public static OrderItemId fromString(String id) {
        return new OrderItemId(UUID.fromString(id));
    }

    public static OrderItemId from(UUID id) {
        return new OrderItemId(id);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if(!(o instanceof OrderItemId)) return false;
        OrderItemId other = (OrderItemId) o;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    /**
     * Returns the UUID value for interaction
     * with other layers
     **/
    public UUID getValue() {
        return value;
    }
}
