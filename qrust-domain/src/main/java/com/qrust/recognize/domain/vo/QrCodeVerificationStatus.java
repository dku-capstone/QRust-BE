package com.qrust.recognize.domain.vo;

public enum QrCodeVerificationStatus {
    TRUSTED_QR,
    INVALID_QR,
    GOOGLE_BLOCKED,
    REPORT_BLACKLISTED,
    AI_MODEL_BLOCKED,
    VERIFIED_SAFE
}
