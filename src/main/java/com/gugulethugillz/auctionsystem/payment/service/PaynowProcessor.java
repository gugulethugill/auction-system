package com.gugulethugillz.auctionsystem.payment.service;

import com.gugulethugillz.auctionsystem.common.GenericResponse;
import com.gugulethugillz.auctionsystem.payment.model.PaymentRequest;

public interface PaynowProcessor {
    GenericResponse<PaymentRequest> handlePayment(PaymentRequest paymentRequest);
    void handlePollRequest();
}
