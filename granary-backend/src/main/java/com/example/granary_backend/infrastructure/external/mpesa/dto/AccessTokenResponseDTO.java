package com.example.granary_backend.infrastructure.external.mpesa.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccessTokenResponseDTO(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("expire_in") String expiresIn) {
}