package com.qrust.qrcode.dto;

import java.time.LocalDateTime;

public record QrCodeListResponseDto (Long qrCodeId, String qrCodeImageUrl, String title, LocalDateTime createdAt, String url) {

    public QrCodeListResponseDto(Long qrCodeId, String qrCodeImageUrl, String title, LocalDateTime createdAt, String url) {
        this.qrCodeId = qrCodeId;
        this.qrCodeImageUrl = qrCodeImageUrl;
        this.title = title;
        this.createdAt = createdAt;
        this.url = url;
    }

}
