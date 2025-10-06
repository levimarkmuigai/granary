package com.example.granary_backend.infrastructure.external.mpesa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record StkCallbackDTO(
        @JsonProperty("Result") ResultBody result) {
    public record ResultBody(
            @JsonProperty("ResultType") Integer resultType,

            @JsonProperty("ResultCode") Integer resultCode,

            @JsonProperty("ResultDesc") String resultDesc,

            @JsonProperty("OriginatorConversationID") String originatorConversationID,

            @JsonProperty("ConversationID") String conversationID,

            @JsonProperty("TransactionID") String transactionID,

            @JsonProperty("ResultParameters") ResultParameters resultParameters) {
    }

    public record ResultParameters(
            @JsonProperty("ResultParameter") List<CallbackParameter> resultParameter) {
    }

    public record CallbackParameter(
            @JsonProperty("Key") String key,

            @JsonProperty("Value") String value) {
    }
}