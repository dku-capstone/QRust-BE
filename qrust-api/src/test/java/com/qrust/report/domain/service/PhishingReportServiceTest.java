package com.qrust.report.domain.service;

import static com.qrust.exception.error.ErrorCode.INVALID_INPUT_VALUE;
import static com.qrust.exception.report.ErrorMessages.REPORT_ALREADY_EXISTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.qrust.exception.CustomException;
import com.qrust.report.domain.entity.PhishingReport;
import com.qrust.report.domain.entity.ReportUrl;
import com.qrust.report.domain.repository.PhishingReportRepository;
import com.qrust.report.dto.PhishingReportUpsertRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PhishingReportServiceTest {

    private PhishingReportRepository phishingReportRepository;
    private PhishingReportService phishingReportService;

    @BeforeEach
    void setUp() {
        phishingReportRepository = mock(PhishingReportRepository.class);
        phishingReportService = new PhishingReportService(phishingReportRepository);
    }

    @Test
    @DisplayName("중복 신고가 아닌 경우 정상적으로 저장된다")
    void testSave_WhenNotDuplicate_ShouldSaveReport() {
        Long userId = 1L;
        ReportUrl reportUrl = mock(ReportUrl.class);
        PhishingReportUpsertRequest request = mock(PhishingReportUpsertRequest.class);

        when(phishingReportRepository.existsByUserIdAndReportUrl(userId, reportUrl)).thenReturn(false);

        phishingReportService.save(request, reportUrl, userId);

        verify(phishingReportRepository, times(1)).save(any(PhishingReport.class));
    }

    @Test
    @DisplayName("중복 신고인 경우 예외가 발생한다")
    void testSave_WhenDuplicate_ShouldThrowException() {
        Long userId = 1L;
        ReportUrl reportUrl = mock(ReportUrl.class);
        PhishingReportUpsertRequest request = mock(PhishingReportUpsertRequest.class);

        when(phishingReportRepository.existsByUserIdAndReportUrl(userId, reportUrl)).thenReturn(true);

        CustomException ex = assertThrows(CustomException.class, () ->
                phishingReportService.save(request, reportUrl, userId)
        );

        assertEquals(INVALID_INPUT_VALUE, ex.getErrorCode());
        assertEquals(REPORT_ALREADY_EXISTS, ex.getMessage());

        verify(phishingReportRepository, never()).save(any());  // 저장이 호출되지 않아야 함
    }
}
