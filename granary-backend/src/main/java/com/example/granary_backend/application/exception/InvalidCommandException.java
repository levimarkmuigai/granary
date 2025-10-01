package com.example.granary_backend.application.exception;

import java.util.Collections;
import java.util.Map;

public class InvalidCommandException extends RuntimeException {

  private final Map<String, String> errors;

  public InvalidCommandException(String message, Map<String, String> errors) {
    super(message);
    this.errors = Collections.unmodifiableMap(errors);
  }

  public InvalidCommandException(String message) {
    this(message, Collections.emptyMap());
  }

  public Map<String, String> getErrors() {
    return errors;
  }
}
