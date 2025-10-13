package com.example.granary_backend.infrastructure.web.mpesa;

public record MpesaCallbackResponse(
        String ResultCode,
        String ResultDesc) {

    public static MpesaCallbackResponse success() {
        return new MpesaCallbackResponse("0", "Accepted");
    }
}
