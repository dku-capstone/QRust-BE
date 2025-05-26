package com.qrust.report.application;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qrust.report.domain.entity.PhishingReport;
import com.qrust.report.domain.entity.ReportUrl;
import com.qrust.report.domain.service.PhishingReportService;
import com.qrust.report.domain.service.ReportUrlService;
import com.qrust.report.dto.PhishingReportUpsertRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportFacadeTest {

    private PhishingReportService phishingReportService;
    private ReportUrlService reportUrlService;
    private ReportFacade reportFacade;

    @BeforeEach
    void setUp() {
        phishingReportService = mock(PhishingReportService.class);
        reportUrlService = mock(ReportUrlService.class);
        reportFacade = new ReportFacade(phishingReportService, reportUrlService);
    }

    @Test
    @DisplayName("관리자가 승인할 때만 reportCount가 증가한다")
    void testApproveReportIncreasesReportCount() {
        Long reportId = 1L;
        String domain = "www.phishing.com";

        ReportUrl reportUrl = mock(ReportUrl.class);
        when(reportUrl.getUrl()).thenReturn(domain);

        PhishingReport phishingReport = mock(PhishingReport.class);
        when(phishingReport.getReportUrl()).thenReturn(reportUrl);

        when(phishingReportService.approveReport(reportId)).thenReturn(phishingReport);

        // when
        reportFacade.approveReport(reportId);

        // then
        verify(reportUrlService, times(1)).increaseReportCount(domain);
    }

    @Test
    @DisplayName("사용자가 신고만 할 경우 reportCount는 증가하지 않는다")
    void testRegisterReportDoesNotIncreaseReportCount() {
        PhishingReportUpsertRequest request = mock(PhishingReportUpsertRequest.class);
        when(request.url()).thenReturn("https://www.phishing.com/path");

        ReportUrl reportUrl = mock(ReportUrl.class);
        when(reportUrlService.upsert(anyString())).thenReturn(reportUrl);

        // when
        reportFacade.registerReport(request, 123L);

        // then
        verify(reportUrlService, never()).increaseReportCount(anyString());
    }
}
