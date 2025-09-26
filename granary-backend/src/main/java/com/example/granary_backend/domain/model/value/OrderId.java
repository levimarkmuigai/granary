package com.example.granary_backend.domain.model;

import java.util.UUID;

/**
 * Value object representing an order's
 * unique  identifier
 * Immutable and guarantees a valid UUID
 **/
public final class OrderId {
  private final UUID value;

  // Constructor that ensures every order has an ID
  private OrderId(UUID value){
      if(value == null) {
          throw new IllegalArgumentException("OrderId was expected but was null!");
      }

      this.value = value;
  }

  /**
   * Generates  a new unique OrderId
   * */
  public static OrderId createNew() {
      return new OrderId( UUID.randomUUID() );
  }

  /**
    * Rebuilds an existing OrderId from its string
    * representation
    * */
  public static OrderId fromString(String id) {
      return new OrderId(UUID.fromString(id));
  }

  public static OrderId from(UUID id) {
    return new OrderId(id);
  }

  @Override
  public String toString() {
      return value.toString();
  }

  @Override
  public boolean equals(Object o){
      if (this == o) return true;
      if(!(o instanceof OrderId)) return false;
      OrderId other = (OrderId) o;
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
