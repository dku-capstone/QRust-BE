package com.qrust.controller.recognize;

import com.qrust.common.dto.ApiResponse;
import com.qrust.external.google.application.GoogleSafeBrowsingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/recognize")
@RequiredArgsConstructor
@Tag(name = "QR Recognize Service", description = "안전한 QR 인식")
public class GoogleSafeBrowsingController {

    private final GoogleSafeBrowsingService safeBrowsingService;

    @Operation(summary = "URL 위험 여부 확인", description = "입력된 URL 블랙리스트 기반 탐지 (true: 악성, false: 안전)")
    @PostMapping("/blacklist")
    public ApiResponse<Boolean> checkUrl(
            @Parameter(description = "확인할 URL", example = "http://example.com")
            @RequestParam String url
    ) {
        return ApiResponse.ok(safeBrowsingService.isUrlDangerous(url));
    }
}
