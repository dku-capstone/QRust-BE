package com.qrust.report.application;

import com.qrust.report.domain.entity.PhishingReport;
import com.qrust.report.domain.entity.ReportUrl;
import com.qrust.report.domain.service.PhishingReportService;
import com.qrust.report.domain.service.ReportUrlService;
import com.qrust.report.dto.PhishingReportUpsertRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReportFacade {
    private final PhishingReportService phishingReportService;
    private final ReportUrlService reportUrlService;

    @Transactional
    public void registerReport(PhishingReportUpsertRequest request, Long userId) {
        ReportUrl reportUrl = reportUrlService.upsert(request.url());
        phishingReportService.save(request, reportUrl, userId);
    }

    @Transactional
    public void approveReport(Long reportId) {
        PhishingReport report = phishingReportService.approveReport(reportId);
        reportUrlService.increaseReportCount(report.getReportUrl().getUrl());
    }

    @Transactional
    public void rejectReport(Long reportId) {
        phishingReportService.rejectReport(reportId);
    }
}
