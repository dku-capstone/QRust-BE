package com.qrust.report.domain.repository;

import com.qrust.report.domain.entity.PhishingReport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhishingReportRepository extends JpaRepository<PhishingReport, Long> {
    List<PhishingReport> findAllByUserId(Long userId);
}
