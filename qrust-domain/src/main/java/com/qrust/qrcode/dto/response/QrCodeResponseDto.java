package com.qrust.qrcode.dto.response;

import com.qrust.qrcode.domain.entity.vo.QrCodeStatus;
import java.time.LocalDate;

public record QrCodeResponseDto(Long qrCodeId, QrCodeStatus status, String qrCodeImageUrl, String title, LocalDate createdAt, String url) {
}
