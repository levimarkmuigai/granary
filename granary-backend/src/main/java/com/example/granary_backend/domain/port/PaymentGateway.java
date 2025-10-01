package com.example.granary_backend.domain.port;

import com.example.granary_backend.domain.model.value.MpesaTransactionId;
import com.example.granary_backend.domain.model.value.OrderId;
import com.example.granary_backend.domain.model.value.PaymentResult;

public interface PaymentGateway {

  PaymentResult requestPayment(OrderId orderId, int amountShillings, String phoneNumber);

  PaymentResult confirmPayment(MpesaTransactionId transactionId);
}
