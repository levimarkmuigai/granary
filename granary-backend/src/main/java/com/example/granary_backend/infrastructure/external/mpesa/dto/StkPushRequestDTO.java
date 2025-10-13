package com.example.granary_backend.infrastructure.external.mpesa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StkPushRequestDTO(
        @JsonProperty("BusinessShortCode") String businessShortCode,

        @JsonProperty("Password") String password,

        @JsonProperty("Timestamp") String timestamp,

        @JsonProperty("TransactionType") String transactionType,

        @JsonProperty("Amount") String amount,

        @JsonProperty("PartyA") String partyA,

        @JsonProperty("PartyB") String partyB,

        @JsonProperty("PhoneNumber") String phoneNumber,

        @JsonProperty("CallBackURL") String callBackURL,

        @JsonProperty("AccountReference") String accountReference,

        @JsonProperty("TransactionDesc") String transactionDesc) {
}
