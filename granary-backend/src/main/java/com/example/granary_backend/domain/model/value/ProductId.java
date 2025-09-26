package com.example.granary_backend.domain.model.value;

import java.util.UUID;

/**
 * Value object representing a product's
 * unique  identifier
 * Immutable and guarantees a valid UUID
 **/
public final class ProductId {
  private final UUID value;

  // Constructor that ensures every product has an ID
  private ProductId(UUID value){
      if(value == null) {
          throw new IllegalArgumentException("ProductId was expected but was null!");
      }

      this.value = value;
  }

  /**
   * Generates  a new unique ProductId
   * */
  public static ProductId createNew() {
      return new ProductId( UUID.randomUUID() );
  }

  /**
    * Rebuilds an existing ProductId from its string
    * representation
    * */
  public static ProductId fromString(String id) {
      return new ProductId(UUID.fromString(id));
  }

  public static ProductId from(UUID id) {
    return new ProductId(id);
  }

  @Override
  public String toString() {
      return value.toString();
  }

  @Override
  public boolean equals(Object o){
      if (this == o) return true;
      if(!(o instanceof ProductId)) return false;
      ProductId other = (ProductId) o;
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
