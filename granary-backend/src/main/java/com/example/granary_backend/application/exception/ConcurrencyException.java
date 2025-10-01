package com.example.granary_backend.application.exception;

public class ConcurrencyException extends RuntimeException{

  private static final String DEFAULT_MESSAGE =
  "The data you are trying to modify is stale. Please refresh and try again.";

  public ConcurrencyException() {
    super(DEFAULT_MESSAGE);
  }

  public ConcurrencyException(String message, Throwable cause) {
    super(message, cause);
  }
}
