package com.example.granary_backend.infrastructure.web.mpesa.exception;

public class MpesaException extends RuntimeException {
    public MpesaException(String message) {
        super(message);
    }

    public MpesaException(String message, Throwable cause) {
        super(message, cause);
    }
}
