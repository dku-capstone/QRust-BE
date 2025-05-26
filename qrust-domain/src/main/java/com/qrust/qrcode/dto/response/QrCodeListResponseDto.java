package com.qrust.qrcode.dto.response;

import com.qrust.qrcode.domain.entity.vo.QrCodeStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record QrCodeListResponseDto (Long qrCodeId, String qrCodeImageUrl, String title, LocalDate createdAt, String url, QrCodeStatus status) {

    public QrCodeListResponseDto(Long qrCodeId, String qrCodeImageUrl, String title, LocalDateTime createdAt, String url, QrCodeStatus status) {
        this(qrCodeId, qrCodeImageUrl, title, createdAt.toLocalDate(), url, status);

    }

}
