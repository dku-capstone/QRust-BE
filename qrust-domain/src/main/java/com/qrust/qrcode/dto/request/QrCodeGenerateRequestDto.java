package com.qrust.qrcode.dto.request;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;

public record QrCodeGenerateRequestDto (String url, String title){

    public QrCodeData toQrCodeData() {
        return new QrCodeData(url, title);
    }
}
