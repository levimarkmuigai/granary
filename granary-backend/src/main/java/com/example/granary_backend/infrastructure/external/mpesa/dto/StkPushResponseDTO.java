package com.example.granary_backend.infrastructure.external.mpesa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StkPushResponseDTO(

                @JsonProperty("OriginatorConversionID") String originatorConversationID,

                @JsonProperty("ConversationId") String conversationId,

                @JsonProperty("ResponseCode") String responseCode,

                @JsonProperty("ResponseDescription") String responseDescription

) {
}
