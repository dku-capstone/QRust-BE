package com.qrust.report.controller;

import com.qrust.dto.ApiResponse;
import com.qrust.report.application.ReportFacade;
import com.qrust.report.swagger.ReportAdminControllerSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportAdminController implements ReportAdminControllerSpec {

    private final ReportFacade reportFacade;

    @Override
    public ApiResponse<Boolean> approveReport(@PathVariable Long reportId) {
        reportFacade.approveReport(reportId);
        return ApiResponse.ok(true);
    }

    @Override
    public ApiResponse<Boolean> rejectReport(@PathVariable Long reportId) {
        reportFacade.rejectReport(reportId);
        return ApiResponse.ok(true);
    }
}
