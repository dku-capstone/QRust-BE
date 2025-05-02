package com.qrust.qrcode.appliaction;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;

public interface QrCodeDecoder {
    QrCodeData decodeQrCodeData(byte[] qrCodeImage);
}
