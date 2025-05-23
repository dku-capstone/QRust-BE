package com.qrust.report.domain.service;

import com.qrust.report.domain.entity.PhishingReport;
import com.qrust.report.domain.entity.ReportUrl;
import com.qrust.report.domain.repository.PhishingReportRepository;
import com.qrust.report.dto.PhishingReportRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhishingReportService {
    private final PhishingReportRepository phishingReportRepository;

    @Transactional
    public void save(PhishingReportRequest request, ReportUrl reportUrl){
        PhishingReport report = PhishingReport.of(request, reportUrl);
        phishingReportRepository.save(report);
    }
}
