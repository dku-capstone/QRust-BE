package com.qrust.qrcode.dto;

import com.qrust.qrcode.domain.entity.vo.QrCodeData;

public record QrCodeGenerateRequestDto (String url, String title){

    public QrCodeData toQrCodeData() {
        return new QrCodeData(url, title);
    }
}
