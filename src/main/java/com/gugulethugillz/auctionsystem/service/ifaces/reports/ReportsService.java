package com.gugulethugillz.auctionsystem.service.ifaces.reports;


import com.gugulethugillz.auctionsystem.forms.reports.AssetReportCriteriaForm;
import com.gugulethugillz.auctionsystem.forms.reports.PaymentReportCriteriaForm;
import com.gugulethugillz.auctionsystem.projections.AssetReportEntry;
import com.gugulethugillz.auctionsystem.projections.PaymentReportEntry;

import java.util.List;

public interface ReportsService {
    List<PaymentReportEntry> getPaymentReportEntries(PaymentReportCriteriaForm criteriaForm);
    List<AssetReportEntry> getAssetReportEntries(AssetReportCriteriaForm criteriaForm);
}
