package com.qrust.recognize.swagger;

import com.qrust.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "QR Recognize Service", description = "AI 기반 URL 위험 탐지")
@RequestMapping("/api/v1/recognize")
public interface AiModelControllerSpec {

    /**
     * Analyzes the given URL using an AI model to determine if it is malicious.
     *
     * @param url the URL to be evaluated for risk
     * @return an ApiResponse containing true if the URL is malicious, or false if it is safe
     */
    @Operation(summary = "AI 모델로 URL 위험 여부 확인", description = "입력된 URL을 AI 모델로 분석해 악성 여부 판별 (true: 악성, false: 안전)")
    @PostMapping("/ai-model")
    ApiResponse<Boolean> checkUrlByAiModel(
            @Parameter(description = "확인할 URL", example = "http://phishing-site.com")
            @RequestParam String url
    );
}
