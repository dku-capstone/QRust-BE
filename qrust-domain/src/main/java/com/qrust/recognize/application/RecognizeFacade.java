package com.qrust.recognize.application;

import com.qrust.external.ai.application.AiModelUrlVerifyService;
import com.qrust.external.google.application.GoogleSafeBrowsingService;
import com.qrust.qrcode.domain.entity.vo.QrCodeData;
import com.qrust.qrcode.domain.service.QrCodeDecoder;
import com.qrust.recognize.domain.service.RecognizeService;
import com.qrust.recognize.domain.vo.QrCodeVerificationStatus;
import com.qrust.recognize.dto.QrCodeVerificationResponse;
import com.qrust.report.application.UrlRecognizeFacade;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecognizeFacade {
    private final QrCodeDecoder qrCodeDecoder;
    private final RecognizeService recognizeService;
    private final GoogleSafeBrowsingService googleSafeBrowsingService;
    private final UrlRecognizeFacade urlRecognizeFacade;
    private final AiModelUrlVerifyService aiModelUrlVerifyService;

    public QrCodeVerificationResponse verifyQr(String encryptedQrCodeData) {
        try {
            QrCodeData data = qrCodeDecoder.decodeQrCodeData(encryptedQrCodeData);  // ✅ 복호화 성공 여부로 판단
            String url = data.getUrl();
            String domain = extractDomain(url);

            // ✅ 복호화 성공 → 신뢰된 QR로 판단하고 바로 리턴
            return new QrCodeVerificationResponse(QrCodeVerificationStatus.TRUSTED_QR, url, domain);

        } catch (Exception decodeException) {
            // 🔽 복호화 실패 → 비신뢰 QR → 3단계 검증
            try {
                String extractedText = encryptedQrCodeData;
                String domain = extractDomain(extractedText);

                // 1. Google Safe Browsing
                if (googleSafeBrowsingService.isUrlDangerous(extractedText)) {
                    return new QrCodeVerificationResponse(QrCodeVerificationStatus.GOOGLE_BLOCKED, extractedText,
                            domain);
                }

                // 2. 내부 블랙리스트
                if (urlRecognizeFacade.checkUrl(extractedText).isBlacklist()) {
                    return new QrCodeVerificationResponse(QrCodeVerificationStatus.REPORT_BLACKLISTED, extractedText,
                            domain);
                }

                // 3. AI 모델 검증
                int aiResult = aiModelUrlVerifyService.verifyUrl(extractedText);
                if (aiResult == 1) {
                    return new QrCodeVerificationResponse(QrCodeVerificationStatus.AI_MODEL_BLOCKED, extractedText,
                            domain);
                }

                // 통과 시
                return new QrCodeVerificationResponse(QrCodeVerificationStatus.VERIFIED_SAFE, extractedText, domain);

            } catch (Exception e) {
                return new QrCodeVerificationResponse(QrCodeVerificationStatus.INVALID_QR, null, null);
            }
        }
    }

    private String extractDomain(String fullUrl) {
        try {
            if (!fullUrl.startsWith("http://") && !fullUrl.startsWith("https://")) {
                fullUrl = "http://" + fullUrl;
            }
            return new URL(fullUrl).getHost();
        } catch (Exception e) {
            return null;
        }
    }
}
