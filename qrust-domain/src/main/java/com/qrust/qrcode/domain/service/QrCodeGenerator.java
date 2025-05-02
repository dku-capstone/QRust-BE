package com.qrust.qrcode.domain.service;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;

public interface QrCodeGenerator {
    byte[] generateQrCode(QrCodeData qrCodeData);
}
