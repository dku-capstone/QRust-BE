package com.qrust.recognize.controller;

import com.qrust.dto.ApiResponse;
import com.qrust.recognize.application.RecognizeFacade;
import com.qrust.recognize.dto.QrCodeVerificationResponse;
import com.qrust.recognize.swagger.QrRecognizeControllerSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/recognize")
public class QrRecognizeController implements QrRecognizeControllerSpec {
    private final RecognizeFacade recognizeFacade;

    @PostMapping(value = "/verify", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ApiResponse<QrCodeVerificationResponse> verifyQr(@RequestBody byte[] qrCodeImageBytes) {
        return ApiResponse.ok(recognizeFacade.verifyQr(qrCodeImageBytes));
    }
}
