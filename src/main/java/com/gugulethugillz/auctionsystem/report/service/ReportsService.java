package com.gugulethugillz.auctionsystem.report.service;


import com.gugulethugillz.auctionsystem.report.form.AssetReportCriteriaForm;
import com.gugulethugillz.auctionsystem.report.form.PaymentReportCriteriaForm;
import com.gugulethugillz.auctionsystem.report.projections.AssetReportEntry;
import com.gugulethugillz.auctionsystem.report.projections.PaymentReportEntry;

import java.util.List;

public interface ReportsService {
    List<PaymentReportEntry> getPaymentReportEntries(PaymentReportCriteriaForm criteriaForm);
    List<AssetReportEntry> getAssetReportEntries(AssetReportCriteriaForm criteriaForm);
}
