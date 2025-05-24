package com.qrust.recognize.controller;

import com.qrust.dto.ApiResponse;
import com.qrust.external.google.application.GoogleSafeBrowsingService;
import com.qrust.recognize.swagger.GoogleSafeBrowsingControllerSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GoogleSafeBrowsingController implements GoogleSafeBrowsingControllerSpec {

    private final GoogleSafeBrowsingService safeBrowsingService;

    @Override
    public ApiResponse<Boolean> checkUrl(@RequestParam String url) {
        return ApiResponse.ok(safeBrowsingService.isUrlDangerous(url));
    }
}
