package com.gugulethugillz.auctionsystem.repository;

import com.gugulethugillz.auctionsystem.asset.model.Asset;
import com.gugulethugillz.auctionsystem.common.enums.EntityStatus;
import com.gugulethugillz.auctionsystem.common.enums.PaymentMethod;
import com.gugulethugillz.auctionsystem.common.enums.PaymentStatus;
import com.gugulethugillz.auctionsystem.payment.model.PaymentRequest;
import com.gugulethugillz.auctionsystem.status.StatusHistory;
import com.gugulethugillz.auctionsystem.status.StatusHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertSame;
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class StatusHistoryRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StatusHistoryRepository statusHistoryRepository;

    private Asset asset;
    private PaymentRequest paymentRequest;
    private StatusHistory statusHistory;

    @BeforeEach
    public void init() {
        asset = getAsset(1L);
        entityManager.persist(asset);

        paymentRequest = getPaymentRequest(1L);
        entityManager.persist(paymentRequest);

        statusHistory = getStatusHistory(1L);
        entityManager.persist(statusHistory);

    }

    @AfterEach
    public void destroy() {
        entityManager.remove(paymentRequest);
        entityManager.remove(asset);
        entityManager.remove(statusHistory);
        paymentRequest = null;
        asset = null;
        statusHistory = null;
    }

    @Test
    public void givenAStatusHistoryEntry_whenPersist_thenShouldReturnPersistedStatusHistory() throws Exception {
        StatusHistory statusHistory = getStatusHistory(2L);
        StatusHistory persisted = statusHistoryRepository.save(statusHistory);
        assertThat(persisted).isNotNull();
        assertAll(
                () -> assertSame(persisted.getId(), statusHistory.getId())
        );
    }

    private Asset getAsset(Long id) {
        Asset asset = new Asset();
        asset.setName("test");
        asset.setDescription("test");
        asset.setBidStartDate(OffsetDateTime.now());
        asset.setBidEndDate(OffsetDateTime.now());
        asset.setBidStartPrice(new BigDecimal("0"));
        asset.setId(id);
        asset.setDateCreated(OffsetDateTime.now());
        asset.setLastUpdated(OffsetDateTime.now());
        asset.setStatus(EntityStatus.ACTIVE);
        asset.setCreatedBy("test");
        asset.setLastUpdatedBy("test");
        return asset;
    }

    private PaymentRequest getPaymentRequest(Long id) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setId(id);
        paymentRequest.setValue(BigDecimal.TEN);
        paymentRequest.setDate(OffsetDateTime.now());
        paymentRequest.setPaymentMethod(PaymentMethod.ECOCASH);
        paymentRequest.setPaymentStatus(PaymentStatus.PENDING);
        paymentRequest.setPollUrl("");
        paymentRequest.setTransactionStatus("");
        paymentRequest.setItem("");
        paymentRequest.setMobileNumber("");
        paymentRequest.setDescription("");
        paymentRequest.setMerchantReference("");
        paymentRequest.setPaynowReference("");
        paymentRequest.setSystemReference("");
        paymentRequest.setAsset(getAsset(id));
        paymentRequest.setDateCreated(OffsetDateTime.now());
        paymentRequest.setDate(OffsetDateTime.now());
        paymentRequest.setLastUpdated(OffsetDateTime.now());
        paymentRequest.setStatus(EntityStatus.ACTIVE);
        paymentRequest.setCreatedBy("test");
        paymentRequest.setLastUpdatedBy("test");
        return paymentRequest;
    }

    private StatusHistory getStatusHistory(Long id) {
        StatusHistory statusHistory = new StatusHistory();
        statusHistory.setEntityStatus("Test");
        statusHistory.setOtherStatus("Another Status");
        statusHistory.setAsset(getAsset(id));
        statusHistory.setPaymentRequest(getPaymentRequest(id));
        statusHistory.setId(id);
        statusHistory.setDateCreated(OffsetDateTime.now());
        statusHistory.setLastUpdated(OffsetDateTime.now());
        statusHistory.setStatus(EntityStatus.ACTIVE);
        statusHistory.setCreatedBy("gugulethu");
        statusHistory.setLastUpdatedBy("gugulethu");
        return statusHistory;
    }
}
