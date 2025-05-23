package com.qrust.report.controller;

import com.qrust.dto.ApiResponse;
import com.qrust.report.application.ReportFacade;
import com.qrust.report.dto.PhishingReportUpsertRequest;
import com.qrust.report.swagger.ReportControllerSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportControllerSpec {

    private final ReportFacade reportFacade;

    @Override
    public ApiResponse<Boolean> registerReport(PhishingReportUpsertRequest request, Long userId) {
        reportFacade.registerReport(request, userId);
        return ApiResponse.ok(true);
    }
}
