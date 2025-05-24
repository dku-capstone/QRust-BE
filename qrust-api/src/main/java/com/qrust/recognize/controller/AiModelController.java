package com.qrust.recognize.controller;

import com.qrust.dto.ApiResponse;
import com.qrust.external.ai.application.AiModelUrlVerifyService;
import com.qrust.recognize.swagger.AiModelControllerSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AiModelController implements AiModelControllerSpec {

    private final AiModelUrlVerifyService aiModelUrlVerifyService;

    @Override
    public ApiResponse<Boolean> checkUrlByAiModel(@RequestParam String url) {
        int result = aiModelUrlVerifyService.verifyUrl(url);
        boolean isMalicious = result == 1;
        return ApiResponse.ok(isMalicious);
    }
}
