package com.example.granary_backend.infrastructure.external.mpesa;

import org.springframework.stereotype.Component;

import com.example.granary_backend.domain.model.Order.PaymentStatus;
import com.example.granary_backend.domain.model.value.MpesaTransactionId;
import com.example.granary_backend.domain.model.value.OrderId;
import com.example.granary_backend.domain.model.value.PaymentResult;
import com.example.granary_backend.domain.port.PaymentGateway;

@Component
public class DarajaPaymentGatewayAdapter implements PaymentGateway {

    @Override
    public String initiateStkPush(OrderId orderId, int amountShillings, String phoneNumber) {
        return "ToDO";
    }

    @Override
    public PaymentResult confirmTransaction(MpesaTransactionId transactionId) {
        String mpesaIdString = "LK12345ABC";
        String apiResponse = "{... JSON response ...}";

        MpesaTransactionId mpesaTransactionId = new MpesaTransactionId(mpesaIdString);

        return new PaymentResult(
                PaymentStatus.SUCCESS,
                mpesaTransactionId,
                apiResponse);
    }

}
