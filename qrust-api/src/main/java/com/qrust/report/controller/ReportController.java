package com.qrust.report.controller;

import com.qrust.annotation.user.LoginUser;
import com.qrust.dto.ApiResponse;
import com.qrust.report.application.ReportFacade;
import com.qrust.report.dto.PhishingReportDetailResponse;
import com.qrust.report.dto.PhishingReportResponse;
import com.qrust.report.dto.PhishingReportUpsertRequest;
import com.qrust.report.swagger.ReportControllerSpec;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController implements ReportControllerSpec {

    private final ReportFacade reportFacade;

    @Override
    public ApiResponse<Boolean> registerReport(@RequestBody PhishingReportUpsertRequest request,
                                               @LoginUser Long userId) {
        reportFacade.registerReport(request, userId);
        return ApiResponse.ok(true);
    }

    @Override
    public ApiResponse<List<PhishingReportResponse>> getMyReports(@LoginUser Long userId) {
        return ApiResponse.ok(reportFacade.getMyReports(userId));
    }

    @Override
    public ApiResponse<PhishingReportDetailResponse> getReportDetail(@PathVariable Long reportId,
                                                                     @LoginUser Long userId) {
        return ApiResponse.ok(reportFacade.getReportDetail(reportId, userId));
    }
}
