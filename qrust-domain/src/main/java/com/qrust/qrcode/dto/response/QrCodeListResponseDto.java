package com.qrust.qrcode.dto.response;

import java.time.LocalDate;

public record QrCodeListResponseDto (Long qrCodeId, String qrCodeImageUrl, String title, LocalDate createdAt, String url) {

    public QrCodeListResponseDto(Long qrCodeId, String qrCodeImageUrl, String title, LocalDate createdAt, String url) {
        this.qrCodeId = qrCodeId;
        this.qrCodeImageUrl = qrCodeImageUrl;
        this.title = title;
        this.createdAt = createdAt;
        this.url = url;
    }

}
