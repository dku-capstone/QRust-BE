package com.qrust.report.dto;

import com.qrust.report.domain.entity.vo.ReportType;
import java.time.LocalDate;

public record PhishingReportUpsertRequest(
        Long userId,
        String url,
        ReportType reportType,
        LocalDate incidentDate,
        String reportText
) {
}
