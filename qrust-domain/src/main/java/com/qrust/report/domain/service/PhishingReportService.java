package com.qrust.report.domain.service;

import static com.qrust.exception.error.ErrorCode.INVALID_INPUT_VALUE;
import static com.qrust.exception.report.ErrorMessages.REPORT_NOT_EXIST;

import com.qrust.exception.CustomException;
import com.qrust.report.domain.entity.PhishingReport;
import com.qrust.report.domain.entity.ReportUrl;
import com.qrust.report.domain.repository.PhishingReportRepository;
import com.qrust.report.dto.PhishingReportUpsertRequest;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PhishingReportService {
    private final PhishingReportRepository phishingReportRepository;

    @Transactional
    public void save(PhishingReportUpsertRequest request, ReportUrl reportUrl, Long userId) {
        PhishingReport report = PhishingReport.of(request, reportUrl, userId);
        phishingReportRepository.save(report);
    }

    @Transactional
    public PhishingReport approveReport(Long reportId) {
        PhishingReport report = getById(reportId);
        report.approve();
        return report;
    }

    @Transactional
    public void rejectReport(Long reportId) {
        PhishingReport report = getById(reportId);
        report.reject();
    }

    @Transactional(readOnly = true)
    public Optional<PhishingReport> findById(Long id) {
        return phishingReportRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public PhishingReport getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new CustomException(INVALID_INPUT_VALUE, REPORT_NOT_EXIST));
    }
}
