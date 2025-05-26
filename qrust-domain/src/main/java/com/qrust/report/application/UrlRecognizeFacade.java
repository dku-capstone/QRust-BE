package com.qrust.report.application;

import com.qrust.report.domain.service.ReportUrlService;
import com.qrust.report.dto.PhishingReportBlacklistCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UrlRecognizeFacade {
    private final ReportUrlService reportUrlService;

    public PhishingReportBlacklistCheckResponse checkUrl(String url) {
        String domain = reportUrlService.extractDomain(url);
        return reportUrlService.findByUrl(domain)
                .map(reportUrl -> new PhishingReportBlacklistCheckResponse(domain, true, reportUrl.getReportCount()))
                .orElseGet(() -> new PhishingReportBlacklistCheckResponse(domain, false, 0));
    }
}
