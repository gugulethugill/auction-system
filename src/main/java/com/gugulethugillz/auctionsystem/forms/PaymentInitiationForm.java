package com.gugulethugillz.auctionsystem.forms;

import com.gugulethugillz.auctionsystem.common.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class PaymentInitiationForm {
    @NotNull(message = "Payment Id is required")
    private Long paymentRequestId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotNull(message = "Mobile number is required")
    private String mobileNumber;
}

