package com.gugulethugillz.auctionsystem.service.ifaces.payments;

import com.gugulethugillz.auctionsystem.common.GenericResponse;
import com.gugulethugillz.auctionsystem.model.PaymentRequest;

public interface PaynowProcessor {
    GenericResponse<PaymentRequest> handlePayment(PaymentRequest paymentRequest);
    void handlePollRequest();
}
