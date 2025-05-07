package com.qrust.qrcode.dto.request;

import com.qrust.qrcode.domain.entity.vo.QrCodeStatus;

public record QrCodeUpdateRequestDto(String title, QrCodeStatus status) {

}
