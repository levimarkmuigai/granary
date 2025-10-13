package com.example.granary_backend.infrastructure.external.mpesa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StkPushResponseDTO(

        @JsonProperty("MerchantRequestID") String merchantRequestID,

        @JsonProperty("CheckoutRequestID") String checkoutRequestID,

        @JsonProperty("ResponseCode") String responseCode,

        @JsonProperty("ResponseDescription") String responseDescription,

        @JsonProperty("CustomerMessage") String customerMessage,

        @JsonProperty("OriginatorConversationID") String originatorConversationID,

        @JsonProperty("ConversationID") String conversationID

) {
}
