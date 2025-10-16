package com.example.granary_backend.infrastructure.shared;

import java.time.Instant;
import java.util.List;

public record ApiErrorResponse(
        String type,
        String message,
        Instant timeStamp,
        int status,
        String path,
        List<ErrorDetail> details) {

    public record ErrorDetail(
            String field,
            String error) {
    }
}
