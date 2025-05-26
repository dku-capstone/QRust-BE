package com.qrust.recognize.controller;

import com.qrust.dto.ApiResponse;
import com.qrust.recognize.swagger.ReportUrlControllerSpec;
import com.qrust.report.application.UrlRecognizeFacade;
import com.qrust.report.dto.PhishingReportBlacklistCheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recognize")
public class ReportUrlController implements ReportUrlControllerSpec {
    private final UrlRecognizeFacade urlRecognizeFacade;

    @Override
    public ApiResponse<PhishingReportBlacklistCheckResponse> checkUrl(@RequestParam String url) {
        return ApiResponse.ok(urlRecognizeFacade.checkUrl(url));
    }
}
