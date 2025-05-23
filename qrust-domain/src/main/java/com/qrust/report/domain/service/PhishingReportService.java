package com.qrust.report.domain.service;

import com.qrust.report.domain.entity.PhishingReport;
import com.qrust.report.domain.entity.ReportUrl;
import com.qrust.report.domain.repository.PhishingReportRepository;
import com.qrust.report.dto.PhishingReportUpsertRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhishingReportService {
    private final PhishingReportRepository phishingReportRepository;

    @Transactional
    public void save(PhishingReportUpsertRequest request, ReportUrl reportUrl, Long userId) {
        PhishingReport report = PhishingReport.of(request, reportUrl, userId);
        phishingReportRepository.save(report);
    }
}
