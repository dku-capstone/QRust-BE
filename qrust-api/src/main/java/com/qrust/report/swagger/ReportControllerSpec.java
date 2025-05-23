package com.qrust.report.swagger;

import com.qrust.annotation.user.LoginUser;
import com.qrust.dto.ApiResponse;
import com.qrust.report.dto.PhishingReportDetailResponse;
import com.qrust.report.dto.PhishingReportResponse;
import com.qrust.report.dto.PhishingReportUpsertRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "QRust REPORT", description = "피싱 신고 API")
@RequestMapping("/api/v1/report")
public interface ReportControllerSpec {

    @Operation(summary = "피싱 신고 등록", description = "피싱 사이트 신고를 등록합니다.")
    @PostMapping("/register")
    ApiResponse<Boolean> registerReport(
            @Parameter(description = "피싱 신고 정보") @RequestBody PhishingReportUpsertRequest request,
            @LoginUser Long userId
    );

    @Operation(summary = "내 신고 목록", description = "로그인한 사용자의 신고 목록을 조회합니다.")
    @GetMapping("/my")
    ApiResponse<List<PhishingReportResponse>> getMyReports(@LoginUser Long userId);

    @Operation(summary = "신고 상세 조회", description = "사용자의 신고 상세 정보를 조회합니다.")
    @GetMapping("/{reportId}")
    ApiResponse<PhishingReportDetailResponse> getReportDetail(
            @PathVariable Long reportId,
            @LoginUser Long userId
    );
}
