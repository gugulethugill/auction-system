package com.gugulethugillz.auctionsystem.service;

import com.gugulethugillz.auctionsystem.payment.repository.PaymentRequestRepository;
import com.gugulethugillz.auctionsystem.report.service.impl.ReportsServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
@ExtendWith(SpringExtension.class)
public class ReportsServiceTest {
    @InjectMocks
    private ReportsServiceImpl reportsService;

    @Mock
    private PaymentRequestRepository paymentRequestRepository;

    @BeforeEach
    public void init() {

    }

    @AfterEach
    public void cleanUp() {

    }

    @Test
    public void givenAnId_whenFindPerson_shouldReturnPerson() throws Exception {

    }
}
