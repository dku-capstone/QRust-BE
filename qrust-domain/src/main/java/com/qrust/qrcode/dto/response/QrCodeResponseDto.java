package com.qrust.qrcode.dto.response;

import java.time.LocalDate;

public record QrCodeResponseDto(String qrCodeImageUrl, String title, LocalDate createdAt, String url) {
}
