package com.qrust.qrcode.application;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;

public interface QrCodeGenerator {
    byte[] generateQrCode(QrCodeData qrCodeData);
}
