package com.example.granary_backend.infrastructure.external.mapping;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.granary_backend.domain.model.Order.PaymentStatus;
import com.example.granary_backend.domain.model.value.MpesaTransactionId;
import com.example.granary_backend.domain.model.value.PaymentResult;
import com.example.granary_backend.infrastructure.external.mpesa.dto.StkCallbackDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DarajaPaymentMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public PaymentResult mapStkCallBackToPayementResult(StkCallbackDTO callbackDTO) {
        Integer resultCode = callbackDTO.result().resultCode();
        PaymentStatus status;

        if (resultCode == 0) {
            status = PaymentStatus.SUCCESS;
        } else if (resultCode == 1032) {
            status = PaymentStatus.CANCELLED;
        } else {
            status = PaymentStatus.FAILED;
        }

        String transactionIdString = null;
        String apiResponseJson = null;

        if (status == PaymentStatus.SUCCESS) {
            Map<String, String> paramsMap = callbackDTO.result().resultParameters()
                    .resultParameter().stream()
                    .collect(
                            Collectors.toMap(
                                    StkCallbackDTO.CallbackParameter::key,
                                    StkCallbackDTO.CallbackParameter::value));

            transactionIdString = paramsMap.get("MpesaRecieptNumber");
        }

        try {
            apiResponseJson = objectMapper.writeValueAsString(callbackDTO);
        } catch (Exception e) {
            apiResponseJson = "Error converting callback DTO to JSON: " + e.getMessage();
        }

        MpesaTransactionId mpesaTransactionId = (transactionIdString != null)
                ? new MpesaTransactionId(transactionIdString)
                : null;

        return new PaymentResult(
                status,
                mpesaTransactionId,
                apiResponseJson);
    }
}
