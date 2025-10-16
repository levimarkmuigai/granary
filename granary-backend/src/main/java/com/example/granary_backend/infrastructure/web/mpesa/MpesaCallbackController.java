package com.example.granary_backend.infrastructure.web.mpesa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.granary_backend.infrastructure.external.mpesa.dto.StkCallbackDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/mpesa")
public class MpesaCallbackController {

    private static final Logger log = LoggerFactory
            .getLogger(MpesaCallbackController.class);
    private final MpesaCallbackHandler callbackHandler;

    public MpesaCallbackController(MpesaCallbackHandler callbackHandler) {
        this.callbackHandler = callbackHandler;
    }

    @PostMapping("/stk-callback")
    public ResponseEntity<Void> handleStkPushCallback(
            @RequestBody @Valid StkCallbackDTO callbackDTO) {
        log.info("Received M-pesa Callback for ConversionID: {}",
                callbackDTO.result().conversationID());

        try {
            callbackHandler.handle(callbackDTO);
        } catch (Exception e) {
            log.error("Error processing M-Pesa callback internally. Returning 200 OK to M-Pesa to acknowledge receipt.",
                    e);
        }

        return ResponseEntity.ok().build();
    }

}
