package com.qrust.qrcode.dto.request;

import com.qrust.qrcode.domain.entity.vo.QrCodeStatus;
import com.qrust.qrcode.domain.entity.vo.QrCodeType;
import java.time.LocalDate;

public record QrCodeSearchRequestDto(String title, LocalDate start, LocalDate end, QrCodeType type, QrCodeStatus status) {

}
