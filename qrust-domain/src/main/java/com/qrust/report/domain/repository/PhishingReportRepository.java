package com.qrust.report.domain.repository;

import com.qrust.report.domain.entity.PhishingReport;
import com.qrust.report.domain.entity.ReportUrl;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhishingReportRepository extends JpaRepository<PhishingReport, Long> {
    List<PhishingReport> findAllByUserId(Long userId);

    Optional<PhishingReport> findByIdAndUserId(Long id, Long userId);
    boolean existsByUserIdAndReportUrl(Long userId, ReportUrl reportUrl);

}
