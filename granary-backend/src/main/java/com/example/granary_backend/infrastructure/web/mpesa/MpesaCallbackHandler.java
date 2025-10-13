package com.example.granary_backend.infrastructure.web.mpesa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.granary_backend.infrastructure.external.mpesa.DarajaPaymentGatewayAdapter;
import com.example.granary_backend.infrastructure.external.mpesa.dto.StkCallbackDTO;
import com.example.granary_backend.infrastructure.web.mpesa.exception.MpesaProcessingException;

@Service
public class MpesaCallbackHandler {

    private static final Logger log = LoggerFactory.getLogger(MpesaCallbackHandler.class);
    private final DarajaPaymentGatewayAdapter paymentGatewayAdapter;

    public MpesaCallbackHandler(DarajaPaymentGatewayAdapter paymentGatewayAdapter) {
        this.paymentGatewayAdapter = paymentGatewayAdapter;
    }

    public void handle(StkCallbackDTO callbackDTO) {
        try {
            paymentGatewayAdapter.handleStkPushCallback(callbackDTO);

            log.info("Successfully processed M-pesa callback.");
        } catch (Exception e) {

            log.error("Failed to process M-pesa callback for ConversationID {}: {}",
                    callbackDTO.result().conversationID(), e.getMessage());

            throw new MpesaProcessingException(
                    "Failed to finalize payment confirmation.", e);
        }
    }

}
