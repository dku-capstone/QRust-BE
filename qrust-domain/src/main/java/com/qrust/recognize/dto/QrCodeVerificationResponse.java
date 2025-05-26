package com.qrust.recognize.dto;

import com.qrust.recognize.domain.vo.QrCodeVerificationStatus;

public record QrCodeVerificationResponse(
        QrCodeVerificationStatus status,
        String url,
        String domain
) {}
