package com.example.granary_backend.infrastructure.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.example.granary_backend.application.exception.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalStateException(
            IllegalStateException ex,
            WebRequest request) {
        return ResponseFactory.createErrorResponse(
                HttpStatus.CONFLICT,
                "BusinessRuleViolation",
                ex.getMessage(),
                request,
                null);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleEntityNotFoundException(
            EntityNotFoundException ex,
            WebRequest request) {
        return ResponseFactory.createErrorResponse(
                HttpStatus.NOT_FOUND,
                "ResourceNotFound",
                ex.getMessage(),
                request,
                null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("Unhandled API Exception: {}", ex.getMessage(), ex);

        return ResponseFactory.createErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "InternalServerError",
                "An unexpected error occurred. Please try again later.",
                request,
                null);
    }
}
