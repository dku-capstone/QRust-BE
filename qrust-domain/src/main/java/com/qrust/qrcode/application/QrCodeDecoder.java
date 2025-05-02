package com.qrust.qrcode.application;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;

public interface QrCodeDecoder {
    QrCodeData decodeQrCodeData(byte[] qrCodeImage);
}
