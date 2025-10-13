package com.example.granary_backend.infrastructure.web.mpesa.exception;

public class MpesaProcessingException extends RuntimeException {

    public MpesaProcessingException(String message) {
        super(message);
    }

    public MpesaProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
