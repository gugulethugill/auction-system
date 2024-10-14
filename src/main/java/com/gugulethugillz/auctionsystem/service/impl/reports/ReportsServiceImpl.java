package com.gugulethugillz.auctionsystem.service.impl.reports;

import com.gugulethugillz.auctionsystem.forms.reports.AssetReportCriteriaForm;
import com.gugulethugillz.auctionsystem.forms.reports.PaymentReportCriteriaForm;
import com.gugulethugillz.auctionsystem.projections.AssetReportEntry;
import com.gugulethugillz.auctionsystem.projections.PaymentReportEntry;
import com.gugulethugillz.auctionsystem.repository.AssetRepository;
import com.gugulethugillz.auctionsystem.repository.PaymentRequestRepository;
import com.gugulethugillz.auctionsystem.service.ifaces.reports.ReportsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportsServiceImpl implements ReportsService {
    private final PaymentRequestRepository paymentRequestRepository;
    private final AssetRepository assetRepository;

    @Override
    public List<PaymentReportEntry> getPaymentReportEntries(PaymentReportCriteriaForm criteriaForm) {
        final OffsetDateTime start = criteriaForm.getStartDate().atOffset(ZoneOffset.UTC);
        final OffsetDateTime end = criteriaForm.getEndDate().atOffset(ZoneOffset.UTC);
        final boolean useCreationDate = criteriaForm.isUseCreationDate();
        if(useCreationDate) {
            return paymentRequestRepository.getPaymentReportEntries(start, end);
        } else {
            return paymentRequestRepository.getPaymentReportEntriesByPaymentDate(start, end);
        }
    }

    @Override
    public List<AssetReportEntry> getAssetReportEntries(AssetReportCriteriaForm criteriaForm) {
        final OffsetDateTime start = criteriaForm.getStartDate().atOffset(ZoneOffset.UTC);
        final OffsetDateTime end = criteriaForm.getEndDate().atOffset(ZoneOffset.UTC);
        return assetRepository.getAssetReportEntries(start, end);
    }
}
