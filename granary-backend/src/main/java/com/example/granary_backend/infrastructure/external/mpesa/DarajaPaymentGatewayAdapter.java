package com.example.granary_backend.infrastructure.external.mpesa;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.granary_backend.domain.model.value.MpesaTransactionId;
import com.example.granary_backend.domain.model.value.OrderId;
import com.example.granary_backend.domain.model.value.PaymentResult;
import com.example.granary_backend.domain.port.PaymentGateway;
import com.example.granary_backend.infrastructure.external.mapping.DarajaPaymentMapper;
import com.example.granary_backend.infrastructure.external.mpesa.configuration.MpesaConfig;
import com.example.granary_backend.infrastructure.external.mpesa.dto.StkCallbackDTO;
import com.example.granary_backend.infrastructure.external.mpesa.dto.StkPushRequestDTO;
import com.example.granary_backend.infrastructure.external.mpesa.dto.StkPushResponseDTO;

@Component
public class DarajaPaymentGatewayAdapter implements PaymentGateway {
    private static final Logger logger = LoggerFactory.getLogger(DarajaPaymentGatewayAdapter.class);
    private static final ZoneId MPESA_ZONE = ZoneId.of("Africa/Nairobi");
    private static final DateTimeFormatter MPESA_TIMESTAMP_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private final MpesaConfig mpesaConfig;
    private final DarajaHttpClient darajaHttpClient;
    private final DarajaPaymentMapper paymentMapper;

    public DarajaPaymentGatewayAdapter(
        MpesaConfig mpesaConfig, 
        DarajaHttpClient darajaHttpClient,
        DarajaPaymentMapper paymentMapper) {
        this.darajaHttpClient = darajaHttpClient;
        this.mpesaConfig = mpesaConfig;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public String initiateStkPush(OrderId orderId, int amountShillings, String phoneNumber) {
        String shortCode = mpesaConfig.getCredentials().getShortCode();
        String timestamp = generateTimestamp();
        String passKey = mpesaConfig.getCredentials().getPassKey();

        String password = generatePassword(shortCode, passKey, timestamp);

        StkPushRequestDTO requestBody = new StkPushRequestDTO(
                shortCode,
                password,
                timestamp,
                "CustomerPayBillOnline",
                String.valueOf(amountShillings),
                phoneNumber,
                shortCode,
                phoneNumber,
                mpesaConfig.getApi().getResultUrl(),
                orderId.toString(),
                "Granary Order Payment");

        StkPushResponseDTO response = null;
        try {
            response = darajaHttpClient.sendStkPush(requestBody);
        } catch (Exception ex) {
            logger.error("Failed to send STK Push for orderId {}: {}", orderId, ex.getMessage(), ex);
            throw new RuntimeException("Failed to initiate STK Push: " + ex.getMessage(), ex);
        }

        if (response == null || response.checkoutRequestID() == null || response.checkoutRequestID().isEmpty()) {
            logger.error("STK Push response or checkoutRequestID is null/empty for orderId {}. Response: {}", orderId, response);
            throw new RuntimeException("Invalid STK Push response: missing checkoutRequestID");
        }

        return response.checkoutRequestID();
    }

    @Override
    public PaymentResult confirmTransaction(MpesaTransactionId transactionId) {
       throw new UnsupportedOperationException("M-Pesa transaction query is not yet implemented.");
    }

    public PaymentResult handleStkPushCallback(StkCallbackDTO callbackDTO) {
        PaymentResult result = paymentMapper.mapStkCallBackToPayementResult(callbackDTO);
        
        return result;
    }

    private String generateTimestamp() {
        return ZonedDateTime.now(MPESA_ZONE).format(MPESA_TIMESTAMP_FORMATTER);
    }

    private String generatePassword(String shortCode, String passKey, String timestamp) {
        String unencodedPassword = shortCode + passKey + timestamp;

    return Base64.getEncoder().encodeToString(unencodedPassword.getBytes(StandardCharsets.UTF_8));
    }

}
