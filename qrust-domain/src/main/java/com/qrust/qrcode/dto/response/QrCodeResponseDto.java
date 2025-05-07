package com.qrust.qrcode.dto.response;

import java.time.LocalDateTime;

public record QrCodeResponseDto(String qrCodeImageUrl, String title, LocalDateTime createdAt, String url) {
}
