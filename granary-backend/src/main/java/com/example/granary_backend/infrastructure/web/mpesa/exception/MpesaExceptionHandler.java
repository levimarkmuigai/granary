package com.example.granary_backend.infrastructure.web.mpesa.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MpesaExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(MpesaExceptionHandler.class);

    @ExceptionHandler(MpesaProcessingException.class)
    public ResponseEntity<MpesaErrorResponse> handleMpesaProcessingException(MpesaProcessingException ex) {
        log.warn("M-pesa processing error: {}", ex.getMessage(), ex);

        MpesaErrorResponse errorResponse = new MpesaErrorResponse(
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value(),
                "M-Pesa processing failed due to invalid data or request.");

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MpesaException.class)
    public ResponseEntity<MpesaErrorResponse> handleMpesaException(MpesaException ex) {
        log.error("Internal Error during M-pesa processing", ex);

        MpesaErrorResponse errorResponse = new MpesaErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An error occurred while processing M-pesa request.");

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
