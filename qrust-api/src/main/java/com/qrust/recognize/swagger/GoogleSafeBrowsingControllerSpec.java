package com.qrust.recognize.swagger;

import com.qrust.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "QR Recognize Service", description = "안전한 QR 인식")
public interface GoogleSafeBrowsingControllerSpec {

    @Operation(summary = "블랙리스트 URL 위험 여부 확인", description = "입력된 URL 블랙리스트 기반 탐지 (true: 악성, false: 안전)")
    @PostMapping("/blacklist")
    ApiResponse<Boolean> checkUrl(
            @Parameter(description = "확인할 URL", example = "http://example.com")
            String url
    );
}
