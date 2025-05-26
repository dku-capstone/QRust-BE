package com.qrust.recognize.swagger;

import com.qrust.dto.ApiResponse;
import com.qrust.recognize.dto.QrCodeVerificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "QR Recognize Service", description = "QR 코드 위험도 검증 서비스")
public interface QrRecognizeControllerSpec {

    @Operation(
            summary = "QR 코드 위험도 검증",
            description = """
        QR 이미지 데이터를 입력받아 복호화 시도 후,
        복호화 성공 여부 및 Google Safe Browsing / 내부 블랙리스트 / AI 검증을 거쳐 위험 여부를 판단합니다.
        
        - 복호화 성공 시: TRUSTED_QR
        - 복호화 실패 시: GOOGLE_BLOCKED, REPORT_BLACKLISTED, AI_MODEL_BLOCKED, VERIFIED_SAFE, INVALID_QR
        """
    )
    @PostMapping("/verify")
    ApiResponse<QrCodeVerificationResponse> verifyQr(
            @Parameter(description = "QR 이미지 바이트 배열 (Base64 또는 Binary 전송)", required = true)
            @RequestBody String encryptedQrCodeData
    );
}
