package com.example.granary_backend.application.util;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.granary_backend.application.exception.InvalidCommandException;

public final class CommandUtils {

  private CommandUtils() {}

  public static <C, ID> Set<ID> extractUniqueIds(
    Collection<C> commands,
    Function<C, ID> idExtractor
  ) {

    Objects.requireNonNull(commands, "Command collection cannot be null.");
    Objects.requireNonNull(idExtractor, "ID extractor function cannot be null.");

    return commands.stream()
    .map(idExtractor)
    .filter(Objects::nonNull)
    .collect(Collectors.toSet());
  }

  public static <T extends Enum<T>> T parseEnum(
    String enumString,
    Class<T> enumClass,
    String fieldName
  ) {

    Objects.requireNonNull(enumString, fieldName + " string cannot be null.");

    try {
    return Enum.valueOf(enumClass, enumString.trim().toUpperCase());
    } catch (IllegalArgumentException e) {

      Map<String, String> errors = Map.of(
      fieldName,
      "Invalid value provided. Must be on of: " + List.of(enumClass.getEnumConstants())
      );

      throw new InvalidCommandException(
        "Failed to parse required enum value",
        errors);
    }
  }
}
