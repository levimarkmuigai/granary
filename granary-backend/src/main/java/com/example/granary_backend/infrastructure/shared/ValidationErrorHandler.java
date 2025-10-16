package com.example.granary_backend.infrastructure.shared;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ValidationErrorHandler {

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiErrorResponse> handleValidationExceptions(
                        MethodArgumentNotValidException ex,
                        WebRequest request) {
                List<ApiErrorResponse.ErrorDetail> details = ex.getBindingResult().getAllErrors().stream()
                                .map(error -> {
                                        String fieldName = (error instanceof FieldError)
                                                        ? ((FieldError) error).getField()
                                                        : error.getObjectName();
                                        String errorMessage = error.getDefaultMessage();
                                        return new ApiErrorResponse.ErrorDetail(fieldName, errorMessage);
                                }).toList();

                return ResponseFactory.createErrorResponse(
                                HttpStatus.BAD_REQUEST,
                                "ValidationError",
                                "Validation failed for one or more fields",
                                request,
                                details);
        }
}
