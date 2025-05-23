package com.qrust.report.domain.entity;

import com.qrust.common.infrastructure.jpa.shared.BaseEntity;
import com.qrust.report.domain.entity.vo.ApproveType;
import com.qrust.report.domain.entity.vo.ReportType;
import com.qrust.report.dto.PhishingReportRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "phishing_report")
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhishingReport extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id", nullable = false)
    private ReportUrl reportUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "report_type", nullable = false)
    private ReportType reportType;

    @Column(name = "incident_date", nullable = false)
    private LocalDate incidentDate;

    @Column(name = "report_text", nullable = false)
    private String reportText;

    @Enumerated(EnumType.STRING)
    @Column(name = "approve_type", nullable = false)
    private ApproveType approveType;

    public static PhishingReport of(PhishingReportRequest request, ReportUrl reportUrl) {
        return PhishingReport.builder()
                .userId(request.userId())
                .reportUrl(reportUrl)
                .reportType(request.reportType())
                .reportText(request.reportText())
                .incidentDate(request.incidentDate())
                .approveType(ApproveType.PENDING)
                .build();
    }
}
