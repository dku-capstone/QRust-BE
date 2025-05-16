package com.qrust.recognize.controller;

import com.qrust.dto.ApiResponse;
import com.qrust.external.ai.application.AiModelUrlVerifyService;
import com.qrust.recognize.swagger.AiModelControllerSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AiModelController implements AiModelControllerSpec {

    private final AiModelUrlVerifyService aiModelUrlVerifyService;

    /**
     * Determines whether the given URL is malicious using an AI-based verification service.
     *
     * @param url the URL to be checked
     * @return an ApiResponse containing true if the URL is identified as malicious, false otherwise
     */
    @Override
    public ApiResponse<Boolean> checkUrlByAiModel(String url) {
        int result = aiModelUrlVerifyService.verifyUrl(url);
        boolean isMalicious = result == 1;
        return ApiResponse.ok(isMalicious);
    }
}
