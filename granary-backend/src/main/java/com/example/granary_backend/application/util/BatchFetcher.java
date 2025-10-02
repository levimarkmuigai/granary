package com.example.granary_backend.application.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class BatchFetcher {

  private static final int BATCH_SIZE = 100;

  private BatchFetcher() {
  }

  public static <ID, E> Map<ID, E> fetch(
      List<ID> ids,
      Function<Set<ID>, List<E>> batchFetchFunction,
      Function<E, ID> idMapperFunction) {

    Objects.requireNonNull(ids, "List of IDs must not be null");
    Objects.requireNonNull(batchFetchFunction, "Batch fetch function must not be null");
    Objects.requireNonNull(idMapperFunction, "ID mapper function must not be null");

    Set<ID> uniqueIds = Set.copyOf(ids);

    if (uniqueIds.isEmpty()) {
      return Map.of();
    }

    List<E> entities = batchFetchFunction.apply(uniqueIds);

    return entities.stream()
        .collect(Collectors.toMap(
            idMapperFunction, Function.identity()));
  }
}
