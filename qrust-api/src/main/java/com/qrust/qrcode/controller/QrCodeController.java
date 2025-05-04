package com.qrust.qrcode.controller;

import com.qrust.common.dto.ApiResponse;
import com.qrust.qrcode.application.QrCodeGeneratorService;
import com.qrust.qrcode.controller.swagger.QrCodeControllerSpec;
import com.qrust.qrcode.dto.QrCodeGenerateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/qrcode")
public class QrCodeController implements QrCodeControllerSpec {

    private final QrCodeGeneratorService qrCodeGeneratorService;

    //TODO
    // Authenticated User 연동 + userId 전달

    @PostMapping("/generate")
    public ApiResponse<?> generateQrCode(@RequestBody QrCodeGenerateRequestDto dto, Long userId) {
        String qrCodeImageUrl = qrCodeGeneratorService.generateQrCode(dto.toQrCodeData(), userId);

        return ApiResponse.created(qrCodeImageUrl);
    }

    //TODO
    // QR 코드의 isExpired 필드가 true 인 경우 분기 처리
}
