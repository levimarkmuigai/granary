package com.example.granary_backend.infrastructure.external.mpesa.dto;

public record StkPushRequestDTO(
        String InitiatorName,
        String SecurityCredential,
        String CommandId,
        Integer Amount,
        String PartyA,
        String PartyB,
        String Remarks,
        String QueueTimeOutUrl,
        String ResultURL,
        String Occasion) {
}
