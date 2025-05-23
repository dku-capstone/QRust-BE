package com.qrust.report.application;

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
    public void upsertReport(PhishingReportUpsertRequest request) {
        ReportUrl reportUrl = reportUrlService.upsert(request.url());
        reportUrlService.increaseReportCount(request.url());
        phishingReportService.save(request, reportUrl);
    }
}
