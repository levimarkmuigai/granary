package com.example.granary_backend.application.validation;

import java.util.Map;
import java.util.regex.Pattern;

public final class ValidationRules {

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

  private static final Pattern PHONE_PATTERN =
      Pattern.compile("^\\+?254[0-9]{9}$");

  private ValidationRules() {

  }

  public static void checkRequired(Map<String, String> errors, String fieldName, String value) {
    if(value == null || value.trim().isEmpty()) {
      errors.put(fieldName, fieldName + " is required.");
    }
  }

  public static void checkPositiveNumber(Map<String, String> errors, String fieldName, Number value) {
    if(value == null || value.doubleValue() <= 0) {
      errors.put(fieldName, fieldName + " must be a positive number.");
    }
  }

  public static void checkNonNegativeNumber(Map<String, String> errors, String fieldName, Number value) {
    if(value == null || value.doubleValue() < 0) {
      errors.put(fieldName, fieldName + " must be a non-negative number.");
    }
  }

  public static void checkEmailFormat(Map<String, String> errors, String fieldName, String value) {
    if(value != null && !EMAIL_PATTERN.matcher(value).matches()) {
      errors.put(fieldName, "Invalid email format for " + fieldName + ".");
    }
  }

  public static void checkPhoneFormat(Map<String, String> errors, String fieldName, String value) {

    if( value == null || value.trim().isEmpty()) {
      return;
    }

    String cleanedValue = value.replaceAll("[\\s()\\-]", "");
    if(!PHONE_PATTERN.matcher(cleanedValue).matches()) {
      errors.put(fieldName, "Invalid phone number format for " + fieldName + ".");
    }
  }
}
