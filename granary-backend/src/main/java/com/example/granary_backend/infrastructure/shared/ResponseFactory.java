package com.example.granary_backend.infrastructure.shared;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

public class ResponseFactory {

    private ResponseFactory() {
    }

    public static ResponseEntity<ApiErrorResponse> createErrorResponse(
            HttpStatus status,
            String errorType,
            String message,
            WebRequest request,
            List<ApiErrorResponse.ErrorDetail> details) {
        String path = request.getDescription(false).replace("uri=", "");

        ApiErrorResponse body = new ApiErrorResponse(
                errorType,
                message,
                Instant.now(),
                status.value(),
                path,
                details != null ? details : Collections.emptyList());

        return new ResponseEntity<>(body, status);
    }

}
