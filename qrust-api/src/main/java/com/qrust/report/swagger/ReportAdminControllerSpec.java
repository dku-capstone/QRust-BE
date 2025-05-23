package com.qrust.report.swagger;

import com.qrust.annotation.RoleOnly;
import com.qrust.dto.ApiResponse;
import com.qrust.user.domain.entity.vo.UserRole;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "QRust ADMIN REPORT", description = "관리자 - 피싱 신고 관리 API")
@RequestMapping("/api/v1/admin/report")
@RoleOnly(UserRole.ADMIN)
public interface ReportAdminControllerSpec {

    @Operation(summary = "신고 승인", description = "PENDING 상태의 신고를 승인 처리합니다.")
    @PostMapping("/{reportId}/approve")
    ApiResponse<Boolean> approveReport(@PathVariable Long reportId);

    @Operation(summary = "신고 거부", description = "PENDING 상태의 신고를 거부합니다.")
    @PostMapping("/{reportId}/reject")
    ApiResponse<Boolean> rejectReport(@PathVariable Long reportId);
}
