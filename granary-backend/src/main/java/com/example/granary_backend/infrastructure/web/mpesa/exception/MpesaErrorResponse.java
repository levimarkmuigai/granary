package com.example.granary_backend.infrastructure.web.mpesa.exception;

import org.springframework.http.HttpStatus;

public record MpesaErrorResponse(
                HttpStatus status,
                int statusCode,
                String message) {
}
