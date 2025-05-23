package com.qrust.report.swagger;

import com.qrust.annotation.user.LoginUser;
import com.qrust.dto.ApiResponse;
import com.qrust.report.dto.PhishingReportUpsertRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
            @Parameter(description = "사용자 ID") @LoginUser Long userId
    );
}
