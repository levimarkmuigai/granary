package com.example.granary_backend.application.exception;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException(String entityType, Object entityId) {
    super(String.format("%s with ID [%s] was not found.",
        entityType,
        entityId != null ? entityId.toString() : "null"));
  }

  public EntityNotFoundException(String message) {
    super(message);
  }
}
