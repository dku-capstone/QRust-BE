package com.qrust.qrcode.domain.service;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;

public interface QrCodeDecoder {
    QrCodeData decodeQrCodeData(String encryptedQrCodeData);
}
