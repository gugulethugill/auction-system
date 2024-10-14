package com.gugulethugillz.auctionsystem.payment.service;


import com.gugulethugillz.auctionsystem.common.GenericResponse;
import com.gugulethugillz.auctionsystem.payment.form.PaymentInitiationForm;
import com.gugulethugillz.auctionsystem.payment.model.PaymentRequest;
import com.gugulethugillz.auctionsystem.payment.repository.PaymentRequestRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRequestService {
   PaymentRequestRepository getRepository();
   GenericResponse<PaymentRequest> makePayment(PaymentInitiationForm paymentInitiationForm);
   Optional<PaymentRequest> findById(Long id);
   List<PaymentRequest> getByUsername(String username);
   List<PaymentRequest> getByUserId(Long userId);
   List<PaymentRequest> findAll();
   List<PaymentRequest> findByKeyword(String keyword);

}
