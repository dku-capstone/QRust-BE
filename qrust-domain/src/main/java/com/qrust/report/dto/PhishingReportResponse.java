package com.qrust.report.dto;

import com.qrust.report.domain.entity.vo.ApproveType;
import com.qrust.report.domain.entity.vo.ReportType;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record PhishingReportResponse(
        Long id,
        ReportType reportType,
        String url,
        ApproveType approveType,
        LocalDate incidentDate
) {
}
