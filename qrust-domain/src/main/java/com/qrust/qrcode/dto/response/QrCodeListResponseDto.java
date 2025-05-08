package com.qrust.qrcode.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record QrCodeListResponseDto (Long qrCodeId, String qrCodeImageUrl, String title, LocalDate createdAt, String url) {

    public QrCodeListResponseDto(Long qrCodeId, String qrCodeImageUrl, String title, LocalDateTime createdAt, String url) {
        this(qrCodeId, qrCodeImageUrl, title, createdAt.toLocalDate(), url);

    }

}
