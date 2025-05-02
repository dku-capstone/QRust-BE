package com.qrust.qrcode.appliaction;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;

public interface QrCodeGenerator {
    byte[] generateQrCode(QrCodeData qrCodeData);
}
