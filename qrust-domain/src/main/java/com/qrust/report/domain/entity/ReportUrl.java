package com.qrust.report.domain.entity;

import com.qrust.common.infrastructure.jpa.shared.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "report_url")
@Getter
@SuperBuilder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReportUrl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url_address", nullable = false)
    private String url;

    @Column(name = "report_count", nullable = false)
    private int reportCount;

    public void incrementReportCount() {
        this.reportCount++;
    }

    public static ReportUrl from(String url) {
        return ReportUrl.builder()
                .url(url)
                .reportCount(0)
                .build();
    }
}
