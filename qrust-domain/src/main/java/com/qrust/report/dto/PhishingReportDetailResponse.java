package com.qrust.report.dto;

import com.qrust.report.domain.entity.vo.ApproveType;
import com.qrust.report.domain.entity.vo.ReportType;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record PhishingReportDetailResponse(
        Long id,
        String url,
        ReportType reportType,
        String reportText,
        LocalDate incidentDate,
        ApproveType approveType
) {
}
