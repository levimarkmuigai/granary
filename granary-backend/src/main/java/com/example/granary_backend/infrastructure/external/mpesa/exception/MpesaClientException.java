package com.example.granary_backend.infrastructure.external.mpesa.exception;

public class MpesaClientException extends RuntimeException {

    public MpesaClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public MpesaClientException(String message) {
        super(message);
    }
}
