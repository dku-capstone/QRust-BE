package com.qrust.report.domain.repository;

import com.qrust.report.domain.entity.PhishingReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhishingReportRepository extends JpaRepository<PhishingReport, Long> {
}
